package me.caden2k3.oneclass.controller.setup;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.util.UtilController;

import java.net.URL;
import java.util.*;

/**
 * @author Caden Kriese
 *
 * Created on 9/27/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class SplashController extends Controller {
    @FXML private Text welcomeText;
    @FXML private ImageView logo;

    private Pane node;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        usePreviousSizes = false;
        resizable = false;
        title = "";
    }

    @Override
    public void apply(Parent root) {
        super.apply(root);
        runAnimation();

        //Initialize it here to keep stuff smooth when animation runs.
        OneClass.getInstance().getFixedThreadPool().submit(() -> {
            node = (Pane) UtilController.loadFile("setup/account-creation.fxml");
        });
    }

    //Runs the welcome animation.
    private void runAnimation() {
        Stage primaryStage = OneClass.getInstance().getPrimaryStage();
        Scene scene = primaryStage.getScene();

        Duration duration = Duration.seconds(1);

        TranslateTransition textTrans = new TranslateTransition(duration, welcomeText);
        textTrans.setFromX(scene.getWidth() / 2);
        textTrans.setToX(0);
        TranslateTransition logoTrans = new TranslateTransition(duration, logo);
        logoTrans.setFromX(scene.getWidth() / 2);
        logoTrans.setToX(0);

        ParallelTransition transition = new ParallelTransition(textTrans, logoTrans);
        transition.setOnFinished(event -> UtilController.transitionToNewStage(UtilController.StageTransition.SCALE, "setup/account-creation.fxml", 1));
        transition.play();
    }
}
