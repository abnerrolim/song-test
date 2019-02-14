package org.abner.samples.musicgathering.domain;

//@Data
public class ReleaseGroup {
    private String actorMbid;
    private String mbid;
    private String title;
    private String imageResource;

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

    public String getActorMbid() {
        return actorMbid;
    }

    public void setActorMbid(String actorMbid) {
        this.actorMbid = actorMbid;
    }

}
