package org.abner.samples.musicgathering.infrastructure.database;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReleaseGroupDBStore extends CrudRepository<ReleaseGroupDB, String> {

    List<ReleaseGroupDB> findByArtist(ArtistDB artistDB);

}
