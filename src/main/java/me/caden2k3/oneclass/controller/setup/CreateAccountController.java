package me.caden2k3.oneclass.controller.setup;

import static java.util.stream.Collectors.toList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.util.CustomValidator;
import me.caden2k3.oneclass.controller.util.CustomValidator.ValidatorRunnable;
import me.caden2k3.oneclass.model.DataManager;
import me.caden2k3.oneclass.model.user.User;

/**
 * @author Caden Kriese
 *
 * Created on 2018-12-11.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class CreateAccountController extends Controller {
  private static @Getter CreateAccountController instance;

  @FXML private JFXButton createAccountButton;
  @FXML private JFXTextField usernameField;
  @FXML private JFXPasswordField passwordField;

  @Override public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    instance = this;

    //Configure window/stage settings.
    minHeight = 325;
    minWidth = 300;
    usePreviousSizes = false;
    title = "Create an Account";

    //VALIDATORS
    CustomValidator passwordValidator = new CustomValidator(new ValidatorRunnable() {
      @Override public boolean eval(CustomValidator validator) {
        if (validator.getSrcControl() == passwordField) {
          String password = passwordField.getText();
          if (password.length() > 0) {
            if (password.length() >= 8) {
              if (!password.equals(password.toLowerCase())) {
                if (!password.matches("[A-Za-z]*")) {
                  return false;
                } else
                  validator.setMessage("Password must contain a number or symbol.");
              } else
                validator.setMessage("Password must contain an uppercase character.");
            } else
              validator.setMessage("Password must be at least 8 characters.");
          } else
            validator.setMessage("Please enter a password.");
        }

        return true;
      }
    });

    //TODO uncomment when the fontawesomefx PR is merged.
//    validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
//        .glyph(FontAwesomeIcon.WARNING)
//        .styleClass("error")
//        .build());

    passwordField.getValidators().add(passwordValidator);
    passwordField.focusedProperty().addListener((o, oldVal, newVal) -> {
      //Clicking out of the field.
      if (!newVal)
        passwordField.validate();
    });

    CustomValidator usernameValidator = new CustomValidator(new ValidatorRunnable() {
      @Override public boolean eval(CustomValidator validator) {
        if (validator.getSrcControl() == usernameField) {
          String username = usernameField.getText();
          if (username.length() > 0) {
            if (!DataManager.getInstance().getUserList().stream()
                .map(User::getUsername).collect(toList()).contains(username)) {
              //TODO verify in DB that username is not taken.
              return false;
            } else
              validator.setMessage("Username already taken!");
          } else
            validator.setMessage("Please enter an username.");
        }

        return true;
      }
    });

    usernameField.getValidators().add(usernameValidator);
    usernameField.focusedProperty().addListener((o, oldVal, newVal) -> {
      if (!newVal)
        usernameField.validate();
    });
  }

  @FXML
  public void handleKeyPressed(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
      createAccountButton.fire();
    }
  }

  @FXML
  public void handleClick() {
    createAccount();
  }

  private void createAccount() {
    if (usernameField.validate()) {
      if (passwordField.validate()) {
        
      } else
        error(passwordField.getActiveValidator().getMessage());
    } else
      error(usernameField.getActiveValidator().getMessage());
  }

  private void error(String errorMessage) {
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
