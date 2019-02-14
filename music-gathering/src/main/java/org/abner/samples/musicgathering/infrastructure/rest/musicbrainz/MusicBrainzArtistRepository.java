package org.abner.samples.musicgathering.infrastructure.rest.musicbrainz;

import feign.FeignException;
import org.abner.samples.musicgathering.application.ArtistRepository;
import org.abner.samples.musicgathering.domain.Artist;
import org.abner.samples.musicgathering.domain.References;
import org.abner.samples.musicgathering.domain.ReleaseGroup;
import org.abner.samples.musicgathering.infrastructure.rest.ModelMapperUtils;
import org.abner.samples.musicgathering.infrastructure.rest.coverart.CoverArt;
import org.abner.samples.musicgathering.infrastructure.rest.coverart.CoverArtClient;
import org.abner.samples.musicgathering.infrastructure.rest.description.ArtistDescriptionExtractorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("rest")
public class MusicBrainzArtistRepository implements ArtistRepository {

    private final MusicBrainzClient musicBrainzClient;
    private final CoverArtClient coverArtClient;
    private final ArtistDescriptionExtractorFactory descriptionExtractorFactory;
    private final String DEFAULT_IMAGE;

    @Autowired
    public MusicBrainzArtistRepository(
            ArtistDescriptionExtractorFactory descriptionExtractorFactory
            , CoverArtClient coverArtClient
            , MusicBrainzClient musicBrainzClient
            , @Value("${music.gathering.default.img}") String defaultImage

    ) {
        this.musicBrainzClient = musicBrainzClient;
        this.coverArtClient = coverArtClient;
        this.descriptionExtractorFactory = descriptionExtractorFactory;
        this.DEFAULT_IMAGE = defaultImage;
    }


    @Override
    public Optional<Artist> findByMBID(String mbid) {
        var artistLkp = Optional.ofNullable(musicBrainzClient.lookupArtist(mbid));
        return artistLkp.map(ModelMapperUtils::map);
    }

    @Override
    public void save(Artist artist) {
        throw new UnsupportedOperationException("Update/Save operations aren't implemented on MusicBrainz repository");
    }

    @Override
    public void save(ReleaseGroup releaseGroup) {
        throw new UnsupportedOperationException("Update/Save operations aren't implemented on MusicBrainz repository");
    }

    @Override
    public void save(References reference) {
        throw new UnsupportedOperationException("Update/Save operations aren't implemented on MusicBrainz repository");
    }

    @Override
    public Collection<ReleaseGroup> findByActorMBID(String mbid) {
        int offset = 0;
        int count = 1;
        var releaseGroupSet = new HashSet<ReleaseGroupPageMB.ReleaseGroupMB>();
        while (releaseGroupSet.size() < count) {
            var page = musicBrainzClient.releaseGroups(mbid, offset);
            releaseGroupSet.addAll(page.getReleaseGroups());
            count = page.getReleaseGroupCount();
            offset += 1;
        }
        return releaseGroupSet.stream().map(releaseGroupMB -> ModelMapperUtils.map(releaseGroupMB, mbid))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReleaseGroup> findByReleaseGroupMBID(String mbid) {
        var opt = Optional.ofNullable(musicBrainzClient.releaseGroup(mbid));
        return opt.map(releaseGrp -> ModelMapperUtils.map(releaseGrp, null));
    }

    @Override
    public ReleaseGroup enrichReleaseGroup(ReleaseGroup releaseGroup) {
        try {

            var covers = coverArtClient.coverArt(releaseGroup.getMbid()).getImages();
            var image = covers.stream().filter(CoverArt.Images::isFront).findAny()
                    .orElse(
                            covers.stream().filter(CoverArt.Images::isBack)
                                    .findAny().orElse(covers.stream().findAny().orElse(new CoverArt.Images()))
                    );
            releaseGroup.setImageResource(image.getImage());
        } catch (FeignException e) {
            defaultImage(releaseGroup);
        }
        return releaseGroup;
    }

    public void defaultImage(ReleaseGroup releaseGroup) {
        releaseGroup.setImageResource(DEFAULT_IMAGE);
    }

}
