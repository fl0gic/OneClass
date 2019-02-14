package me.caden2k3.oneclass.controller.setup;

import javafx.animation.*;
import javafx.application.Platform;
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

        new ParallelTransition(textTrans, logoTrans).play();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                    int scaleSize = 6;

                    ScaleTransition textScale = new ScaleTransition(duration, welcomeText);
                    textScale.setToX(scaleSize);
                    textScale.setToY(scaleSize);
                    FadeTransition textFade = new FadeTransition(duration, welcomeText);
                    textFade.setToValue(0);
                    ScaleTransition logoScale = new ScaleTransition(duration, logo);
                    logoScale.setToX(scaleSize);
                    logoScale.setToY(scaleSize);
                    FadeTransition logoFade = new FadeTransition(duration, logo);
                    logoFade.setToValue(0);

                    List<Transition> transitions = new ArrayList<>(Arrays.asList(textFade, textScale, logoFade, logoScale));

                    ((Pane) OneClass.getInstance().getPrimaryStage().getScene().getRoot()).getChildren().add(node);

                    node.setLayoutX(0);
                    node.setLayoutY(0);

                    ScaleTransition scaleTransition = new ScaleTransition(duration, node);
                    scaleTransition.setFromX(0);
                    scaleTransition.setFromY(0);
                    scaleTransition.setToX(1);
                    scaleTransition.setToY(1);
                    transitions.add(scaleTransition);

                    ParallelTransition sceneTrans = new ParallelTransition(transitions.toArray(new Transition[]{}));
                    sceneTrans.setOnFinished(event -> UtilController.openFile("setup/account-creation.fxml"));
                    sceneTrans.play();
                });
            }
        }, 1500);
    }
}
