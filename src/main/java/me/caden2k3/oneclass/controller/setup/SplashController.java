package me.caden2k3.oneclass.controller.setup;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.FXMLChild;
import me.caden2k3.oneclass.controller.util.UIRunnable;
import me.caden2k3.oneclass.controller.util.UtilController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Caden Kriese
 *
 * Created on 9/27/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
@FXMLChild(path = "setup/splash.fxml")
public class SplashController extends Controller {
    @FXML private Text welcomeText;
    @FXML private ImageView logo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        usePreviousSizes = false;
        resizable = false;
        title = "";
    }

    @Override
    public void apply(Parent root) {
        super.apply(root);
        runAnimation();
    }

    //Runs the welcome animation.
    private void runAnimation() {
        Stage primaryStage = OneClass.getInstance().getPrimaryStage();
        Scene scene = primaryStage.getScene();

        Duration duration = new Duration(750);

        TranslateTransition logoTrans = new TranslateTransition(duration, logo);
        logoTrans.setFromX(scene.getWidth() / 2);
        logoTrans.setToX(0);
        TranslateTransition textTrans = new TranslateTransition(duration, welcomeText);
        textTrans.setFromX(scene.getWidth() / 2);
        textTrans.setToX(0);
        textTrans.setDelay(new Duration(100));

        ParallelTransition transition = new ParallelTransition(textTrans, logoTrans);
        transition.setOnFinished(event ->
                new UIRunnable() {
                    @Override
                    public void run() {
                        UtilController.transitionToNewStage(
                                UtilController.StageTransitionType.SWIPE_NODES, "setup/account-creation.fxml", 1.5);
                    }
                }.runTaskLater(500));

        transition.play();
    }
}
