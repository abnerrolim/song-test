package org.abner.samples.musicgathering.infrastructure.database;

import org.abner.samples.musicgathering.domain.Artist;
import org.abner.samples.musicgathering.domain.References;
import org.abner.samples.musicgathering.domain.ReleaseGroup;
import org.abner.samples.musicgathering.domain.SyncStatus;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

public final class ModelMapperUtils {
    private ModelMapperUtils(){}

    public static Artist map(ArtistDB db){
        var artist = new Artist();
        artist.setMbid(db.getMbid());
        artist.setDescription(db.getDescription());
        artist.setName(db.getName());
        artist.setSyncStatus(infoStatus(db));
        return artist;
    }

    public static ReleaseGroup map(ReleaseGroupDB releaseGroupDB){
        var releaseGroup = new ReleaseGroup();
        releaseGroup.setImageResource(releaseGroupDB.getImageResource());
        releaseGroup.setMbid(releaseGroupDB.getMbid());
        releaseGroup.setTitle(releaseGroupDB.getTitle());
        releaseGroup.setActorMbid(releaseGroupDB.getArtist().getMbid());
        return releaseGroup;
    }

    public static References map(ArtistInfoSourceDB artistInfoSourceDB){
        var ref = new References();
        ref.setResource(artistInfoSourceDB.getResource());
        ref.setType(artistInfoSourceDB.getType());
        ref.setId(artistInfoSourceDB.getMbid());
        ref.setActorMbid(artistInfoSourceDB.getArtist().getMbid());
        return ref;
    }

    public static SyncStatus infoStatus(ArtistDB artistDB){
        if(artistDB.getInfoCompleted() && !StringUtils.isEmpty(artistDB.getDescription()))
            return SyncStatus.FULL;
        if(!artistDB.getInfoCompleted() && StringUtils.isEmpty(artistDB.getDescription()))
            return SyncStatus.RELEASE_AND_DESCRIPTION_INCOMPLETE;
        if(!artistDB.getInfoCompleted())
            return SyncStatus.RELEASE_INCOMPLETE;
        return SyncStatus.DESCRIPTION_INCOMPLETE;
    }

    public static ArtistDB map(Artist artist){
        var db = new ArtistDB();
        db.setDescription(artist.getDescription());
        db.setMbid(artist.getMbid());
        db.setName(artist.getName());
        db.setInfoCompleted(artist.getSyncStatus().isCompleted());
        return db;
    }

    public static ReleaseGroupDB map(ReleaseGroup releaseGroup){
        var relGroupDB = new ReleaseGroupDB();
        relGroupDB.setArtist(new ArtistDB(releaseGroup.getActorMbid()));
        relGroupDB.setTitle(releaseGroup.getTitle());
        relGroupDB.setImageResource(releaseGroup.getImageResource());
        relGroupDB.setMbid(releaseGroup.getMbid());
        return relGroupDB;
    }

    public static ArtistInfoSourceDB map(References reference){
        var infoSource = new ArtistInfoSourceDB();
        infoSource.setArtist(new ArtistDB(reference.getActorMbid()));
        infoSource.setMbid(reference.getId());
        infoSource.setResource(reference.getResource());
        infoSource.setType(reference.getType());
        return infoSource;
    }
}
