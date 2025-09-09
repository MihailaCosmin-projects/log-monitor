# Log Monitor

**Log Monitor** is a Java-based application that processes log files to track job execution and generate reports. It is designed to handle both scheduled tasks and background jobs, providing insights into their durations and statuses.

## Business Logic

1. **Log Parsing**
    - Reads a log file (`logs.log`) line by line.
    - Each line contains a timestamp, job name, event type (`START` or `END`), and job ID.
    - Lines are parsed into `LogEntry` objects with `JobType` (scheduled task / background job) and `LogEventType`.

2. **Job Lifecycle Tracking**
    - `JobLifecycleService` manages active jobs and completed jobs.
    - START events create a new `JobSession` and store it in active jobs.
    - END events complete the session, compute duration, and move it to completed jobs.

3. **Report Generation**
    - `ReportGenerator` interface allows multiple report formats using Strategy Pattern.
    - `TextReportGenerator` logs results to the console.
    - `FileReportGenerator` writes results to a text file.
    - Each report shows job name, ID, duration in seconds, and status:
        - `OK` (short jobs)
        - `WARNING` (medium duration)
        - `ERROR` (long duration)

## Technologies
- Java 17
- Maven
- Lombok (for boilerplate reduction)
- SLF4J (logging)
- JUnit 5 (unit tests)

## Usage
1. Place `logs.log` in `src/main/resources` path.
2. Use `LogFileReader` with `CsvLogParser` to parse log lines.
3. Feed parsed entries into `JobLifecycleService`.
4. Generate reports using `TextReportGenerator` or `FileReportGenerator`.

