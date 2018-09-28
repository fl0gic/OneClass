package me.caden2k3.oneclass;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Caden Kriese on 9/27/18.
 *
 * TODO elaborate on this for main class
 * License is specified by the distributor which this file was written for. Otherwise it can be
 * found in the LICENSE file. If there is no license file the code is then completely copyrighted
 * and you must contact me before using it IN ANY WAY.
 */

public class OneClass extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Hello World");
    primaryStage.setScene(new Scene(root, 300, 275));
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
