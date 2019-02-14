package org.abner.samples.musicgathering.domain.messages;

public class ReleaseCoverArtCommand {

    private String mbid;

    public ReleaseCoverArtCommand(String mdib) {
        this.mbid = mdib;
    }

    public ReleaseCoverArtCommand(){};

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
