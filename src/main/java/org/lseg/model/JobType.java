package org.lseg.model;

import lombok.NonNull;


public enum JobType {
    SCHEDULED_TASK,
    BACKGROUND_JOB;

    public static JobType fromString(@NonNull String value) {
        if (value.toLowerCase().startsWith("scheduled task")) {
            return SCHEDULED_TASK;
        } else if (value.toLowerCase().startsWith("background job")) {
            return BACKGROUND_JOB;
        }
        throw new IllegalArgumentException("Unknown job type: " + value);
    }
}
