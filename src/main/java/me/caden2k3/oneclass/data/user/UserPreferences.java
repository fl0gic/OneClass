package me.caden2k3.oneclass.data.user;

import com.google.gson.annotations.SerializedName;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Caden Kriese
 *
 * Created on 9/27/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
@Getter
@Setter
public class UserPreferences {
  @SerializedName("background")
  private Color backgroundColor;
  @SerializedName("primary-text")
  private Color primaryTextColor;
  @SerializedName("secondary-text")
  private Color secondaryTextColor;
}
