package org.lseg.service;

import org.lseg.model.JobSession;
import org.lseg.model.LogEntry;
import org.lseg.model.LogEventType;

import java.util.*;

import static java.util.Objects.nonNull;

public class JobLifecycleService {

    private final Map<String, JobSession> activeJobs = new HashMap<>();
    private final List<JobSession> completedJobs = new ArrayList<>();

    public void processEntries(List<LogEntry> entries) {
        entries.forEach(entry -> { LogEventType eventType = entry.getEventType();
            if (LogEventType.START.equals(eventType)) handleStartEntry(entry);
            if (LogEventType.END.equals(eventType)) handleEndEntry(entry);
        });
    }

    private void handleStartEntry(LogEntry entry) {
        String jobId = entry.getJobId();
        JobSession session = JobSession.builder()
                .jobId(jobId)
                .jobName(entry.getJobName())
                .jobType(entry.getJobType())
                .startTime(entry.getTimestamp())
                .build();

        activeJobs.put(jobId, session);
    }

    private void handleEndEntry(LogEntry entry) {
        JobSession session = activeJobs.remove(entry.getJobId());

        if (nonNull(session)) {
            session.setEndTime(entry.getTimestamp());
            completedJobs.add(session);
        }
    }

    public List<JobSession> getCompletedJobs() {
        return Collections.unmodifiableList(completedJobs);
    }
}
