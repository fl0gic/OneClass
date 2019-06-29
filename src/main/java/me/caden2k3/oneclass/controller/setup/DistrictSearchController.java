package me.caden2k3.oneclass.controller.setup;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import lombok.Getter;
import me.caden2k3.infinitecampusapi.InfiniteCampusAPI;
import me.caden2k3.infinitecampusapi.district.DistrictInfo;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.FXMLChild;
import me.caden2k3.oneclass.controller.util.ControllerUtil;
import me.caden2k3.oneclass.controller.util.DialogUtil;
import me.caden2k3.oneclass.controller.validator.CustomValidator;
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
    private static @Getter
    DistrictSearchController instance;
    
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

        state.getValidators().add(new CustomValidator(new CustomValidator.ValidatorRunnable() {
            @Override
            public boolean eval(CustomValidator validator) {
                if (state.getSelectionModel().getSelectedItem() == null) {
                    validator.setMessage("Please select a state.");
                    return true;
                }

                return false;
            }
        }));

        district.getValidators().add(new CustomValidator(new CustomValidator.ValidatorRunnable() {
            @Override
            public boolean eval(CustomValidator validator) {
                if (district.getText().length() < 3) {
                    validator.setMessage("District names must be at least 3 characters.");
                    return true;
                }

                return false;
            }
        }));

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

    @FXML
    public void search() {
        if (!state.validate()) {
            DialogUtil.dialog(root, state.getActiveValidator().getMessage());
            return;
        } else if (!district.validate()) {
            DialogUtil.dialog(root, district.getActiveValidator().getMessage());
            return;
        }

        spinner("Searching for district.");

        ExecutorService fixedThreadPool = OneClass.getInstance().getFixedThreadPool();

        fixedThreadPool.submit(() -> {
            String stateCode = UtilStates.getStateCode(state.getConverter().toString(state.getValue()));

            try {
                List<DistrictInfo> queryResult = InfiniteCampusAPI.searchDistricts(district.getText(), stateCode);
                Platform.runLater(() -> {
                    removeSpinner();

                    if (queryResult.size() == 0) {
                        DialogUtil.dialog(root, "No results found.");
                        return;
                    } else if (queryResult.size() == 1) {
                        DistrictInfo info = queryResult.get(0);

                        DialogUtil.confirmDialog(root, "Is '" + info.getDistrictName() + "' your district?", () -> {
                            //On confirmation.
                            fixedThreadPool.submit(() -> OneClass.getInstance()
                                    .setInfiniteCampusCore(new InfiniteCampusAPI(info.getDistrictCode())));
                            ControllerUtil.transitionToNewStage(
                                    ControllerUtil.StageTransitionType.SWIPE_NODES,
                                    ICLoginController.class,
                                    1.5);
                        });
                        return;
                    }

                    JFXListView<Label> listView = new JFXListView<>();
                    listView.setOnMouseClicked(event -> {
                        if (listView.getSelectionModel().getSelectedItem() != null) {
                            Label label = listView.getSelectionModel().getSelectedItem();
                            DistrictInfo info = queryResult.get(listView.getItems().indexOf(label));

                            DialogUtil.confirmDialog(root, "Is '" + info.getDistrictName() + "' your district?", () -> {
                                //On confirmation.
                                fixedThreadPool.submit(() -> OneClass.getInstance()
                                        .setInfiniteCampusCore(new InfiniteCampusAPI(info.getDistrictCode())));
                                ControllerUtil.transitionToNewStage(
                                        ControllerUtil.StageTransitionType.SWIPE_NODES,
                                        ICLoginController.class,
                                        1.5);
                            });
                        }
                    });

                    for (DistrictInfo info : queryResult) {
                        Label infoLabel = new Label(info.getDistrictName());

                        infoLabel.setTooltip(new Tooltip("Name: " + info.getDistrictName() +
                                "\nState: " + UtilStates.getStateName(info.getStateCode()) +
                                "\nCode: " + info.getDistrictCode() +
                                "\nID: " + info.getId()));

                        listView.getItems().add(infoLabel);
                    }

                    JFXDialogLayout layout = new JFXDialogLayout();
                    VBox vBox = new VBox(new Label("Select your district."), listView);
                    vBox.setSpacing(20);
                    layout.setBody(vBox);

                    if (root.getChildrenUnmodifiable().stream().noneMatch(node -> node instanceof JFXDialog && ((JFXDialog) node).getContent() != layout)) {
                        JFXDialog dialog = new JFXDialog();
                        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
                        dialog.setDialogContainer((StackPane) root);
                        dialog.setContent(layout);
                        dialog.show();
                    }
                });

            } catch (IOException ex) {
                Platform.runLater(() -> {
                    removeSpinner();
                    DialogUtil.dialog(root, "An error occurred while searching.\n \nPlease try again.");
                });

                UtilLog.error(ex);
            }
        });
    }
}
