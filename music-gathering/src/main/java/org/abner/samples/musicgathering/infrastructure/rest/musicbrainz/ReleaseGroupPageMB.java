package org.abner.samples.musicgathering.infrastructure.rest.musicbrainz;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;

//@Data
public class ReleaseGroupPageMB {

    @JsonProperty("release-group-count")
    private Integer releaseGroupCount;

    @JsonProperty("release-group-offset")
    private Integer offset;

    @JsonProperty("release-groups")
    private List<ReleaseGroupMB> releaseGroups;

    public Integer getReleaseGroupCount() {
        return releaseGroupCount;
    }

    public void setReleaseGroupCount(Integer releaseGroupCount) {
        this.releaseGroupCount = releaseGroupCount;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public List<ReleaseGroupMB> getReleaseGroups() {
        return releaseGroups;
    }

    public void setReleaseGroups(List<ReleaseGroupMB> releaseGroups) {
        this.releaseGroups = releaseGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReleaseGroupPageMB that = (ReleaseGroupPageMB) o;
        return Objects.equals(releaseGroupCount, that.releaseGroupCount) &&
                Objects.equals(offset, that.offset) &&
                Objects.equals(releaseGroups, that.releaseGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(releaseGroupCount, offset, releaseGroups);
    }

    //    @Data
    public static class ReleaseGroupMB {
        private String id;
        private String title;
        private String coverArtImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoverArtImage() {
            return coverArtImage;
        }

        public void setCoverArtImage(String coverArtImage) {
            this.coverArtImage = coverArtImage;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ReleaseGroupMB that = (ReleaseGroupMB) o;
            return Objects.equals(id, that.id) &&
                    Objects.equals(title, that.title) &&
                    Objects.equals(coverArtImage, that.coverArtImage);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, title, coverArtImage);
        }
    }

}
