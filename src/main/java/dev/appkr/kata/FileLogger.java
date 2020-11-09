package dev.appkr.kata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileLogger implements Logger {

  private static final String FILE_NAME = "socket.tmp";
  private static final ObjectMapper mapper = new ObjectMapper();
  static {
    /**
     * Caused by: com.fasterxml.jackson.databind.exc.MismatchedInputException: Expected array or string.
     *  at [Source: (String)"[{"dateTime":{"month":"NOVEMBER","dayOfWeek":"MONDAY","dayOfYear":314,"nano":229264000,"year":2020,"monthValue":11,"dayOfMonth":9,"hour":14,"minute":35,"second":37,"chronology":{"id":"ISO","calendarType":"iso8601"}},"input":"abcd","transcoder":"r","output":"dcba"}]"; line: 1, column: 14] (through reference chain: java.util.ArrayList[0]->dev.appkr.kata.LogEntry["dateTime"])
     * @see https://github.com/HomoEfficio/dev-tips/blob/master/Java8-LocalDateTime-Jackson-%EC%A7%81%EB%A0%AC%ED%99%94-%EB%AC%B8%EC%A0%9C.md
     */
    SimpleModule module = new SimpleModule();
    module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    mapper.registerModule(module);
  }

  public FileLogger() {
    File file = new File(FILE_NAME);
    if (file.exists()) {
      // 기존 데이터 초기화
      file.delete();
    }

    if (!file.exists()) {
      try {
        file.createNewFile();
        file.setWritable(true);
      } catch (IOException e) {
        throw new RuntimeException("파일을 만들 수 없습니다", e);
      }
    }
  }

  @Override
  public void log(String input, String transcoder, String output) {
    List<LogEntry> logs = getLogs();
    logs.add(new LogEntry(input, transcoder, output));

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
      final String s = mapper.writeValueAsString(logs);
      writer.write(s);
    } catch (IOException e) {
      throw new RuntimeException("파일에 쓸 수 없습니다", e);
    }
  }

  @Override
  public List<LogEntry> getLogs() {
    List<LogEntry> logs = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
      final String s = reader.readLine();
      if (s == null) {
        return logs;
      }
      logs = mapper.readValue(s, new TypeReference<ArrayList<LogEntry>>() {});
    } catch (IOException e) {
      throw new RuntimeException("파일을 읽을 수 없습니다", e);
    }

    return logs;
  }
}
