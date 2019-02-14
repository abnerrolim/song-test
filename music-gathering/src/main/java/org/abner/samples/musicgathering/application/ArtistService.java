package org.abner.samples.musicgathering.application;

import org.abner.samples.musicgathering.domain.Artist;
import org.abner.samples.musicgathering.infrastructure.message.AsyncArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArtistService {
    private final ArtistRepository database;
    private final AsyncArtistService asyncArtistService;

    @Autowired
    public ArtistService(@Qualifier("database") ArtistRepository database
            , AsyncArtistService asyncArtistService) {
        this.database = database;
        this.asyncArtistService = asyncArtistService;
    }

    public Optional<Artist> findByMDIB(String mbid) {
        var optArtist = database.findByMBID(mbid);
        if (!optArtist.isPresent())
            asyncArtistService.startAsync(mbid);
        return optArtist;
    }

}
