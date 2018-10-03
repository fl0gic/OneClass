package me.caden2k3.oneclass.data.util;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import me.caden2k3.oneclass.data.Properties;

/**
 * @author Caden Kriese
 *
 * Created on 10/3/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class UtilFile {

  /**
   * Reads text from a file.
   *
   * @param file The file to be read from.
   * @return A string with all the lines of the file.
   */
  public static String read(File file) {
    if (!file.isDirectory()) {
      try {
        if (Properties.OS_NAME.contains("win")) {
          return String.join("\r\n", Files.readAllLines(Paths.get(file.toURI())));
        } else {
          return String.join("\n", Files.readAllLines(Paths.get(file.toURI())));
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return null;
  }

  /**
   * Writes text to a file.
   *
   * @param string The string to be written to a file.
   * @param file The file to be written to.
   */
  public static void write(String string, File file) {
    if (!file.isDirectory()) {
      List<String> lines = Arrays.asList(string.split("\n"));
      if (Properties.OS_NAME.contains("win")) {
        lines = Arrays.asList(string.split("\n"));
      }
      Path path = Paths.get(file.toURI());
      try {
        Files.write(path, lines, Charset.forName("UTF-8"));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
