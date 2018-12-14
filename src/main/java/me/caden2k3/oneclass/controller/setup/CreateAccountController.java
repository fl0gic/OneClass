package me.caden2k3.oneclass.controller.setup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.util.PasswordValidator;

/**
 * @author Caden Kriese
 *
 * Created on 2018-12-11.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class CreateAccountController extends Controller {
  private static @Getter CreateAccountController instance;
  private @Getter Parent root;

  @FXML private JFXButton createAccountButton;
  @FXML private JFXTextField usernameField;
  @FXML private JFXPasswordField passwordField;

  @Override public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    instance = this;
  }

  @Override public void apply(Parent root) {
    this.root = root;

    Stage stage = OneClass.getInstance().getPrimaryStage();
    Scene scene = new Scene(root);

    PasswordValidator validator = new PasswordValidator();
//    validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
//        .glyph(FontAwesomeIcon.WARNING)
//        .styleClass("error")
//        .build());

    passwordField.getValidators().add(validator);
    passwordField.focusedProperty().addListener((o, oldVal, newVal) -> {
      //Clicking out of the field.
      if (!newVal) {
        passwordField.validate();
      }
    });

    stage.setResizable(true);
    stage.setScene(scene);

    if (!stage.isShowing()) {
      stage.initStyle(StageStyle.UNIFIED);
      stage.show();
    }
  }

  @FXML
  public void handleKeyPressed(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
      createAccountButton.fire();
    }
  }

  @FXML
  public void handleClick(ActionEvent event) {
    if (usernameField.getText().length() > 0 && passwordField.getText().length() > 0)
      createAccount();
  }

  private void createAccount() {
    String password = passwordField.getText();
    if (passwordField.validate()) {

    }
  }

  private void error(String errorMessage) {
    JFXDialogLayout layout = new JFXDialogLayout();
    layout.setBody(new Label(errorMessage));
    JFXDialog dialog = new JFXDialog();
    dialog.setTransitionType(DialogTransition.CENTER);
    dialog.setDialogContainer((StackPane) root);
    dialog.setContent(layout);
    dialog.show();
  }
}
