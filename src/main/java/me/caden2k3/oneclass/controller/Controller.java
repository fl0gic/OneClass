package me.caden2k3.oneclass.controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSpinner;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
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

    @Getter protected Parent root;
    @Getter protected boolean usePreviousSizes = true;
    protected Image windowIcon;
    protected int minHeight = 200;
    protected int minWidth = 300;
    protected boolean resizable = true;
    protected StageStyle style = StageStyle.UNIFIED;
    protected String title = "";

    private Label currentSpinnerLabel;

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

        UtilLog.debug("Setting resizable to '"+resizable+"'.");
        stage.setResizable(resizable);

        UtilLog.debug("Setting minimum width to '" + minWidth + "' and minimum height to '" + minHeight + "'.");
        stage.setMinHeight(minHeight);
        stage.setMinWidth(minWidth);

        UtilLog.debug("Setting title to '" + title + "'.");
        stage.setTitle(title);

        if (windowIcon != null) {
            UtilLog.debug("Setting window icon.");
            stage.getIcons().add(windowIcon);
        }

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
        if (root.getChildrenUnmodifiable().stream().noneMatch(node -> node instanceof JFXDialog)) {
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
        if (root.getChildrenUnmodifiable().stream().noneMatch(node -> node instanceof JFXDialog)) {
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setBody(nodes);
            JFXDialog dialog = new JFXDialog();
            dialog.setTransitionType(transition);
            dialog.setDialogContainer((StackPane) root);
            dialog.setContent(layout);
            root.addEventHandler(KeyEvent.KEY_PRESSED, event -> dialog.close());
            dialog.show();
        }
    }

    /**
     * Displays a blank spinner over the current page.
     */
    public void spinner() {
        spinner("", 0.2);
    }

    /**
     * Displays a spinner over the current window.
     *
     * @param message The message to be displayed next to the spinner.
     */
    public void spinner(String message) {
        spinner(message, 0.2);
    }

    /**
     * Displays a spinner over the current window.
     *
     * @param message The message to be displayed.
     * @param duration The duration of the zoom animation for the spinner in seconds.
     */
    public void spinner(String message, double duration) {
        if (currentSpinnerLabel != null)
            removeSpinner();

        JFXSpinner spinner = new JFXSpinner();
        spinner.setRadius(10);

        Label label = new Label();
        label.setGraphic(spinner);
        label.setText(message);
        label.getStylesheets().addAll(root.getStylesheets());
        label.getStyleClass().add("field-style");
        label.applyCss();

        ((Pane) root).getChildren().add(label);

        currentSpinnerLabel = label;

        if (duration > 0) {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(duration), label);
            scaleTransition.setFromX(0);
            scaleTransition.setFromY(0);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);

            scaleTransition.play();
        }
    }

    /**
     * Removes a spinner that is currently displayed.
     */
    public void removeSpinner() {
        removeSpinner(0.2);
    }

    /**
     * Removes a spinner that is currently displayed.
     *
     * @param duration The duration for the scale transition in seconds.
     */
    public void removeSpinner(double duration) {
        if (duration > 0) {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(duration), currentSpinnerLabel);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(0);
            scaleTransition.setToY(0);

            scaleTransition.play();
        }

        ((Pane) root).getChildren().remove(currentSpinnerLabel);
        currentSpinnerLabel = null;
    }
}
