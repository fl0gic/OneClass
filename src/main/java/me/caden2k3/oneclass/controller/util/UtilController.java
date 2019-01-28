package me.caden2k3.oneclass.controller.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.model.Properties;
import me.caden2k3.oneclass.model.util.UtilLog;

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
    UtilLog.debug("Attempting to open '"+fxmlFile+"'.");

    try {
      FXMLLoader loader = new FXMLLoader(OneClass.class.getResource(Properties.VIEW_PATH+fxmlFile));
      Parent root = loader.load();
      UtilLog.debug("File loaded, attempting to apply controller.");
      if (loader.getController() != null)
        ((Controller) loader.getController()).apply(root);
      else {
        UtilLog.getLog().warn("Controller file for '" + fxmlFile + "' is null, attempting to display.");
        display(root);
      }
    } catch (Exception ex) {
      UtilLog.error(ex);
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
      UtilLog.debug("Loading file '"+fxmlFile+"'.");
      return new FXMLLoader(OneClass.class.getResource(Properties.VIEW_PATH+fxmlFile)).load();
    } catch (Exception ex) {
      UtilLog.error(ex);
    }

    return null;
  }

  private static void display(Parent root) {
    Stage stage = OneClass.getInstance().getPrimaryStage();
    Scene scene = new Scene(root);

    UtilLog.debug("Setting stage scene to root.");
    stage.setScene(scene);

    if (!stage.isShowing()) {
      UtilLog.debug("Stage not showing, showing it.");
      stage.show();
    }
  }
}
