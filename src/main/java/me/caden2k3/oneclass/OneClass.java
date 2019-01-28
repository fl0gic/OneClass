package me.caden2k3.oneclass;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import me.caden2k3.oneclass.controller.util.UtilController;
import me.caden2k3.oneclass.model.AppData;
import me.caden2k3.oneclass.model.DataManager;
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
    UtilLog.init();
    UtilLog.setDebug(false);
    UtilLog.debug("Initializing OneClass, running #start()");

    instance = this;
    this.primaryStage = primaryStage;

    //UtilLog.debug("Setting project icon.");
    //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(Properties.IMAGE_PATH+"icon.png")));

    //Initialize classes
    UtilLog.debug("Initializing DataManager.");
    DataManager.getInstance().init();

    UtilController.openFile("setup/splash.fxml");

//    if (DataManager.getInstance().getUserList().size() == 0) {
//      UtilController.openFile("splash.fxml");
//    } else {
//      //TODO init app based on last login of other account.
//    }
  }

  @Override public void stop() {
    UtilLog.debug("OneClass shutting down, running #stop().");
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
