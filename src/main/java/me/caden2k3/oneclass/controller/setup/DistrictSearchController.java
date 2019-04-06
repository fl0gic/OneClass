package me.caden2k3.oneclass.controller.setup;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import lombok.Getter;
import me.caden2k3.infinitecampusapi.InfiniteCampus;
import me.caden2k3.infinitecampusapi.district.DistrictInfo;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.FXMLChild;
import me.caden2k3.oneclass.controller.util.UtilController;
import me.caden2k3.oneclass.model.util.UtilLog;
import me.caden2k3.oneclass.model.util.UtilStates;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

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

        ExecutorService fixedThreadPool = OneClass.getInstance().getFixedThreadPool();

        fixedThreadPool.submit(() -> {
            String stateCode = UtilStates.getStateCode(state.getConverter().toString(state.getValue()));

            //TODO add support for multiple districts from a query.
            try {
                List<DistrictInfo> queryResult = InfiniteCampus.searchDistricts(district.getText(), stateCode);
                Platform.runLater(() -> {
                    removeSpinner();

                    JFXListView<Label> listView = new JFXListView<>();

                    listView.setOnMouseClicked(event -> {
                        if (listView.getSelectionModel().getSelectedItem() != null) {
                            Label label = listView.getSelectionModel().getSelectedItem();
                            DistrictInfo info = queryResult.get(listView.getItems().indexOf(label));

                            JFXDialogLayout layout = new JFXDialogLayout();
                            JFXDialog dialog = new JFXDialog();
                            VBox vBox = new VBox();
                            HBox bottomBox = new HBox();

                            vBox.setSpacing(40);
                            vBox.getChildren().add(new Label("Is '"+info.getDistrictName()+"' your district?"));

                            bottomBox.setSpacing(200);
                            JFXButton yesButton = new JFXButton("Yes");
                            JFXButton noButton = new JFXButton("No");
                            yesButton.setOnAction(event1 -> {
                                fixedThreadPool.submit(() ->
                                        OneClass.getInstance().setInfiniteCampusCore(new InfiniteCampus(info.getDistrictCode())));
                                UtilController.transitionToNewStage(UtilController.StageTransitionType.SWIPE_NODES, ICLoginController.class, 1.5);
                            });
                            noButton.setOnAction(event1 -> dialog.close());
                            bottomBox.getChildren().add(yesButton);
                            bottomBox.getChildren().add(noButton);
                            vBox.getChildren().add(bottomBox);

                            layout.setBody(vBox);
                            dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
                            dialog.setDialogContainer((StackPane) root);
                            dialog.setContent(layout);
                            dialog.show();
                        }
                    });

                    for (DistrictInfo info : queryResult) {
                        Label infoLabel = new Label(info.getDistrictName() + " - "+info.getStateCode());
                        listView.getItems().add(infoLabel);
                    }

                    if (root.getChildrenUnmodifiable().stream().noneMatch(node -> node instanceof JFXDialog)) {
                        JFXDialogLayout layout = new JFXDialogLayout();
                        VBox vBox = new VBox(new Label("Select your district."), listView);
                        vBox.setSpacing(20);
                        layout.setBody(vBox);
                        JFXDialog dialog = new JFXDialog();
                        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
                        dialog.setDialogContainer((StackPane) root);
                        dialog.setContent(layout);
                        dialog.show();
                    }
                });

            } catch (IOException ex) {
                UtilLog.error(ex);
            }
        });
    }
}
