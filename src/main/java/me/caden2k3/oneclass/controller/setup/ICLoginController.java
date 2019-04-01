package me.caden2k3.oneclass.controller.setup;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import me.caden2k3.infinitecampusapi.InfiniteCampus;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.FXMLChild;
import me.caden2k3.oneclass.model.Properties;

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

    @Override public void apply(Parent root) {
        super.apply(root);
    }

    public void handleClick(ActionEvent event) {

        if (infiniteCampus.attemptLogin(usernameField.getText(), passwordField.getText(), infiniteCampus.getDistrictInfo())) {

        }
    }

    public void handleKeyPressed(KeyEvent keyEvent) {
    }
}
