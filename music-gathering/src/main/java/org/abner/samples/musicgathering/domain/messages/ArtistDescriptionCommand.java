package org.abner.samples.musicgathering.domain.messages;

import java.util.Objects;

public class ArtistDescriptionCommand {

    private String mbid;

    public ArtistDescriptionCommand(String mdib) {
        this.mbid = mdib;
    }

    public ArtistDescriptionCommand() {
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    @Override
    public String toString() {
        return String.format("mbid{mbid=%s}", getMbid());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistDescriptionCommand that = (ArtistDescriptionCommand) o;
        return Objects.equals(mbid, that.mbid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mbid);
    }
}
