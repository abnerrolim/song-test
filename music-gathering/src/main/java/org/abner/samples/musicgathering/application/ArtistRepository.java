package org.abner.samples.musicgathering.application;

import org.abner.samples.musicgathering.domain.Artist;
import org.abner.samples.musicgathering.domain.References;
import org.abner.samples.musicgathering.domain.ReleaseGroup;

import java.util.Collection;
import java.util.Optional;

public interface ArtistRepository {

    Optional<Artist> findByMBID(String mbid);

    void save(Artist artist);
    void save(ReleaseGroup releaseGroup);
    void save(References reference);

    Collection<ReleaseGroup> findByActorMBID(String mbid);

    Optional<ReleaseGroup> findByReleaseGroupMBID(String mbid);

    ReleaseGroup enrichReleaseGroup(ReleaseGroup releaseGroup);
}
