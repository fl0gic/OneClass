package me.caden2k3.oneclass.controller;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.caden2k3.oneclass.OneClass;

/**
 * @author Caden Kriese
 *
 * Created on 2018-12-10.
 *
 * This code is copyright © Caden Kriese 2018
 */
public abstract class Controller implements Initializable {

  @Override public void initialize(URL location, ResourceBundle resources) {
    //Ensure that all FXML fields are not null.
    for (Field field : getClass().getFields()) {
      if (field.getAnnotation(FXML.class) != null) {
        try {
          assert field.get(this) != null :
              "Field '" + field.getName() + "' was null, check " + location.toExternalForm() + ".";
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }

  public void apply(Parent root) {
    Stage stage = OneClass.getInstance().getPrimaryStage();
    Scene scene = new Scene(root);

    stage.setScene(scene);

    if (!stage.isShowing()) {
      stage.initStyle(StageStyle.UNIFIED);
      stage.show();
    }
  }
}
