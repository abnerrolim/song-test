package org.abner.samples.musicgathering.domain;

import java.util.Objects;

//@Data
public class References {
    private String id;
    private String type;
    private String resource;
    private String actorMbid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getActorMbid() {
        return actorMbid;
    }

    public void setActorMbid(String actorMbid) {
        this.actorMbid = actorMbid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        References that = (References) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type) &&
                Objects.equals(resource, that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, resource);
    }
}
