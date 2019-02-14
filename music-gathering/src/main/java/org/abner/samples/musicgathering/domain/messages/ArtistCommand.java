package org.abner.samples.musicgathering.domain.messages;

public class ArtistCommand {

    private String mbid;

    public ArtistCommand(String mdib) {
        this.mbid = mdib;
    }

    public ArtistCommand() {
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
}
