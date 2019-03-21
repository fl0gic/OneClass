package me.caden2k3.oneclass.controller.util;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.model.Properties;
import me.caden2k3.oneclass.model.util.UtilLog;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Caden Kriese
 *
 * Created on 2018-12-10.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class UtilController {
    private static @Getter Controller currentController = null;

    /**
     * Fully initializes & displays a FXML file.
     *
     * @param fxmlFile The name of the file to be loaded. (Excluding path, including .fxml)
     * @see Controller
     */
    public static void openFile(String fxmlFile) {
        UtilLog.debug("Attempting to open '" + fxmlFile + "'.");

        try {
            FXMLLoader loader = new FXMLLoader(OneClass.class.getResource(Properties.VIEW_PATH + fxmlFile));
            Parent root = loader.load();
            UtilLog.debug("File loaded, attempting to apply controller.");

            if (loader.getController() instanceof Controller) {
                currentController = loader.getController();
                currentController.apply(root);
            } else {
                UtilLog.getLog().warn("Controller file for '" + fxmlFile + "' is null, attempting to display.");
                display(root);
            }
        } catch (Exception ex) {
            UtilLog.error(ex);
        }
    }

    /**
     * Loads the controller of a FXML file.
     *
     * @param fxmlFile The file to be loaded, located in resources/view/
     * @return The loaded FXML file in the form of a Parent.
     */
    public static Parent loadFile(String fxmlFile) {
        try {
            UtilLog.debug("Loading file '" + fxmlFile + "'.");
            return new FXMLLoader(OneClass.class.getResource(Properties.VIEW_PATH + fxmlFile)).load();
        } catch (Exception ex) {
            UtilLog.error(ex);
        }

        return null;
    }

    @SuppressWarnings("Duplicates")
    public static void transitionToNewStage(StageTransition transitionType, String pathToNextStage, int durationSeconds) {
        Stage primaryStage = OneClass.getInstance().getPrimaryStage();
        Scene scene = primaryStage.getScene();

        Duration duration = Duration.seconds(durationSeconds/2d);

        if (transitionType == StageTransition.SCALE) {
            int scaleSize = 6;

            //TODO investigate this vs scaling/fading the scene as a whole.

            //Scale and fade out objects.
            Animation[] scaleOutAnimations = scene.getRoot().getChildrenUnmodifiable()
                    .stream()
                    .map(node -> new ScaleTransition(duration, node))
                    .peek(scaleTransition -> scaleTransition.setToX(scaleSize))
                    .peek(scaleTransition -> scaleTransition.setToY(scaleSize))
                    .toArray(Animation[]::new);
            Animation[] fadeOutAnimations = scene.getRoot().getChildrenUnmodifiable()
                    .stream()
                    .map(node -> new FadeTransition(duration, node))
                    .peek(scaleTransition -> scaleTransition.setToValue(0))
                    .toArray(Animation[]::new);
            ParallelTransition transition1 = new ParallelTransition(ArrayUtils.addAll(scaleOutAnimations, fadeOutAnimations));

            transition1.setOnFinished(event -> {
                //Switch to next scene
                openFile(pathToNextStage);
                Scene currentScene = OneClass.getInstance().getPrimaryStage().getScene();

                //Scale and fade in new nodes.
                Animation[] scaleInAnimations = currentScene.getRoot().getChildrenUnmodifiable()
                        .stream()
                        .map(node -> new ScaleTransition(duration, node))
                        .peek(scaleTransition -> scaleTransition.setFromX(0))
                        .peek(scaleTransition -> scaleTransition.setFromY(0))
                        .peek(scaleTransition -> scaleTransition.setToX(1))
                        .peek(scaleTransition -> scaleTransition.setToY(1))
                        .toArray(Animation[]::new);

                Animation[] fadeInAnimations = currentScene.getRoot().getChildrenUnmodifiable()
                        .stream()
                        .map(node -> new FadeTransition(duration, node))
                        .peek(scaleTransition -> scaleTransition.setFromValue(0))
                        .peek(scaleTransition -> scaleTransition.setToValue(1))
                        .toArray(Animation[]::new);

                ParallelTransition transition2 = new ParallelTransition(ArrayUtils.addAll(scaleInAnimations, fadeInAnimations));
                transition2.play();
            });

            transition1.play();
        }
    }

    private static void display(Parent root) {
        Stage stage = OneClass.getInstance().getPrimaryStage();
        Scene scene = new Scene(root);

        UtilLog.debug("Setting stage scene to root.");
        stage.setScene(scene);

        if (!stage.isShowing()) {
            UtilLog.debug("Stage not showing, showing it.");
            stage.show();
        }
    }

    public enum StageTransition {
        SCALE,
        SWIPE
    }
}
