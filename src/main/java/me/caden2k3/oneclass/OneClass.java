package me.caden2k3.oneclass;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.caden2k3.oneclass.data.DataManager;

/**
 * @author Caden Kriese
 *
 * Created on 10/3/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class OneClass extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    DataManager.getInstance().init();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
