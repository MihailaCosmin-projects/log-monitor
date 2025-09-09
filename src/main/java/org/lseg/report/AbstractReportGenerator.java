package org.lseg.report;

import org.lseg.model.JobSession;

import java.time.Duration;
import java.util.List;

public abstract class AbstractReportGenerator implements ReportGenerator {

    private static final Duration WARNING_THRESHOLD = Duration.ofMinutes(5);
    private static final Duration ERROR_THRESHOLD = Duration.ofMinutes(10);

    public static final String ERROR = "ERROR";
    public static final String WARNING = "WARNING";
    public static final String OK = "OK";

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
        if (duration.compareTo(ERROR_THRESHOLD) > 0) {
            return ERROR;
        } else if (duration.compareTo(WARNING_THRESHOLD) > 0) {
            return WARNING;
        } else {
            return OK;
        }
    }
}

