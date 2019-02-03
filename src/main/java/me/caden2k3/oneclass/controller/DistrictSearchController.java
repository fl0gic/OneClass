package me.caden2k3.oneclass.controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.model.util.UtilInfiniteCampus;

/**
 * @author Caden Kriese
 *
 * Created on 2019-02-01.
 *
 * This code is copyright © Caden Kriese 2019
 */
public class DistrictSearchController extends Controller {
  private static @Getter DistrictSearchController instance;

  @FXML private JFXTextField districtName;
  @FXML private JFXTextField stateField;

  @Override public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);

    instance = this;


  }

  @FXML public void search() {


    OneClass.getInstance().getFixedThreadPool().submit(() -> {
       String id = UtilInfiniteCampus.searchDistrict(districtName.getText(), stateField.getText());
       Platform.runLater(() -> dialog(id));
    });
  }

  private void dialog(String errorMessage) {
    //Avoid duplicate alerts.
    if (((Pane) root).getChildren().stream().noneMatch(node -> node instanceof JFXDialog)) {
      JFXDialogLayout layout = new JFXDialogLayout();
      layout.setBody(new Label(errorMessage));
      JFXDialog dialog = new JFXDialog();
      dialog.setTransitionType(DialogTransition.CENTER);
      dialog.setDialogContainer((StackPane) root);
      dialog.setContent(layout);
      dialog.show();
    }
  }
}
