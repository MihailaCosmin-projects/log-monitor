package org.lseg.model;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;

import static java.util.Objects.nonNull;

@Data
@Builder
public class JobSession {
    private String jobId;
    private String jobName;
    private JobType jobType;
    private LocalTime startTime;
    private LocalTime endTime;

    public Duration getDuration() {
        if (nonNull(startTime) && nonNull(endTime)) {
            return Duration.between(startTime, endTime);
        }
        return Duration.ZERO;
    }
}

