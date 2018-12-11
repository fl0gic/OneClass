package me.caden2k3.oneclass.model;

/**
 * @author Caden Kriese The {@link Properties} class Stores static properties for the application,
 * such as file locations, and system information.
 *
 * Created on 10/1/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class Properties {

  //APP
  public static final String DATA_FOLDER_PATH =
      System.getProperty("user.home") + "/Library/Application Support/OneClass";
  public static final String USERS_FOLDER_PATH =
      System.getProperty("user.home") + "/Library/Application Support/OneClass/Users";
  public static final String VIEW_PATH = "./src/main/resources/view/";

  //OS
  public static final String OS_NAME = System.getProperty("os.name");
  public static String OS_VERSION = System.getProperty("os.version");
  public static String OS_ARCH = System.getProperty("os.arch");
}
