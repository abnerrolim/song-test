package org.abner.samples.musicgathering.infrastructure.database;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "RELEASE_GROUP")
//@Data
//@EqualsAndHashCode
public class ReleaseGroupDB {
    @Id
    @Column(name = "MBID")
    private String mbid;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "IMAGE_RESOURCE")
    private String imageResource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ARTIST_MBID")
    private ArtistDB artist;

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public ArtistDB getArtist() {
        return artist;
    }

    public void setArtist(ArtistDB artist) {
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReleaseGroupDB that = (ReleaseGroupDB) o;
        return mbid.equals(that.mbid) &&
                title.equals(that.title) &&
                imageResource.equals(that.imageResource) &&
                artist.equals(that.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mbid, title, imageResource, artist);
    }
}
