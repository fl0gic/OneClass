package me.caden2k3.oneclass.data;

import com.google.gson.Gson;
import lombok.Getter;
import me.caden2k3.oneclass.data.user.User;

/**
 * @author Caden Kriese
 *
 * Created on 9/27/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
@Getter
public class DataManager {

  private DataManager() { }

  private static DataManager instance = new DataManager();
  private User currentUser;
  private AppData appData;

  private Gson gson = new Gson();

  public void init() {
    //TODO Write in copy defaults from src/main/resources with default app configs.
  }
}
