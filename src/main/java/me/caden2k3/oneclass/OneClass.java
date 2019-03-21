package me.caden2k3.oneclass;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import me.caden2k3.oneclass.controller.util.UtilController;
import me.caden2k3.oneclass.model.AppData;
import me.caden2k3.oneclass.model.DataManager;
import me.caden2k3.oneclass.model.util.UtilLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ONECLASS
 *
 * This code is copyright © Caden Kriese 2018
 * Created on 10/3/18.
 */
public class OneClass extends Application {
    @Getter private static OneClass instance;
    @Getter private Stage primaryStage;
    @Getter private ExecutorService fixedThreadPool;

    @SuppressWarnings("FieldCanBeLocal")
    private static boolean DEBUG_MODE = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(Stage primaryStage) {
        //Logging
        UtilLog.init();
        UtilLog.setDebug(DEBUG_MODE);
        UtilLog.debug("Initializing OneClass, running #start()");

        //Initialize Sentry (Error tracking).
        //Sentry.init(Properties.SENTRY_DSN);

        //Class Setup
        instance = this;
        this.primaryStage = primaryStage;
        fixedThreadPool = Executors.newFixedThreadPool(5);

        //Initialize classes
        UtilLog.debug("Initializing DataManager.");
        DataManager.getInstance().init();

        UtilController.openFile("setup/splash.fxml");

//        if (DataManager.getInstance().getUserList().size() == 0) {
//            UtilController.openFile("splash.fxml");
//        } else {
//            //TODO init app based on last login of other account.
//        }
    }

    @Override
    public void stop() {
        UtilLog.debug("OneClass shutting down, running #stop().");
        AppData appData = DataManager.getInstance().getAppData();

        if (UtilController.getCurrentController() != null && UtilController.getCurrentController().isUsePreviousSizes()) {
            appData.setLastHeight(primaryStage.getHeight());
            appData.setLastWidth(primaryStage.getWidth());
        }

        if (DataManager.getInstance().getCurrentUser() != null)
            appData.setLatestUsername(DataManager.getInstance().getCurrentUser().getUsername());

        DataManager.getInstance().save();
    }
}
