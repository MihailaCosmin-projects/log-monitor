package org.lseg.report;

import lombok.extern.slf4j.Slf4j;
import org.lseg.model.JobSession;

import java.util.List;

@Slf4j
public class TextReportGenerator extends AbstractReportGenerator {

    @Override
    public void generateReport(List<JobSession> sessions) {
        List<String> lines = formatSessions(sessions);
        lines.forEach(log::info);
    }
}