package me.caden2k3.oneclass.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Caden Kriese
 *
 * Created on 10/1/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public @Data
class AppData {
    @SerializedName("latest-username")
    private String latestUsername;
    @SerializedName("last-width")
    private double lastWidth;
    @SerializedName("last-height")
    private double lastHeight;

    AppData() {
        lastHeight = 400d;
        lastWidth = 600d;
    }
}
