package me.caden2k3.oneclass.controller.setup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import lombok.Setter;
import me.caden2k3.infinitecampusapi.InfiniteCampus;
import me.caden2k3.infinitecampusapi.Student;
import me.caden2k3.infinitecampusapi.exception.InvalidCredentialsException;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.FXMLChild;
import me.caden2k3.oneclass.model.DataManager;
import me.caden2k3.oneclass.model.Properties;
import me.caden2k3.oneclass.model.user.User;
import me.caden2k3.oneclass.model.user.UserInfiniteCampus;
import me.caden2k3.oneclass.model.util.UtilLog;
import nu.xom.ParsingException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Caden Kriese
 *
 * Created on 2019-02-12.
 */
@FXMLChild(path = "setup/ic-login.fxml")
public class ICLoginController extends Controller {
    @Getter private static ICLoginController instance;
    @Getter @Setter private InfiniteCampus infiniteCampus;

    @FXML private JFXTextField usernameField;
    @FXML private JFXPasswordField passwordField;
    @FXML private JFXButton loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        instance = this;

        //Configure window/stage settings.
        minHeight = 375;
        minWidth = 300;
        usePreviousSizes = false;
        title = "Login to Infinite Campus";
        windowIcon = new Image(OneClass.class.getResourceAsStream(Properties.IMAGE_PATH+"infinite-campus.png"));
    }

    public void handleClick(ActionEvent event) {
        spinner("Attempting login.");

        InfiniteCampus campusCore = OneClass.getInstance().getInfiniteCampusCore();

        UserInfiniteCampus userIC = new UserInfiniteCampus(
                usernameField.getText(),
                passwordField.getText(),
                campusCore.getDistrictInfo().getDistrictCode());

        User user = DataManager.getInstance().getCurrentUser();
        user.setInfiniteCampus(userIC);

        usernameField.setDisable(true);
        passwordField.setDisable(true);
        loginButton.setDisable(true);

        OneClass.getInstance().getFixedThreadPool().submit(() -> {
            try {
                Student student = userIC.toStudent();
                DataManager.getInstance().setCurrentStudent(student);

                Platform.runLater(this::removeSpinner);

                usernameField.setDisable(false);
                passwordField.setDisable(false);
                loginButton.setDisable(false);

                UtilLog.debug(student.getInfoString());
            } catch (ParsingException | IOException e) {
                UtilLog.error(e);
            } catch (InvalidCredentialsException e) {
                Platform.runLater(() -> {
                    removeSpinner();

                    usernameField.setDisable(false);
                    passwordField.setDisable(false);
                    loginButton.setDisable(false);

                    dialog(JFXDialog.DialogTransition.CENTER, "Invalid credentials!");
                });
            }
        });
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            //Ripple animation.
            loginButton.arm();
            loginButton.disarm();
            //Run logic
            loginButton.fire();
        }
    }
}
