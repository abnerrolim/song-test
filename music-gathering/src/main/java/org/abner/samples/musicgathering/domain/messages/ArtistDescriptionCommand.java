package org.abner.samples.musicgathering.domain.messages;

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
}
