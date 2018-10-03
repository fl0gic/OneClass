package me.caden2k3.oneclass.data;

/**
 * @author Caden Kriese
 * The {@link Properties} class Stores static properties for the application, such as file locations, and system information.
 *
 * Created on 10/1/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class Properties {

  //APP
  public static String DATA_FOLDER_PATH =
      System.getProperty("user.home") + "/Library/Application\\ Support/OneClass";
  public static String USERS_FOLDER_PATH =
      System.getProperty("user.home") + "/Library/Application\\ Support/OneClass/Users";

  //OS
  public static String OS_NAME = System.getProperty("os.name");
  public static String OS_VERSION = System.getProperty("os.version");
  public static String OS_ARCH = System.getProperty("os.arch");
}
