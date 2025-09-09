package org.lseg.parser;

import lombok.RequiredArgsConstructor;
import org.lseg.model.LogEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
public class LogFileReader {

    private final LogParser parser;

    public List<LogEntry> read(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        return parser.parse(lines);
    }
}
