package me.caden2k3.oneclass.model.util;

import lombok.Cleanup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author Caden Kriese
 *
 * Created on 2019-02-01.
 *
 * This code is copyright © Caden Kriese 2019
 */
public class UtilReader {
  public static String readFrom(String url) throws IOException {
    @Cleanup InputStream is = new URL(url).openStream();
    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }
}
