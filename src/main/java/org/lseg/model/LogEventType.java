package org.lseg.model;

public enum LogEventType {
    START,
    END;

    public static LogEventType fromString(String value) {
        return switch (value.toUpperCase()) {
            case "START" -> START;
            case "END" -> END;
            default -> throw new IllegalArgumentException("Unknown event type: " + value);
        };
    }
}

