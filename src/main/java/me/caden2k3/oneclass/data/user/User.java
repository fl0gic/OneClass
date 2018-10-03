package me.caden2k3.oneclass.data.user;

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
  private String passHash;
  private String googleToken;
  private UserInfiniteCampus infiniteCampus;
  private UserPreferences preferences;
}
