package org.abner.samples.musicgathering.infrastructure.database;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "ARTIST_INFO_SOURCE")
//@Data
//@EqualsAndHashCode
public class ArtistInfoSourceDB {

    @Id
    @Column(name = "MBID")
    private String mbid;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTIST_MBID")
    private ArtistDB artist;

    @Column(name = "type")
    private String type;

    @Column(name = "resource")
    private String resource;


    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public ArtistDB getArtist() {
        return artist;
    }

    public void setArtist(ArtistDB artist) {
        this.artist = artist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistInfoSourceDB that = (ArtistInfoSourceDB) o;
        return mbid.equals(that.mbid) &&
                artist.equals(that.artist) &&
                Objects.equals(type, that.type) &&
                Objects.equals(resource, that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mbid, artist, type, resource);
    }
}
