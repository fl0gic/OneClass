package me.caden2k3.oneclass;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import me.caden2k3.infinitecampusapi.InfiniteCampusAPI;
import me.caden2k3.oneclass.controller.setup.DistrictSearchController;
import me.caden2k3.oneclass.controller.setup.SplashController;
import me.caden2k3.oneclass.controller.util.ControllerUtil;
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

    @Getter @Setter private InfiniteCampusAPI infiniteCampusCore;

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

        ControllerUtil.openController(SplashController.class);

        //TODO Use this to initialize in the future.
//        if (DataManager.getInstance().getUserList().size() == 0) {
//            ControllerUtil.openController(SplashController.class);
//        } else {
//            User lastUser = DataManager.getInstance().getUserList().stream().min(Comparator.comparingLong(user -> user.getLastLogin().getTime())).orElse(null);
//            ZonedDateTime thirtyDaysAgo = ZonedDateTime.now().plusDays(-30);
//            //if they logged in < 30 days ago
//            if (lastUser != null && !lastUser.getLastLogin().toInstant().isBefore(thirtyDaysAgo.toInstant())) {
//                DataManager.getInstance().setCurrentUser(lastUser);
//                if (lastUser.getInfiniteCampus() != null) {
//                    ControllerUtil.openController(ClassViewController.class);
//                } else
//                    ControllerUtil.openController(DistrictSearchController.class);
//            }
//        }
    }

    @Override
    public void stop() {
        UtilLog.debug("OneClass shutting down, running #stop().");
        fixedThreadPool.shutdown();

        AppData appData = DataManager.getInstance().getAppData();

        if (ControllerUtil.getCurrentController() != null && ControllerUtil.getCurrentController().isUsePreviousSizes()) {
            appData.setLastHeight(primaryStage.getHeight());
            appData.setLastWidth(primaryStage.getWidth());
        }

        if (DataManager.getInstance().getCurrentUser() != null)
            appData.setLatestUsername(DataManager.getInstance().getCurrentUser().getUsername());

        DataManager.getInstance().save();
    }
}
