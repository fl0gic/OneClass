package me.caden2k3.oneclass.controller.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 2019-06-28
 */
public class DialogUtil {

    /**
     * Sends a dialog to the user with the specified message.
     *
     * @param root    The parent for the dialog to be applied to.
     * @param message The message to display in the dialog.
     * @see JFXDialog
     * @see JFXDialogLayout
     */
    public static void dialog(Parent root, String message) {
        dialog(root, JFXDialog.DialogTransition.CENTER, message);
    }

    /**
     * Sends a dialog to the user with the specified message.
     *
     * @param root       The parent for the dialog to be applied to.
     * @param transition The type of transition to perform.
     * @param message    The message to display in the dialog.
     * @see JFXDialog
     * @see JFXDialogLayout
     */
    public static void dialog(Parent root, JFXDialog.DialogTransition transition, String message) {
        dialog(root, transition, new Label(message));
    }

    /**
     * Sends a dialog to the user with the specific node(s).
     *
     * @param root       The parent for the dialog to be applied to.
     * @param transition The type of transition to perform.
     * @param nodes      The message to display in the dialog.
     * @see JFXDialog
     * @see JFXDialogLayout
     */
    public static void dialog(Parent root, JFXDialog.DialogTransition transition, Node... nodes) {
        //Avoid duplicate alerts.
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setBody(nodes);

        if (root.getChildrenUnmodifiable().stream().noneMatch(node -> node instanceof JFXDialogLayout
                                                                              && ((JFXDialogLayout) node).getBody() == layout.getBody())) {
            JFXDialog dialog = new JFXDialog();
            dialog.setTransitionType(transition);
            dialog.setDialogContainer((StackPane) root);
            dialog.setContent(layout);
            root.addEventHandler(KeyEvent.KEY_PRESSED, event -> dialog.close());
            dialog.show();
        }
    }


    /**
     * Creates a confirmation dialog for the user.
     *
     * @param root      The parent for the dialog to be applied to.
     * @param message   The message to be displayed to the user.
     * @param onConfirm The runnable to be executed if they hit yes.
     * @apiNote The dialog will close when they hit cancel.
     */
    public static void confirmDialog(Parent root, String message, Runnable onConfirm) {
        confirmDialog(root, message, JFXDialog.DialogTransition.CENTER, onConfirm, null);
    }

    /**
     * Creates a confirmation dialog for the user.
     *
     * @param root      The parent for the dialog to be applied to.
     * @param message   The message to be displayed to the user.
     * @param onConfirm The runnable to be executed if they hit yes.
     * @param onDeny    The runnable to be executed if they hit no.
     * @apiNote The dialog will close when they hit cancel.
     */
    public void confirmDialog(Parent root, String message, Runnable onConfirm, Runnable onDeny) {
        confirmDialog(root, message, JFXDialog.DialogTransition.CENTER, onConfirm, onDeny);
    }

    /**
     * Creates a confirmation dialog for the user.
     *
     * @param root       The parent for the dialog to be applied to.
     * @param message    The message to be displayed to the user.
     * @param transition The type of transition for the confirmation window to take.
     * @param onConfirm  The runnable to be executed if they hit yes.
     * @param onDeny     The runnable to be executed if they hit no.
     * @apiNote The dialog will close when they hit cancel.
     */
    public static void confirmDialog(Parent root, String message, JFXDialog.DialogTransition transition, Runnable onConfirm, Runnable onDeny) {
        JFXDialogLayout layout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog();
        VBox vBox = new VBox();
        HBox bottomBox = new HBox();

        vBox.setSpacing(40);
        vBox.getChildren().add(new Label(message));

        bottomBox.setSpacing(200);
        JFXButton confirmButton = new JFXButton("Confirm");
        JFXButton cancelButton = new JFXButton("Cancel");

        confirmButton.setOnAction(event -> onConfirm.run());

        //Make dialog close on cancel otherwise run the runnable.
        if (onDeny == null)
            cancelButton.setOnAction(event -> dialog.close());
        else
            cancelButton.setOnAction(event -> {
                dialog.close();
                onDeny.run();
            });

        bottomBox.getChildren().add(cancelButton);
        bottomBox.getChildren().add(confirmButton);
        vBox.getChildren().add(bottomBox);

        layout.setBody(vBox);
        dialog.addEventHandler(KeyEvent.KEY_PRESSED, event -> dialog.close());
        dialog.setTransitionType(transition);
        dialog.setDialogContainer((StackPane) root);
        dialog.setContent(layout);
        dialog.show();
    }
}
