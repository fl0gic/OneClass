package me.caden2k3.oneclass.controller.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.model.Properties;

/**
 * @author Caden Kriese
 *
 * Created on 2018-12-10.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class UtilController {
  /**
   * Fully initializes & displays a FXML file.
   *
   * @see Controller
   * @param fxmlFile The name of the file to be loaded. (Excluding path, including .fxml)
   */
  public static void openFile(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(OneClass.class.getResource(Properties.VIEW_PATH+fxmlFile));
      Parent root = loader.load();
      ((Controller) loader.getController()).apply(root);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads the controller of a FXML file.
   *
   * @param fxmlFile The file to be loaded, located in resources/view/
   * @return The loaded FXML file in the form of a Parent.
   */
  public static Parent loadFile(String fxmlFile) {
    try {
      return new FXMLLoader(OneClass.class.getResource(Properties.VIEW_PATH+fxmlFile)).load();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
