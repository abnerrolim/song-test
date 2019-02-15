package org.abner.samples.musicgathering.infrastructure.rest.musicbrainz;


import java.util.List;
import java.util.Objects;

//@Data
public class ArtistLookupMB {
    private String id;
    private String name;
    private List<Relation> relations;
    private String externalDescription;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    public String getExternalDescription() {
        return externalDescription;
    }

    public void setExternalDescription(String externalDescription) {
        this.externalDescription = externalDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistLookupMB that = (ArtistLookupMB) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(relations, that.relations) &&
                Objects.equals(externalDescription, that.externalDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, relations, externalDescription);
    }

    //    @Data
    public static class Relation {
        private String type;
        private RelationUrl url;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public RelationUrl getUrl() {
            return url;
        }

        public void setUrl(RelationUrl url) {
            this.url = url;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Relation relation = (Relation) o;
            return Objects.equals(type, relation.type) &&
                    Objects.equals(url, relation.url);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, url);
        }
    }

    //    @Data
    public static class RelationUrl {
        private String id;
        private String resource;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
            RelationUrl url = (RelationUrl) o;
            return Objects.equals(id, url.id) &&
                    Objects.equals(resource, url.resource);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, resource);
        }
    }
}
