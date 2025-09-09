package org.lseg.report;

import org.lseg.model.JobSession;

import java.time.Duration;
import java.util.List;

public abstract class AbstractReportGenerator implements ReportGenerator {

    protected static final Duration WARNING_THRESHOLD = Duration.ofMinutes(5);
    protected static final Duration ERROR_THRESHOLD = Duration.ofMinutes(10);

    protected String formatJobSession(JobSession session) {
        Duration duration = session.getDuration();

        return String.format("%s (%s) - Duration: %d seconds - %s",
                session.getJobName(),
                session.getJobId(),
                duration.getSeconds(),
                computeStatus(duration));
    }

    protected List<String> formatSessions(List<JobSession> sessions) {
        return sessions.stream()
                .map(this::formatJobSession)
                .toList();
    }

    private static String computeStatus(Duration duration) {
        String status;
        if (duration.compareTo(ERROR_THRESHOLD) > 0) {
            status = "ERROR";
        } else if (duration.compareTo(WARNING_THRESHOLD) > 0) {
            status = "WARNING";
        } else {
            status = "OK";
        }
        return status;
    }
}

