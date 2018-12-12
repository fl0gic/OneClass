package me.caden2k3.oneclass.controller.setup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;

/**
 * @author Caden Kriese
 *
 * Created on 2018-12-11.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class LoginController extends Controller {
  private static @Getter LoginController instance;
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

    stage.setResizable(true);
    stage.setTitle("Create Account");
    stage.setScene(scene);

    if (!stage.isShowing()) {
      stage.initStyle(StageStyle.UNIFIED);
      stage.show();
    }
  }

  @FXML
  public void handleKeyPressed(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER)
      createAccount();
  }

  @FXML
  public void handleClick(ActionEvent event) {
    createAccount();
  }

  private void createAccount() {
    String password = passwordField.getText();
    if (password.length() > 8) {

    } else {
    }
  }

  private void error(String errorMessage) {

  }
}
