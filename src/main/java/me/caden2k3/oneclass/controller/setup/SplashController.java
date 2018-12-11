package me.caden2k3.oneclass.controller.setup;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import me.caden2k3.oneclass.OneClass;

/**
 * @author Caden Kriese
 *
 * Created on 9/27/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class SplashController extends Controller {
  @FXML private Text welcomeText;

  public void apply(Parent root) {
    try {
      //TODO ensure the transition to login screen is quick.
      Stage stage = OneClass.getInstance().getPrimaryStage();
      Scene scene = new Scene(root);
      //scene.getStylesheets().add(getClass().getResource("SplashScreen.css").toExternalForm());

      stage.setResizable(true);
      stage.initStyle(StageStyle.UNIFIED);
      stage.setTitle("");
      stage.setScene(scene);
      stage.show();

      runAnimation();
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void runAnimation() {
    Stage primaryStage = OneClass.getInstance().getPrimaryStage();
    Scene scene = primaryStage.getScene();

    System.out.println(welcomeText.getText());
    TranslateTransition tt = new TranslateTransition(Duration.seconds(2), welcomeText);

    tt.setFromX(scene.getWidth()/2);
    tt.setToX(0);
    tt.setCycleCount(1);
    tt.play();
  }
}
