package me.caden2k3.oneclass.controller;

import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import lombok.Getter;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.model.util.UtilInfiniteCampus;

import java.net.URL;
import java.util.ResourceBundle;

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
  @FXML private JFXSpinner spinner;

  @Override public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    instance = this;

    //Configure window/stage settings.
    minHeight = 300;
    minWidth = 300;
    usePreviousSizes = false;
    title = "District Search";

    //Hide
    spinner.setManaged(false);
  }

  @FXML public void keyPress(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER)
      search();
  }

  @FXML public void search() {
    //Show
    spinner.setManaged(true);

    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), spinner);
    scaleTransition.setToX(0.6);
    scaleTransition.setToY(0.6);
    scaleTransition.setFromX(0);
    scaleTransition.setFromY(0);

    scaleTransition.play();

    OneClass.getInstance().getFixedThreadPool().submit(() -> {
       String id = UtilInfiniteCampus.searchDistrict(districtName.getText(), stateField.getText());

       Platform.runLater(() -> {
         dialog(DialogTransition.CENTER, id == null ? "Unable to find district!" : id);

         ScaleTransition revertTransition = new ScaleTransition(Duration.seconds(0.5), spinner);
         revertTransition.setToX(0);
         revertTransition.setToY(0);
         revertTransition.setFromX(0.6);
         revertTransition.setFromY(0.6);

         revertTransition.play();
       });
    });
  }
}
