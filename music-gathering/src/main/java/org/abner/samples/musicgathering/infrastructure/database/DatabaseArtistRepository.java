package org.abner.samples.musicgathering.infrastructure.database;

import org.abner.samples.musicgathering.application.ArtistRepository;
import org.abner.samples.musicgathering.domain.Artist;
import org.abner.samples.musicgathering.domain.References;
import org.abner.samples.musicgathering.domain.ReleaseGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Qualifier("database")
public class DatabaseArtistRepository implements ArtistRepository {

    private final ArtistDBStore artistDBStore;
    private final ArtistInfoSourceDBStore artistInfoSourceDBStore;
    private final ReleaseGroupDBStore releaseGroupDBStore;

    @Autowired
    public DatabaseArtistRepository(
            ArtistDBStore artistDBStore
            , ArtistInfoSourceDBStore artistInfoSourceDBStore
            , ReleaseGroupDBStore releaseGroupDBStore
    ) {
        this.artistDBStore = artistDBStore;
        this.artistInfoSourceDBStore = artistInfoSourceDBStore;
        this.releaseGroupDBStore = releaseGroupDBStore;
    }

    @Override
//    @Cacheable(value="defaultCache", unless="#result == null")
    public Optional<Artist> findByMBID(String mbid) {

        return artistDBStore.findById(mbid)
                .map(artistDB -> {
                    var artist = ModelMapperUtils.map(artistDB);
                    artist.setReleasesGroup(findByActorMBID(mbid));
                    artist.setReferences(findReferencesByActorMBID(mbid));
                    return artist;
                });
    }

    @Override
    public void save(Artist artist) {
        artistDBStore.save(ModelMapperUtils.map(artist));
        artistInfoSourceDBStore.saveAll(artist.getReferences().stream()
                .map(ModelMapperUtils::map)
                .collect(Collectors.toList())
        );
        releaseGroupDBStore.saveAll(artist.getReleasesGroup().stream()
                .map(ModelMapperUtils::map)
                .collect(Collectors.toList())
        );
    }

    @Override
    public void save(ReleaseGroup releaseGroup) {
        releaseGroupDBStore.save(ModelMapperUtils.map(releaseGroup));
    }

    @Override
    public void save(References reference) {
        artistInfoSourceDBStore.save(ModelMapperUtils.map(reference));
    }

    @Override
    public Collection<ReleaseGroup> findByActorMBID(String mbid) {
        return releaseGroupDBStore.findByArtist(new ArtistDB(mbid))
                .stream()
                .map(ModelMapperUtils::map)
                .collect(Collectors.toList());
    }

    private Collection<References> findReferencesByActorMBID(String mbid) {
        return artistInfoSourceDBStore.findByArtist(new ArtistDB(mbid))
                .stream()
                .map(ModelMapperUtils::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReleaseGroup> findByReleaseGroupMBID(String mbid) {
        var opt = releaseGroupDBStore.findById(mbid);
        return opt.map(ModelMapperUtils::map);
    }

    @Override
    public ReleaseGroup enrichReleaseGroup(ReleaseGroup releaseGroup) {
        var opt = releaseGroupDBStore.findById(releaseGroup.getMbid());
        return opt.map(ModelMapperUtils::map).orElse(releaseGroup);
    }

}
