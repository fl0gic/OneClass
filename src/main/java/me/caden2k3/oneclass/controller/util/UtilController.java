package me.caden2k3.oneclass.controller.util;

import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Runs animations to transition to a new stage.
     *
     * @param transitionType The type of transition to perform.
     * @param pathToNextStage The path to the next FXML file that should be transitioned to.
     * @param durationSeconds The duration of the transition in seconds.
     *
     * @apiNote When using the SWIPE transition the overall duration will last slightly longer than the duration specified duration.
     */
    @SuppressWarnings("Duplicates")
    public static void transitionToNewStage(StageTransitionType transitionType, String pathToNextStage, double durationSeconds) {
        Stage primaryStage = OneClass.getInstance().getPrimaryStage();
        Scene scene = primaryStage.getScene();

        Duration duration = Duration.seconds(durationSeconds/2d);

        //Size to scale objects to for scale transitions.
        int scaleSize = 6;

        if (transitionType == StageTransitionType.SCALE_INDIVIDUAL) {
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

            ParallelTransition transitionOut = new ParallelTransition(ArrayUtils.addAll(scaleOutAnimations, fadeOutAnimations));

            transitionOut.setOnFinished(event -> {
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

                ParallelTransition transitionIn = new ParallelTransition(ArrayUtils.addAll(scaleInAnimations, fadeInAnimations));
                transitionIn.play();
            });

            transitionOut.play();
        } else if (transitionType == StageTransitionType.SCALE_STAGE) {
            ScaleTransition scaleOutAnimation = new ScaleTransition(duration, scene.getRoot());
            scaleOutAnimation.setToX(scaleSize);
            scaleOutAnimation.setToY(scaleSize);

            FadeTransition fadeOutAnimation = new FadeTransition(duration, scene.getRoot());
            fadeOutAnimation.setToValue(0);

            ParallelTransition transitionOut = new ParallelTransition(scaleOutAnimation, fadeOutAnimation);

            transitionOut.setOnFinished(event -> {
                //Switch to next scene
                openFile(pathToNextStage);
                Scene currentScene = OneClass.getInstance().getPrimaryStage().getScene();

                //Scale and fade in new nodes.
                ScaleTransition scaleInAnimation = new ScaleTransition(duration, currentScene.getRoot());
                scaleInAnimation.setFromY(0);
                scaleInAnimation.setFromX(0);
                scaleInAnimation.setToX(1);
                scaleInAnimation.setToY(1);

                FadeTransition fadeInAnimation = new FadeTransition(duration, currentScene.getRoot());
                fadeInAnimation.setFromValue(0);
                fadeInAnimation.setToValue(1);

                ParallelTransition transitionIn = new ParallelTransition(scaleInAnimation, fadeInAnimation);
                transitionIn.play();
            });

            transitionOut.play();
        } else if (transitionType == StageTransitionType.SWIPE) {
            //NOTE: Because of the stagger this transition will take longer than the duration specified (node count * stagger amount longer).
            int staggerAmount = 100;

            List<TranslateTransition> translateOutTransitions = scene.getRoot().getChildrenUnmodifiable()
                    .stream()
                    .sorted(Comparator.comparing(Node::getLayoutY))
                    .map(node -> new TranslateTransition(duration, node))
                    .peek(translateTransition -> translateTransition.setToX((scene.getWidth()*-1)-(translateTransition.getNode().getLayoutBounds().getWidth()+10)))
                    .collect(Collectors.toList());

            //Stagger each animation.
            translateOutTransitions.forEach(translateTransition -> translateTransition
                    .setDelay(new Duration(translateOutTransitions.indexOf(translateTransition)*staggerAmount)));

            ParallelTransition transitionOut = new ParallelTransition(translateOutTransitions.toArray(Animation[]::new));

            transitionOut.setOnFinished(event -> {
                //Switch to next scene
                openFile(pathToNextStage);
                Scene currentScene = OneClass.getInstance().getPrimaryStage().getScene();

                List<TranslateTransition> translateInTransitions = currentScene.getRoot().getChildrenUnmodifiable()
                        .stream()
                        .sorted(Comparator.comparing(Node::getLayoutY))
                        .map(node -> new TranslateTransition(duration, node))
                        .peek(translateTransition -> translateTransition.setToX(translateTransition.getNode().getTranslateX()))
                        .peek(translateTransition -> translateTransition.setFromX(currentScene.getWidth()+translateTransition.getNode().getLayoutBounds().getWidth()+10))
                        .collect(Collectors.toList());

                translateInTransitions.forEach(translateTransition -> translateTransition.setDelay(
                        new Duration(translateInTransitions.indexOf(translateTransition)*staggerAmount)));

                new ParallelTransition(translateInTransitions.toArray(Animation[]::new)).play();
            });

            transitionOut.play();
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

    public enum StageTransitionType {
        SCALE_INDIVIDUAL,
        SCALE_STAGE,
        SWIPE
    }
}
