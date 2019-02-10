package me.caden2k3.oneclass.controller.setup;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.validator.EmailValidator;
import me.caden2k3.oneclass.controller.validator.PasswordValidator;
import me.caden2k3.oneclass.controller.validator.UsernameValidator;
import me.caden2k3.oneclass.model.DataManager;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * @author Caden Kriese
 *
 * Created on 2018-12-11.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class CreateAccountController extends Controller {
    private static @Getter
    CreateAccountController instance;

    @FXML private JFXTextField usernameField;
    @FXML private JFXTextField emailField;
    @FXML private JFXPasswordField passwordField;
    @FXML private JFXCheckBox tosCheckBox;
    @FXML private JFXButton createAccountButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        instance = this;

        //Configure window/stage settings.
        minHeight = 375;
        minWidth = 300;
        usePreviousSizes = false;
        title = "Create an Account";

        //VALIDATORS
        EmailValidator validator = new EmailValidator();
        emailField.getValidators().add(validator);
        usernameField.getValidators().add(new UsernameValidator());
        passwordField.getValidators().add(new PasswordValidator());

        //Add listeners for all of them
        Stream.of(emailField, usernameField, passwordField)
                .forEach(field -> field.focusedProperty().addListener((o, oldVal, newVal) -> {
            //Clicking out of the field.
            if (!newVal)
                field.validate();
        }));
    }

    @FXML public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER
                && root.getChildrenUnmodifiable().stream().noneMatch(node -> node instanceof JFXDialog)) {
            createAccountButton.arm();
            createAccountButton.disarm();
            createAccount();
        }
    }
    @FXML public void handleClick() {
        createAccount();
    }
    @FXML public void showTos() {
        dialog(DialogTransition.CENTER, "I agree to not be a dick.");
    }

    private void createAccount() {
        if (usernameField.validate()) {
            if (passwordField.validate()) {
                if (emailField.validate()) {
                    if (tosCheckBox.isSelected()) {
                        DataManager.getInstance().setCurrentUser(DataManager.getInstance()
                                .createNewUser(usernameField.getText(), emailField.getText(), passwordField.getText()));
                        dialog(DialogTransition.CENTER, "Account created & saved!");
                    } else
                        error("You must agree to the terms of service!");
                } else
                    error(emailField.getActiveValidator().getMessage());
            } else
                error(passwordField.getActiveValidator().getMessage());
        } else
            error(usernameField.getActiveValidator().getMessage());
    }

    private void error(String errorMessage) {
        dialog(DialogTransition.CENTER, errorMessage);
    }
}
