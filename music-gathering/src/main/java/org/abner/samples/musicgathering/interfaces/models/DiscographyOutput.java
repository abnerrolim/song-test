package org.abner.samples.musicgathering.interfaces.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.abner.samples.musicgathering.domain.Artist;
import org.abner.samples.musicgathering.domain.References;
import org.abner.samples.musicgathering.domain.ReleaseGroup;

import java.util.Collection;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
//@Data
public class DiscographyOutput {
    private ArtistitOutput artist;
    private String message;

    public static DiscographyOutput from(Artist artist) {
        var artistOut = new ArtistitOutput();
        artistOut.setReleasesGroup(artist.getReleasesGroup().stream()
                .map(ReleaseGroupOutput::from)
                .collect(Collectors.toList())
        );
        artistOut.setReferences(artist.getReferences().stream()
                .map(ReferencesOutput::from)
                .collect(Collectors.toList())
        );
        artistOut.setDescription(artist.getDescription());
        artistOut.setMbid(artist.getMbid());
        artistOut.setName(artist.getName());
        var disco = new DiscographyOutput();
        disco.setArtist(artistOut);
        return disco;
    }

    public static DiscographyOutput from() {
        var disco = new DiscographyOutput();
        disco.setMessage("Unable to find artist MBID by now. We are collecting information and if this one exists, should by here soon. Try in some minutes");
        return disco;
    }

    public ArtistitOutput getArtist() {
        return artist;
    }

    public void setArtist(ArtistitOutput artist) {
        this.artist = artist;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //    @Data
    public static class ArtistitOutput {
        private String mbid;
        private String name;
        private String description;
        private Collection<ReleaseGroupOutput> releasesGroup;
        private Collection<ReferencesOutput> references;

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

        public Collection<ReleaseGroupOutput> getReleasesGroup() {
            return releasesGroup;
        }

        public void setReleasesGroup(Collection<ReleaseGroupOutput> releasesGroup) {
            this.releasesGroup = releasesGroup;
        }

        public Collection<ReferencesOutput> getReferences() {
            return references;
        }

        public void setReferences(Collection<ReferencesOutput> references) {
            this.references = references;
        }
    }

    //    @Data
    public static class ReleaseGroupOutput {
        private String mbid;
        private String title;
        private String imageResource;

        static ReleaseGroupOutput from(ReleaseGroup releaseGroup) {
            var rg = new ReleaseGroupOutput();
            rg.setImageResource(releaseGroup.getImageResource());
            rg.setMbid(releaseGroup.getMbid());
            rg.setTitle(releaseGroup.getTitle());
            return rg;
        }

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
    }

    //    @Data
    public static class ReferencesOutput {
        private String id;
        private String type;
        private String resource;

        static ReferencesOutput from(References references) {
            var ref = new ReferencesOutput();
            ref.setId(references.getId());
            ref.setResource(references.getResource());
            ref.setType(references.getType());
            return ref;
        }

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
    }
}
