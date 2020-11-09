package dev.appkr.kata;

import java.util.List;

public interface Logger {

  void log(String input, String transcoder, String output);
  List<LogEntry> getLogs();
}
