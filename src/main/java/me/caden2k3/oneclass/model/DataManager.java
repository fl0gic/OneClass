package me.caden2k3.oneclass.model;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import me.caden2k3.oneclass.model.user.User;
import me.caden2k3.oneclass.model.user.UserInfiniteCampus;
import me.caden2k3.oneclass.model.util.UtilCrypt;
import me.caden2k3.oneclass.model.util.UtilFile;
import me.caden2k3.oneclass.model.util.UtilLog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    private static @Getter
    DataManager instance = new DataManager();
    private final Gson gson = new Gson();
    private final String APP_DATA_PATH = Properties.DATA_FOLDER_PATH + "/app.json";
    private @Getter
    List<User> userList = new ArrayList<>();
    private @Getter
    @Setter
    User currentUser;
    private @Getter
    AppData appData;
    private DataManager() {
    }

    /**
     * Used to read data from the system and put it into the app.
     */
    public void init() {
        //Create file structure.
        File userDir = new File(Properties.USERS_FOLDER_PATH);
        if (!userDir.exists()) {
            if (userDir.mkdirs()) {
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
            try {
                appDataFile.createNewFile();
            } catch (IOException ex) {
                UtilLog.error(ex);
            }

            appData = new AppData();
            return;
        }

        //TODO implement user data encryption on serialize, & decryption on deserialize
        Arrays.stream(Objects.requireNonNull(userDir.listFiles()))
                .forEach(file -> userList.add(gson.fromJson(UtilFile.read(file), User.class)));

        currentUser = userList.stream()
                .filter(user -> user.getUsername().equals(appData.getLatestUsername())).findFirst()
                .orElse(null);
    }

    /**
     * Writes all currently stored data to files.
     */
    public void save() {
        userList.forEach(user -> UtilFile.writeAndCreate(user,
                new File(Properties.USERS_FOLDER_PATH + "/" + user.getUsername() + ".json")));
        UtilFile.writeAndCreate(appData, new File(APP_DATA_PATH));
    }

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
}
