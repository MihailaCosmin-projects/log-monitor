package org.lseg.parser;

import org.junit.jupiter.api.Test;
import org.lseg.model.LogEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogFileReaderTest {

    @Test
    void whenValidFile_thenReadReturnsParsedEntries() throws IOException {
        Path tempFile = Files.createTempFile("logs", ".log");
        List<String> lines = List.of(
                "11:35:23,scheduled task 032,START,37980",
                "11:35:56,scheduled task 032,END,37980"
        );
        Files.write(tempFile, lines);

        LogFileReader reader = new LogFileReader(new CsvLogParser());
        List<LogEntry> entries = reader.read(tempFile);

        assertEquals(2, entries.size());

        Files.deleteIfExists(tempFile);
    }
}

