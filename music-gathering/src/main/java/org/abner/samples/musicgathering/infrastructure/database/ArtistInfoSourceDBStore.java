package org.abner.samples.musicgathering.infrastructure.database;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface ArtistInfoSourceDBStore extends CrudRepository<ArtistInfoSourceDB, String> {

    Collection<ArtistInfoSourceDB> findByArtist(ArtistDB artistDB);
}
