package me.caden2k3.oneclass.model;

import com.google.gson.Gson;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import me.caden2k3.oneclass.model.user.User;
import me.caden2k3.oneclass.model.user.UserInfiniteCampus;
import me.caden2k3.oneclass.model.util.UtilCrypt;
import me.caden2k3.oneclass.model.util.UtilFile;

/**
 * @author Caden Kriese
 *
 * The {@link DataManager} class is used for almost all interactions for retrieving data container
 * classes from the system or database.
 *
 * Created on 9/27/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class DataManager {
  private DataManager() {}
  private static @Getter DataManager instance = new DataManager();

  private @Getter List<User> userList = new ArrayList<>();
  private @Getter @Setter User currentUser;
  private @Getter AppData appData;

  private final Gson gson = new Gson();
  private final String APP_DATA_PATH = Properties.DATA_FOLDER_PATH + "/app.json";

  /**
   * The #load() method is used to read data from the system and put it into the app.
   */
  public void load() {
    //Create file structure.
    File userDirs = new File(Properties.USERS_FOLDER_PATH);
    if (!userDirs.exists()) {
      if (userDirs.mkdirs()) {
        System.out.println("Successfully created user dirs.");
      } else {
        System.out.println("User directory creation failed.");
        return;
      }
    }

    File appDataFile = new File(APP_DATA_PATH);
    if (appDataFile.exists()) {
      appData = gson.fromJson(UtilFile.read(appDataFile), AppData.class);
    } else {
      appData = null;
      return;
    }

    //TODO implement user data encryption on serialize, & decryption on deserialize
    Arrays.stream(Objects.requireNonNull(userDirs.listFiles()))
        .forEach(
            file -> userList.add(
                gson.fromJson(UtilFile.read(file), User.class)
            ));

    /*currentUser = userList.stream()
        .filter(user -> user.getUsername().equals(appData.getLatestUsername())).findFirst()
        .orElse(null);*/

    System.out.println();
  }

//  public void save() {
//    try {
//      for (User user : userList) {
//        File userFile = new File(Properties.USERS_FOLDER_PATH+"/"+user.getUsername()+".json");
//        if (userFile.exists() || userFile.createNewFile()) {
//
//        }
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

  /**
   * Decrypts an users infinite campus credentials using their password.
   *
   * @param user The infinite campus credentials to be decrypted.
   * @return The decrypted IC credentials.
   */
  public UserInfiniteCampus decryptICCreds(UserInfiniteCampus user, String password) {
    user = new UserInfiniteCampus();
    user.setUsername(UtilCrypt.decrypt(user.getUsername(), password));
    user.setPassword(UtilCrypt.decrypt(user.getPassword(), password));
    user.setDistrict(UtilCrypt.decrypt(user.getDistrict(), password));

    return user;
  }

  /**
   * Encrypts an users infinite campus credentials using their password.
   *
   * @param user The infinite campus credentials to be encrypted.
   * @return The encrypted IC credentials.
   */
  public UserInfiniteCampus encryptICCreds(UserInfiniteCampus user, String password) {
    user = new UserInfiniteCampus();
    user.setUsername(UtilCrypt.encrypt(user.getUsername(), password));
    user.setPassword(UtilCrypt.encrypt(user.getPassword(), password));
    user.setDistrict(UtilCrypt.encrypt(user.getDistrict(), password));

    return user;
  }

  public void addUserToList(User user) {
    userList.add(user);
  }
}
