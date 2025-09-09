package org.lseg.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class LogEntry {
    private LocalTime timestamp;
    private JobType jobType;
    private String jobName;
    private LogEventType eventType;
    private String jobId;
}
