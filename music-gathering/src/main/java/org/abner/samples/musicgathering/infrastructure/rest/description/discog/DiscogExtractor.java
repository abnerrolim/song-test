package org.abner.samples.musicgathering.infrastructure.rest.description.discog;

import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.abner.samples.musicgathering.domain.References;
import org.abner.samples.musicgathering.infrastructure.rest.description.ArtistDescriptionExtractor;
import org.abner.samples.musicgathering.infrastructure.rest.musicbrainz.ArtistLookupMB;
import org.abner.samples.musicgathering.infrastructure.rest.UserAgentInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DiscogExtractor implements ArtistDescriptionExtractor {

    private final static String DISCOGS_TYPE = "discogs";
    private final DiscogClient discogClient;
    private References reference;

    @Autowired
    public DiscogExtractor(DiscogClient discogClient){
        this.discogClient = discogClient;
    }

    @Override
    public String extract() {
        Objects.requireNonNull(reference);
        var resource = reference.getResource();
        var artistID = resource.substring(resource.lastIndexOf("/"));
        return discogClient.artistProfile(artistID).getProfile();
    }

    @Override
    public Boolean apply(References referenceCandidate) {
        var applied = DISCOGS_TYPE.equals(referenceCandidate.getType());
        if(applied)
            this.reference = referenceCandidate;
        return applied;
    }
}
