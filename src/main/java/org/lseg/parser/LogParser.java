package org.lseg.parser;

import org.lseg.model.LogEntry;

import java.util.List;

public interface LogParser {
    List<LogEntry> parse(List<String> lines);
}
