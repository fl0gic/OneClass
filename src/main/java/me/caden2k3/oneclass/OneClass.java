package me.caden2k3.oneclass;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import me.caden2k3.oneclass.view.setup.SplashView;

/**
 * @author Caden Kriese
 *
 * Created on 10/3/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class OneClass extends Application {
  private static @Getter OneClass instance;

  @Override
  public void start(Stage primaryStage) {
    instance = this;

    System.out.println("strt");

    SplashView.getInstance().apply(primaryStage);

//    DataManager.getInstance().load();
//    if (DataManager.getInstance().getAppData() == null || DataManager.getInstance().getUserList().size() == 0) {
//      //TODO Splash screen & create acc.
//    } else {
//      //TODO load app based on last login of other account.
//    }
  }

  public static void main(String[] args) {
    System.out.println("main");
    launch(args);
  }
}
