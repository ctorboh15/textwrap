package com.example.textwrap.textwrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TextwrapApplication implements CommandLineRunner {

  @Autowired private ConfigurableApplicationContext context;

  @Value("${app.get.maxlength.input}")
  private boolean getMaxLengthInput;

  @Value("${app.max.character.length}")
  private int DEFAULT_MAX_LENGTH;

  public static void main(String[] args) {

    SpringApplication.run(TextwrapApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Scanner in = getInputScanner();
    int maxCharacterLength;
    if (getMaxLengthInput) {
      maxCharacterLength = getMaxCharacterLimitFromInput(in);
    } else {
      maxCharacterLength = DEFAULT_MAX_LENGTH;
    }
    List<String> results = doParsingReturnList(maxCharacterLength);
    if (results != null) {
      for (String s : results) {
        System.out.println("\n" + s);
      }
    }
  }

  /**
   * Returns the user inputted max character limit.
   *
   * @param in
   * @return
   */
  protected int getMaxCharacterLimitFromInput(Scanner in) {
    int maxCharacterLength;
    System.out.println("\nPlease input the desired maximum character length");
    while (true) {
      try {
        maxCharacterLength = Integer.parseInt(in.nextLine());
        break;
      } catch (NumberFormatException e) {
        System.out.println("\nMax character length is invalid");
      }
    }
    return maxCharacterLength;
  }

  /**
   * This method will run an infinite loop and continue parsing input until told to exit;
   *
   * @param in
   * @param maxCharacterLength
   */
  protected void doParsingInfinitely(Scanner in, int maxCharacterLength) {
    System.out.println("\npaste in your string you want to parse");

    // Get the program to continuously run until its killed.
    while (true) {
      String text = in.nextLine();

      // Handle empty input scanner read
      if (text.isEmpty()) {
        continue;
      }

      // if the user types exit or quit kill the program
      if (text.equalsIgnoreCase(":exit") | text.equalsIgnoreCase(":quit")) {
        System.exit(SpringApplication.exit(context));
      }

      String[] spl1 = text.split(" ");

      StringBuilder stringBuilder = getStringBuilder();
      int i = 0;

      while (i < spl1.length) {

        // check that the next word is not longer than the max characters allowed. If so fail
        // gracefully without killing the application
        if (spl1[i].length() > maxCharacterLength) {
          System.out.println(
              String.format(
                  "\n \n \nOne of the words in this string is over %s characters ... processing will stop.",
                  maxCharacterLength));
          stringBuilder.setLength(0);
          break;
        }

        if (canAddNextStringWithSpace(
            stringBuilder.length(), spl1[i].length(), maxCharacterLength)) {
          stringBuilder.append(spl1[i]).append(" ");
          i++;
          continue;
        } else if (canAddNextStringWithoutSpace(
            stringBuilder.length(), spl1[i].length(), maxCharacterLength)) {
          stringBuilder.append(spl1[i]);
          i++;
          continue;
        } else {
          System.out.println(stringBuilder.toString());
          stringBuilder.setLength(0);
          continue;
        }
      }

      if (stringBuilder.length() > 0) {
        System.out.println(stringBuilder.toString() + "\n\n");
      }
    }
  }

  /**
   * This method will return a list of string that adhere to the max character length.
   *
   * @param maxCharacterLength
   * @return
   */
  protected List<String> doParsingReturnList(int maxCharacterLength) {
    List<String> result = new ArrayList<>();

    System.out.println("\nPlease paste in your string");
    Scanner in = getInputScanner();
    String text = in.nextLine();

    String[] spl1 = text.split(" ");

    StringBuilder stringBuilder = getStringBuilder();
    int i = 0;

    while (i < spl1.length) {

      // check that the next word is not longer than the max characters allowed. If so fail
      // gracefully without killing the application
      if (spl1[i].length() > maxCharacterLength) {
        System.out.println(
            String.format(
                "\n\n\nOne of the words in this string is over %s characters ... processing will stop.",
                maxCharacterLength));
        return null;
      }

      if (canAddNextStringWithSpace(stringBuilder.length(), spl1[i].length(), maxCharacterLength)) {
        stringBuilder.append(spl1[i]).append(" ");
        i++;
        continue;
      } else if (canAddNextStringWithoutSpace(
          stringBuilder.length(), spl1[i].length(), maxCharacterLength)) {
        stringBuilder.append(spl1[i]);
        i++;
        continue;
      } else {
        result.add(stringBuilder.toString());
        stringBuilder.setLength(0);
        continue;
      }
    }

    if (stringBuilder.length() > 0) {
      result.add(stringBuilder.toString());
    }
    return result;
  }

  private StringBuilder getStringBuilder() {
    return new StringBuilder();
  }

  protected Scanner getInputScanner() {
    return new Scanner(System.in);
  }

  private boolean canAddNextStringWithSpace(
      int currentStringLength, int nextStringLength, int maxCharacterLength) {
    return currentStringLength + nextStringLength < maxCharacterLength;
  }

  private boolean canAddNextStringWithoutSpace(
      int currentStringLength, int nextStringLength, int maxCharacterLength) {
    return currentStringLength + nextStringLength == maxCharacterLength;
  }
}
