package dev.appkr.kata;

import java.time.LocalDateTime;

public class LogEntry {

  private LocalDateTime dateTime;
  private String input;
  private String transcoder;
  private String output;

  public LogEntry(String input, String transcoder, String output) {
    this.dateTime = LocalDateTime.now();
    this.input = input;
    this.transcoder = transcoder;
    this.output = output;
  }

  public LogEntry() {}

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getTranscoder() {
    return transcoder;
  }

  public void setTranscoder(String transcoder) {
    this.transcoder = transcoder;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

  @Override
  public String toString() {
    return "LogEntry{" +
        "dateTime=" + dateTime +
        ", input='" + input + '\'' +
        ", transcoder='" + transcoder + '\'' +
        ", output='" + output + '\'' +
        '}';
  }
}
