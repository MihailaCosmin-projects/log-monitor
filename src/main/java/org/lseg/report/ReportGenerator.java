package org.lseg.report;

import org.lseg.model.JobSession;

import java.util.List;

public interface ReportGenerator {
    void generateReport(List<JobSession> sessions);
}

