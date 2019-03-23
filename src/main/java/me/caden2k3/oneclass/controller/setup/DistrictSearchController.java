package me.caden2k3.oneclass.controller.setup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import lombok.Getter;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.util.UtilController;
import me.caden2k3.oneclass.model.util.UtilInfiniteCampus;
import me.caden2k3.oneclass.model.util.UtilStates;

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
    @FXML private JFXTextField district;
    @FXML private JFXComboBox<Label> state;
    @FXML private JFXButton searchButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        instance = this;

        //Configure window/stage settings.
        minHeight = 300;
        minWidth = 300;
        usePreviousSizes = false;
        title = "District Search";

        state.setConverter(new StringConverter<>() {
            @Override
            public String toString(Label object) {
                return object == null ? "" : object.getText();
            }

            @Override
            public Label fromString(String string) {
                return string == null || string.isEmpty() ? null : new Label(string);
            }
        });
    }

    @FXML
    public void keyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            //Ripple animation.
            searchButton.arm();
            searchButton.disarm();

            search();
        }
    }

    @FXML public void search() {
        JFXSpinner spinner = new JFXSpinner();
        spinner.setRadius(10);
        ((Pane) root).getChildren().add(spinner);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), spinner);
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        scaleTransition.play();

        OneClass.getInstance().getFixedThreadPool().submit(() -> {
            String stateCode = UtilStates.getStateCode(state.getConverter().toString(state.getValue()));
            String id = UtilInfiniteCampus.searchDistrict(district.getText(), stateCode);

            Platform.runLater(() -> {
                ((Pane) root).getChildren().remove(spinner);

                UtilController.transitionToNewStage(UtilController.StageTransitionType.SWIPE_NODES, "setup/ic-login.fxml", 2);
//                dialog(DialogTransition.CENTER, id == null ? "Unable to find district!" : id);
            });
        });
    }
}
