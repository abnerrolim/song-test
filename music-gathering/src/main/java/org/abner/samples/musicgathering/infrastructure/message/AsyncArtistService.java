package org.abner.samples.musicgathering.infrastructure.message;

import org.abner.samples.musicgathering.application.ArtistRepository;
import org.abner.samples.musicgathering.domain.messages.*;
import org.abner.samples.musicgathering.infrastructure.rest.description.ArtistDescriptionExtractor;
import org.abner.samples.musicgathering.infrastructure.rest.description.ArtistDescriptionExtractorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AsyncArtistService {


    private final ArtistRepository databaseRepo;
    private final ArtistRepository restRepo;
    private final JmsTemplate jmsTemplate;
    private final ArtistDescriptionExtractorFactory descriptionExtractorFactory;

    private static final Logger LOG = Logger.getLogger(AsyncArtistService.class.getName());

    @Autowired
    public AsyncArtistService(
            @Qualifier("database") ArtistRepository database,
            @Qualifier("rest") ArtistRepository rest,
            ArtistDescriptionExtractorFactory descriptionExtractorFactory,
            JmsTemplate jmsTemplate
    ) {
        this.restRepo = rest;
        this.databaseRepo = database;
        this.jmsTemplate = jmsTemplate;
        this.descriptionExtractorFactory = descriptionExtractorFactory;
    }


    @JmsListener(destination = MessageDestinationChannels.ARTIST, containerFactory = "jmsConnectionFactory")
    public void receiveMessage(ArtistCommand artistCommand) {

        LOG.log(Level.INFO, "ArtistCommand received {0}", artistCommand);
        restRepo.findByMBID(artistCommand.getMbid()).ifPresent(artist -> {
            databaseRepo.save(artist);
            jmsTemplate.convertAndSend(MessageDestinationChannels.RELEASES, new ReleasesCommand(artistCommand.getMbid()));
            jmsTemplate.convertAndSend(MessageDestinationChannels.ARTIST_DESCRIPTION, new ArtistDescriptionCommand(artistCommand.getMbid()));
        });
    }

    @JmsListener(destination = MessageDestinationChannels.ARTIST_DESCRIPTION, containerFactory = "jmsConnectionFactory")
    public void receiveMessage(ArtistDescriptionCommand artistDescriptionCommand) {
        LOG.log(Level.INFO, "ArtistDescriptionCommand received {0}", artistDescriptionCommand);
        databaseRepo.findByMBID(artistDescriptionCommand.getMbid()).ifPresent(artist -> {
            String desc = null;
            for (ArtistDescriptionExtractor extractor : descriptionExtractorFactory.extractors(artist.getReferences())) {
                desc = extractor.extract();
                if (!StringUtils.isEmpty(desc))
                    break;
            }
            artist.setDescription(desc);
            databaseRepo.save(artist);
        });
    }


    @JmsListener(destination = MessageDestinationChannels.RELEASES, containerFactory = "jmsConnectionFactory")
    public void receiveMessage(ReleasesCommand releasesCommand) {
        LOG.log(Level.INFO, "ReleasesCommand received {0}", releasesCommand);
        databaseRepo.findByMBID(releasesCommand.getMbid()).ifPresent(artist -> {
            restRepo.findByActorMBID(releasesCommand.getMbid()).stream()
                    .forEach(releaseGroup -> {
                        jmsTemplate.convertAndSend(MessageDestinationChannels.RELEASES_COVER_ART, new ReleaseCoverArtCommand(releaseGroup.getMbid()));
                        databaseRepo.save(releaseGroup);
                    });
        });
    }

    @JmsListener(destination = MessageDestinationChannels.RELEASES_COVER_ART, containerFactory = "jmsConnectionFactory")
    public void receiveMessage(ReleaseCoverArtCommand releaseCoverArtCommand) {
        LOG.log(Level.INFO, "ReleaseCoverArtCommand received {0}", releaseCoverArtCommand);
        databaseRepo.findByReleaseGroupMBID(releaseCoverArtCommand.getMbid()).ifPresent(releaseGroupDB -> {
            var enriched = restRepo.enrichReleaseGroup(releaseGroupDB);
            databaseRepo.save(enriched);
        });
    }

    @Cacheable(value = "enqueued")
    public boolean startAsync(String mdib) {
        LOG.log(Level.INFO, "Starting async search of {0}", mdib);
        jmsTemplate.convertAndSend(MessageDestinationChannels.ARTIST, new ArtistCommand(mdib));
        return true;
    }
}
