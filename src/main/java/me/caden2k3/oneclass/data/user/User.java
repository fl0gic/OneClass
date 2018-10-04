package me.caden2k3.oneclass.data.user;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Caden Kriese
 *
 * The {@link User} class is used to store all data pertaining to users within the app.
 * It is designed in a way that it can be easily mapped to JSON through Gson, or mapped to other datatypes in the future.
 *
 * Created on 9/27/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
@Getter
@Setter
public class User {
  private String username;
  private String email;
  @SerializedName("hash")
  private String passHash;
  @SerializedName("google-token")
  private String googleToken;
  @SerializedName("preferences")
  private UserPreferences preferences;
  @SerializedName("campus")
  private UserInfiniteCampus infiniteCampus;
}
