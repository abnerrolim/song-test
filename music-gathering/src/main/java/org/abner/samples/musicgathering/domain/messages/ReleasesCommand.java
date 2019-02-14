package org.abner.samples.musicgathering.domain.messages;

public class ReleasesCommand {

    private String mbid;

    public ReleasesCommand(String mdib) {
        this.mbid = mdib;
    }

    public ReleasesCommand(){};

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
