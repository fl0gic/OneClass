package me.caden2k3.oneclass.controller.setup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import lombok.Getter;
import me.caden2k3.infinitecampusapi.InfiniteCampus;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.FXMLChild;
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
@FXMLChild(path = "setup/district-search.fxml")
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
        spinner("Searching for district.");

        OneClass.getInstance().getFixedThreadPool().submit(() -> {
            String stateCode = UtilStates.getStateCode(state.getConverter().toString(state.getValue()));

            //TODO add support for multiple districts from a query.
            String id = UtilInfiniteCampus.searchDistrict(district.getText(), stateCode);

            if (id != null) {
                Platform.runLater(() -> {
                    UtilController.transitionToNewStage(UtilController.StageTransitionType.SWIPE_NODES, ICLoginController.class, 2);
                    ICLoginController.getInstance().setInfiniteCampus(new InfiniteCampus(UtilInfiniteCampus.searchDistrict(district.getText(), stateCode)));
                });
            } else {
                dialog(JFXDialog.DialogTransition.CENTER, "No districts found under that request!");
            }
        });
    }
}
