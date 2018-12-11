package me.caden2k3.oneclass.controller.util;

import java.nio.file.Paths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import me.caden2k3.oneclass.controller.setup.Controller;
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
   * Fully initializes a controller.
   *
   * @see me.caden2k3.oneclass.controller.setup.Controller
   * @param fxmlFile The name of the file to be loaded. (Excluding path, including .fxml)
   */
  public static void openFile(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(Paths.get(Properties.VIEW_PATH+fxmlFile).toUri().toURL());
      Parent root = loader.load();
      ((Controller) loader.getController()).apply(root);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
