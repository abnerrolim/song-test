package org.abner.samples.musicgathering.infrastructure.database;


import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "ARTIST")
//@Data
//@EqualsAndHashCode
public class ArtistDB {

    @Id
    @Column(name = "MBID")
    private String mbid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LAST_UPDATE")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @Column(name = "IS_INFO_COMPLETE")
    private Boolean infoCompleted;

    public ArtistDB(){}

    public ArtistDB(String mbid){
        this.mbid = mbid;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Boolean getInfoCompleted() {
        return infoCompleted;
    }

    public void setInfoCompleted(Boolean infoCompleted) {
        this.infoCompleted = infoCompleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistDB artistDB = (ArtistDB) o;
        return Objects.equals(mbid, artistDB.mbid) &&
                Objects.equals(name, artistDB.name) &&
                Objects.equals(description, artistDB.description) &&
                Objects.equals(lastUpdate, artistDB.lastUpdate) &&
                Objects.equals(infoCompleted, artistDB.infoCompleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mbid, name, description, lastUpdate, infoCompleted);
    }
}
