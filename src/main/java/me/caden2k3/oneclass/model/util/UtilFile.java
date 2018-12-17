package me.caden2k3.oneclass.model.util;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import lombok.NonNull;
import me.caden2k3.oneclass.model.Properties;

/**
 * @author Caden Kriese
 *
 * Created on 10/3/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class UtilFile {

  private static Gson gson = new Gson();

  /**
   * Reads text from a file.
   *
   * @param file The file to be read from.
   * @return A string with all the lines of the file.
   */
  public @NonNull static String read(File file) {
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
  public @NonNull static void write(String string, File file) {
    if (!file.isDirectory()) {
      List<String> lines = Arrays.asList(string.split("\n"));
      if (Properties.OS_NAME.contains("win")) {
        lines = Arrays.asList(string.split("\r\n"));
      }

      Path path = Paths.get(file.toURI());
      try {
        Files.write(path, lines, Charset.forName("UTF-8"));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Writes an object to a file, serializing it with {@link com.google.gson.Gson#toJson(Object)}.
   *
   * @param object The object to be written to a file
   * @param file The file to be written to.
   */
  public @NonNull static void write(Object object, File file) {
    try {
      String fileText = UtilFile.read(file);
      String serializedObject = gson.toJson(object);

      if (fileText != null) {
        if (file.exists() && !fileText.equals(serializedObject)) {
          UtilFile.write(serializedObject, file);
        } else if (file.createNewFile()) {
          UtilFile.write(serializedObject, file);
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
