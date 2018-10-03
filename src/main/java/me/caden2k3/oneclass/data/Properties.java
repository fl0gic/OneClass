package me.caden2k3.oneclass.data;

/**
 * Created by Caden Kriese on 10/1/18.
 *
 * License is specified by the distributor which this file was written for. Otherwise it can be
 * found in the LICENSE file. If there is no license file the code is then completely copyrighted
 * and you must contact me before using it IN ANY WAY.
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
