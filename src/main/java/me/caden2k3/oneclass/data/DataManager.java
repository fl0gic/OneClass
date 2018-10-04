package me.caden2k3.oneclass.data;

import com.google.gson.Gson;
import java.io.File;
import lombok.Getter;
import lombok.Setter;
import me.caden2k3.oneclass.data.user.User;
import me.caden2k3.oneclass.data.util.UtilFile;

/**
 * @author Caden Kriese
 *
 * Created on 9/27/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class DataManager {

  private DataManager() {
  }

  private static @Getter DataManager instance = new DataManager();
  private @Getter @Setter User currentUser;
  private @Getter AppData appData;

  private final Gson gson = new Gson();
  private final String APP_DATA_PATH = Properties.DATA_FOLDER_PATH + "/app.json";

  public void init() {
    File file = new File(APP_DATA_PATH);
    if (file.exists()) {
      appData = gson.fromJson(UtilFile.read(file), AppData.class);
    } else {
      appData = null;
    }

    if (appData != null && appData.getLatestUsername() != null) {
      File userFile = new File(
          Properties.USERS_FOLDER_PATH + "/" + appData.getLatestUsername() + ".json");
      if (userFile.exists()) {
        currentUser = gson.fromJson(UtilFile.read(file), User.class);
      } else {
        appData.setLatestUsername(null);
      }
    }
  }
}
