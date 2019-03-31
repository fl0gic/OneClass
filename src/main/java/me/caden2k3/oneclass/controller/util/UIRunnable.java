package me.caden2k3.oneclass.controller.util;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 2019-03-22
 */
public abstract class UIRunnable implements Runnable {
    private Timer currentTimer;

    /**
     * Executes the runnable on the UI thread as soon as possible.
     */
    public void runTask() {
        Platform.runLater(this);
    }

    /**
     * Runs the task later on the UI thread.
     *
     * @param delay The delay in milliseconds before the task is run.
     */
    public void runTaskLater(long delay) {
        UIRunnable runnable = this;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(runnable);
            }
        }, delay);
    }

    /**
     * Cancels any pending {@link #runTaskLater(long)} calls.
     */
    public void cancel() {
        currentTimer.cancel();
    }
}
