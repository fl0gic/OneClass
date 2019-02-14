package me.caden2k3.oneclass.model;

import javafx.scene.image.Image;
import me.caden2k3.oneclass.OneClass;

/**
 * @author Caden Kriese The {@link Properties} class Stores static properties for the application,
 * such as file locations, and system information.
 *
 * Created on 10/1/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class Properties {

    //OS
    public static final String USER_HOME = System.getProperty("user.home");
    public static final String OS_NAME = System.getProperty("os.name");
    //APP
    //TODO find a way to convert data folder paths from MacOS to windows.
    public static final String DATA_FOLDER_PATH = USER_HOME + "/Library/Application Support/OneClass";
    public static final String LOGGING_FOLDER_PATH = DATA_FOLDER_PATH + "/logs";
    public static final String USERS_FOLDER_PATH = DATA_FOLDER_PATH + "/users";
    public static final String VIEW_PATH = "/resources/view/";
    public static final String IMAGE_PATH = "/resources/image/";
    public static String OS_VERSION = System.getProperty("os.version");
    public static String OS_ARCH = System.getProperty("os.arch");
    public static final Image APPLICATION_ICON = new Image(OneClass.class.getResourceAsStream(IMAGE_PATH+"icon.png"));
}
