package org.abner.samples.musicgathering.infrastructure.database;

import org.springframework.data.repository.CrudRepository;

public interface ArtistDBStore extends CrudRepository<ArtistDB, String> {
}
