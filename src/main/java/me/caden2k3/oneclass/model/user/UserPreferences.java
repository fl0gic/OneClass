package me.caden2k3.oneclass.model.user;

import com.google.gson.annotations.SerializedName;
import javafx.scene.paint.Color;
import lombok.Data;

/**
 * @author Caden Kriese
 *
 * Created on 9/27/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public @Data class UserPreferences {
    @SerializedName("background")
    private Color backgroundColor;
    @SerializedName("primary-text")
    private Color primaryTextColor;
    @SerializedName("secondary-text")
    private Color secondaryTextColor;
    @SerializedName("debug-mode")
    private boolean debug = true;
}
