package dev.appkr.kata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

  public static void main(String[] args) throws IOException {

//    final InMemoryLogger logger = new InMemoryLogger();
    final FileLogger logger = new FileLogger();
    final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      message(">>> 원본 문자열을 입력하세요 (종료하려면 exit 를 입력하고 Enter):");
      final String input = reader.readLine();
      if (input.startsWith("exit")) {
        break;
      }
      if (input.length() < 1) {
        error("한 문자 이상을 입력해야 합니다");
        continue;
      }

      message(">>> 주어진 문자열을 변경할 트랜스코더 선택하세요 [shuffle(s)|reverse(r)]: ");
      final String transcoder = reader.readLine();
      String output = null;
      try {
        output = new TranscodingService(TranscoderFactory.getInstance(transcoder)).transcode(input);
        logger.log(input, transcoder, output);
      } catch (Exception e) {
        e.printStackTrace();
        error(e.getMessage());
        continue;
      }

      message("=> " + output);
    }

    logger.getLogs().forEach(System.out::println);
  }

  static void message(String message) {
    System.out.println(message);
  }

  static void error(String message) {
    System.out.println("\u001B[31m" + message + "\u001B[0m");
  }
}
