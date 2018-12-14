package me.caden2k3.oneclass;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import me.caden2k3.oneclass.controller.util.UtilController;
import me.caden2k3.oneclass.model.Properties;

/**
 * ONECLASS
 *
 * This code is copyright © Caden Kriese 2018
 * Created on 10/3/18.
 */
public class OneClass extends Application {
  private static @Getter OneClass instance;
  private @Getter Stage primaryStage;

  @Override
  public void start(Stage primaryStage) {
    instance = this;
    this.primaryStage = primaryStage;

    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(Properties.IMAGE_PATH+"icon.png")));

    UtilController.openFile("account-creation.fxml");

//    DataManager.getInstance().load();
//    if (DataManager.getInstance().getAppData() == null || DataManager.getInstance().getUserList().size() == 0) {
//      UtilController.openFile("splash.fxml");
//    } else {
//      //TODO load app based on last login of other account.
//    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
