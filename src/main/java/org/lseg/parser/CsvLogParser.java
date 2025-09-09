package org.lseg.parser;

import org.lseg.model.LogEntry;
import org.lseg.model.JobType;
import org.lseg.model.LogEventType;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvLogParser implements LogParser {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public List<LogEntry> parse(List<String> lines) {
        return lines.stream()
                .map(line -> line.split(","))
                .filter(entryArray -> entryArray.length == 4)
                .map(entryArray -> LogEntry.builder()
                        .timestamp(LocalTime.parse(entryArray[0].trim(), TIME_FORMAT))
                        .jobName(entryArray[1].trim())
                        .jobType(JobType.fromString(entryArray[1].trim()))
                        .eventType(LogEventType.fromString(entryArray[2].trim()))
                        .jobId(entryArray[3].trim())
                        .build())
                .toList();
    }
}

