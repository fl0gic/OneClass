package me.caden2k3.oneclass.controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.model.AppData;
import me.caden2k3.oneclass.model.DataManager;
import me.caden2k3.oneclass.model.util.UtilLog;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Caden Kriese
 *
 * Created on 2018-12-10.
 *
 * This code is copyright © Caden Kriese 2018
 */
public abstract class Controller implements Initializable {

    @Getter
    protected Parent root;
    protected int minHeight = 200;
    protected int minWidth = 300;
    protected boolean usePreviousSizes = true;
    protected StageStyle style = StageStyle.UNIFIED;
    protected String title = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UtilLog.debug("Initializing '" + location.toExternalForm() + "'...");

        //Ensure that all FXML fields are not null.
        for (Field field : this.getClass().getFields()) {
            if (field.getAnnotation(FXML.class) != null) {
                try {
                    if (field.get(this.getClass()) == null) {
                        throw new NullPointerException(
                                "Field '" + field.getName() + "' was null, check " + location.toExternalForm() + ".");
                    } else
                        UtilLog.debug("Field '" + field.getName() + "' not null.");
                } catch (Exception ex) {
                    UtilLog.error(ex);
                }
            }

            UtilLog.debug("Initialized '" + location.toExternalForm() + "'.");
        }
    }


    public void apply(Parent root) {
        this.root = root;

        UtilLog.debug("Applying '" + this.getClass().getName() + "'...");
        Stage stage = OneClass.getInstance().getPrimaryStage();
        Scene scene = new Scene(root);

        UtilLog.debug("Setting minimum width to '" + minWidth + "' and minimum height to '" + minHeight + "'.");
        stage.setMinHeight(minHeight);
        stage.setMinWidth(minWidth);

        UtilLog.debug("Setting title to '" + title + "'.");
        stage.setTitle(title);

        if (usePreviousSizes) {
            AppData data = DataManager.getInstance().getAppData();
            UtilLog.debug("Setting width to '" + data.getLastWidth() + "' and height to '" + data.getLastHeight() + "'.");
            stage.setWidth(data.getLastWidth());
            stage.setHeight(data.getLastHeight());
        }

        UtilLog.debug("Setting stage scene to root.");
        stage.setScene(scene);

        if (!stage.isShowing()) {
            UtilLog.debug("Stage not showing, displaying it.");
            stage.initStyle(style);
            stage.show();
        }

        UtilLog.debug("Applied '" + this.getClass().getName() + "'.");
    }

    /**
     * Sends a dialog to the user with the specified message.
     *
     * @param transition The type of transition to perform.
     * @param message    The message to display in the dialog.
     * @see JFXDialog
     * @see JFXDialogLayout
     */
    public void dialog(JFXDialog.DialogTransition transition, String message) {
        //Avoid duplicate alerts.
        if (((Pane) root).getChildren().stream().noneMatch(node -> node instanceof JFXDialog)) {
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setBody(new Label(message));
            JFXDialog dialog = new JFXDialog();
            dialog.setTransitionType(transition);
            dialog.setDialogContainer((StackPane) root);
            dialog.setContent(layout);
            root.addEventHandler(KeyEvent.KEY_PRESSED, event -> dialog.close());
            dialog.show();
        }
    }

    /**
     * Sends a dialog to the user with the specific node(s).
     *
     * @param transition The type of transition to perform.
     * @param nodes      The message to display in the dialog.
     * @see JFXDialog
     * @see JFXDialogLayout
     */
    public void dialog(JFXDialog.DialogTransition transition, Node... nodes) {
        //Avoid duplicate alerts.
        if (((Pane) root).getChildren().stream().noneMatch(node -> node instanceof JFXDialog)) {
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setBody(nodes);
            JFXDialog dialog = new JFXDialog();
            dialog.setTransitionType(transition);
            dialog.setDialogContainer((StackPane) root);
            dialog.setContent(layout);
            dialog.show();
        }
    }
}
