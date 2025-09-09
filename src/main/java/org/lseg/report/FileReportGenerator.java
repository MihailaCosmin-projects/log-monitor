package org.lseg.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lseg.model.JobSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FileReportGenerator extends AbstractReportGenerator {

    private final Path outputFile;

    @Override
    public void generateReport(List<JobSession> sessions) {
        List<String> lines = formatSessions(sessions);
        try {
            Files.write(outputFile, lines);
        } catch (IOException e) {
            log.error("Failed to write report: {}", e.getMessage(), e);
        }
    }
}

