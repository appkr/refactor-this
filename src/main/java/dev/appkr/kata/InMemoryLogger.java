package dev.appkr.kata;

import java.util.ArrayList;
import java.util.List;

public class InMemoryLogger implements Logger {

  private List<LogEntry> logs = new ArrayList<>();

  @Override
  public void log(String input, String transcoder, String output) {
    logs.add(new LogEntry(input, transcoder, output));
  }

  @Override
  public List<LogEntry> getLogs() {
    return logs;
  }
}
