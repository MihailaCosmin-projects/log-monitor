package org.lseg.report;

import org.junit.jupiter.api.Test;
import org.lseg.model.JobSession;
import org.lseg.model.JobType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.lseg.report.AbstractReportGenerator.WARNING;

class FileReportGeneratorTest {

    @Test
    void whenGenerateReport_thenFileCreatedWithContent() throws IOException {
        Path tempFile = Files.createTempFile("report", ".txt");
        FileReportGenerator generator = new FileReportGenerator(tempFile);

        JobSession session = JobSession.builder()
                .jobId("123")
                .jobName("scheduled task 001")
                .jobType(JobType.SCHEDULED_TASK)
                .startTime(LocalTime.of(10,0))
                .endTime(LocalTime.of(10,6))
                .build();

        generator.generateReport(List.of(session));

        String content = Files.readString(tempFile);
        assertTrue(content.contains("scheduled task 001"));
        assertTrue(content.contains(WARNING));

        Files.deleteIfExists(tempFile);
    }
}

