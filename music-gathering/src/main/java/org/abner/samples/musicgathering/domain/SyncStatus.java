package org.abner.samples.musicgathering.domain;

public enum SyncStatus {
    FULL,
    RELEASE_INCOMPLETE,
    DESCRIPTION_INCOMPLETE,
    RELEASE_AND_DESCRIPTION_INCOMPLETE;

    public Boolean isCompleted() {
        return this.equals(SyncStatus.FULL);
    }
}
