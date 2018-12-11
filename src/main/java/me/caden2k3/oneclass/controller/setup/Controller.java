package me.caden2k3.oneclass.controller.setup;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

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

  public abstract void apply(Parent root);
}
