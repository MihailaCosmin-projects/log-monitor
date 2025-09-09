package org.lseg.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lseg.model.JobSession;
import org.lseg.model.LogEntry;
import org.lseg.model.LogEventType;
import org.lseg.model.JobType;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JobLifecycleServiceTest {

    private JobLifecycleService service;

    @BeforeEach
    void setUp() {
        service = new JobLifecycleService();
    }

    @Test
    void whenProcessValidStartAndEndEntries_thenCompletedJobsReturned() {
        LogEntry start = LogEntry.builder()
                .jobId("123")
                .jobName("scheduled task 001")
                .jobType(JobType.SCHEDULED_TASK)
                .eventType(LogEventType.START)
                .timestamp(LocalTime.of(10, 0, 0))
                .build();

        LogEntry end = LogEntry.builder()
                .jobId("123")
                .jobName("scheduled task 001")
                .jobType(JobType.SCHEDULED_TASK)
                .eventType(LogEventType.END)
                .timestamp(LocalTime.of(10, 5, 0))
                .build();

        service.processEntries(List.of(start, end));
        List<JobSession> completed = service.getCompletedJobs();

        assertEquals(1, completed.size());
        JobSession session = completed.get(0);
        assertEquals("123", session.getJobId());
        assertEquals(300, session.getDuration().getSeconds());
    }

    @Test
    void whenEndEntryWithoutStart_thenShouldNotAddJob() {
        LogEntry end = LogEntry.builder()
                .jobId("999")
                .jobName("scheduled task 999")
                .jobType(JobType.SCHEDULED_TASK)
                .eventType(LogEventType.END)
                .timestamp(LocalTime.of(10, 10, 0))
                .build();

        service.processEntries(List.of(end));
        assertTrue(service.getCompletedJobs().isEmpty());
    }
}

