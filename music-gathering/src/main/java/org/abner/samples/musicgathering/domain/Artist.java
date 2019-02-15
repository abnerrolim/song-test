package org.abner.samples.musicgathering.domain;


import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

//@Data
public class Artist {
    private String mbid;
    private String name;
    private String description;
    private Collection<ReleaseGroup> releasesGroup = new ArrayList<>();
    private Collection<References> references = new ArrayList<>();
    private SyncStatus syncStatus;

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

    public Collection<ReleaseGroup> getReleasesGroup() {
        return releasesGroup;
    }

    public void setReleasesGroup(Collection<ReleaseGroup> releasesGroup) {
        this.releasesGroup = releasesGroup;
    }

    public Collection<References> getReferences() {
        return references;
    }

    public void setReferences(Collection<References> references) {
        this.references = references;
    }

    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }

    public void syncStatus() {
        if (StringUtils.isEmpty(description) && CollectionUtils.isEmpty(releasesGroup))
            syncStatus = SyncStatus.RELEASE_AND_DESCRIPTION_INCOMPLETE;
        else if (StringUtils.isEmpty(description))
            syncStatus = SyncStatus.DESCRIPTION_INCOMPLETE;
        else if (CollectionUtils.isEmpty(releasesGroup))
            syncStatus = SyncStatus.RELEASE_INCOMPLETE;
        else
            syncStatus = SyncStatus.FULL;
    }
}
