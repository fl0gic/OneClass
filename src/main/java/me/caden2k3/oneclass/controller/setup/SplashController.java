package me.caden2k3.oneclass.controller.setup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.util.UtilController;

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

  public void apply(Parent root) {
    Stage stage = OneClass.getInstance().getPrimaryStage();
    Scene scene = new Scene(root);

    stage.initStyle(StageStyle.UNIFIED);

    stage.setResizable(true);
    stage.setTitle("");
    stage.setScene(scene);

    if (!stage.isShowing())
      stage.show();

    runAnimation();
  }

  public void runAnimation() {
    Stage primaryStage = OneClass.getInstance().getPrimaryStage();
    Scene scene = primaryStage.getScene();

    Duration duration = Duration.seconds(1);

    TranslateTransition textTrans = new TranslateTransition(duration, welcomeText);
    textTrans.setFromX(scene.getWidth()/2);
    textTrans.setToX(0);
    TranslateTransition logoTrans = new TranslateTransition(duration, logo);
    logoTrans.setFromX(scene.getWidth()/2);
    logoTrans.setToX(0);

    new ParallelTransition(textTrans, logoTrans).play();

    new Timer().schedule(new TimerTask() {
      @Override public void run() {
        Platform.runLater(() -> {

          int scaleSize = 8;

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

          Pane node = (Pane) UtilController.loadFile("login.fxml");

          assert node != null : "Root was null, ya done goofed.";

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
          sceneTrans.setOnFinished(event -> UtilController.openFile("login.fxml"));
          sceneTrans.play();
        });
      }
    }, 1500);
  }
}
