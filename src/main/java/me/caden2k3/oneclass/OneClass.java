package me.caden2k3.oneclass;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import me.caden2k3.oneclass.controller.util.UtilController;
import me.caden2k3.oneclass.model.AppData;
import me.caden2k3.oneclass.model.DataManager;
import me.caden2k3.oneclass.model.Properties;
import me.caden2k3.oneclass.model.util.UtilLog;

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

    //Initialize classes
    DataManager.getInstance().init();
    UtilLog.init();

    UtilController.openFile("account-creation.fxml");


//    DataManager.getInstance().init();
//    if (DataManager.getInstance().getAppData() == null || DataManager.getInstance().getUserList().size() == 0) {
//      UtilController.openFile("splash.fxml");
//    } else {
//      //TODO init app based on last login of other account.
//    }
  }

  @Override public void stop() {
    AppData appData = DataManager.getInstance().getAppData();

    appData.setLastHeight(primaryStage.getHeight());
    appData.setLastWidth(primaryStage.getWidth());
    if (DataManager.getInstance().getCurrentUser() != null)
      appData.setLatestUsername(DataManager.getInstance().getCurrentUser().getUsername());

    DataManager.getInstance().save();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
