package org.lseg.parser;

import org.junit.jupiter.api.Test;
import org.lseg.model.LogEntry;
import org.lseg.model.LogEventType;
import org.lseg.model.JobType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvLogParserTest {

    private final CsvLogParser parser = new CsvLogParser();

    @Test
    void whenValidLines_thenParseReturnsCorrectLogEntries() {
        List<String> lines = List.of(
                "11:35:23,scheduled task 032,START,37980",
                "11:35:56,scheduled task 032,END,37980"
        );

        List<LogEntry> entries = parser.parse(lines);

        assertEquals(2, entries.size());
        LogEntry first = entries.get(0);
        assertEquals(JobType.SCHEDULED_TASK, first.getJobType());
        assertEquals(LogEventType.START, first.getEventType());
        assertEquals("scheduled task 032", first.getJobName());
        assertEquals("37980", first.getJobId());
    }

    @Test
    void whenInvalidLine_thenParseSkipsIt() {
        List<String> lines = List.of(
                "11:35:12,scheduled task 032,START,37980",
                "11:35:23,scheduled task 032,END,37980, INVALID LINE"
        );

        List<LogEntry> entries = parser.parse(lines);

        assertEquals(1, entries.size());
        assertEquals("11:35:12", entries.get(0).getTimestamp().toString());
    }

    @Test
    void whenUnknownJobType_thenShouldThrow() {
        List<String> lines = List.of(
                "11:35:23,unknown job,START,12345"
        );

        assertThrows(IllegalArgumentException.class, () -> parser.parse(lines));
    }

    @Test
    void whenUnknownEventType_thenShouldThrow() {
        List<String> lines = List.of(
                "11:35:23,scheduled task 032,UNKNOWN,37980"
        );

        assertThrows(IllegalArgumentException.class, () -> parser.parse(lines));
    }
}

