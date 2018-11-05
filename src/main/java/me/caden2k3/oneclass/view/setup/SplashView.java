package me.caden2k3.oneclass.view.setup;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;

/**
 * @author Caden Kriese
 *
 * Created on 9/27/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class SplashView {
  public SplashView() {}
  private static @Getter SplashView instance = new SplashView();

  private Scene scene;
  private Parent root;
  private Stage stage;

  public void apply(Stage primaryStage) {
    try {
      stage = primaryStage;
      root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
      scene = new Scene(root);

      primaryStage.setResizable(false);
      primaryStage.initStyle(StageStyle.UNIFIED);
      primaryStage.setTitle("Welcome!");
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }
}
