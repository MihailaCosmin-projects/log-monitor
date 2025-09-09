package org.lseg;

import lombok.extern.slf4j.Slf4j;
import org.lseg.model.JobSession;
import org.lseg.parser.CsvLogParser;
import org.lseg.parser.LogFileReader;
import org.lseg.report.FileReportGenerator;
import org.lseg.report.ReportGenerator;
import org.lseg.report.TextReportGenerator;
import org.lseg.service.JobLifecycleService;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class LogMonitor {

    private static final List<ReportGenerator> reportGenerators = Arrays.asList(
            new TextReportGenerator(),
            new FileReportGenerator(Path.of("log_report.txt")));

    public static void main(String[] args) {
        try {
            Path logFile = Path.of("src/main/resources/logs.log");

            LogFileReader reader = new LogFileReader(new CsvLogParser());
            List<JobSession> jobSessions = new JobLifecycleService() {{
                processEntries(reader.read(logFile));
            }}.getCompletedJobs();

            for (ReportGenerator generator : reportGenerators) {
                log.info("Generating report using <{}>", generator.getClass().getSimpleName());
                generator.generateReport(jobSessions);
            }

            log.info("All reports generated successfully.");
        } catch (Exception e) {
            log.error("Failed to process log file: {}", e.getMessage(), e);
        }
    }
}
