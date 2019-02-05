package me.caden2k3.oneclass.model.user;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.caden2k3.oneclass.model.util.UtilCrypt;

import java.util.Date;

/**
 * @author Caden Kriese
 *
 * The {@link User} class is used to store all data pertaining to users within the app. It is
 * designed in a way that it can be easily mapped to JSON through Gson, or mapped to other datatypes
 * in the future.
 *
 * Created on 9/27/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public @Data
class User {
    private String username;
    private String email;

    @SerializedName("hash")
    private String passHash;
    @SerializedName("last-login")
    private Date lastLogin;
    @SerializedName("google-token")
    private String googleToken;
    @SerializedName("preferences")
    private UserPreferences preferences;
    @SerializedName("campus")
    private UserInfiniteCampus infiniteCampus;

    private User() {
    }

    User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.passHash = UtilCrypt.hash(password);
    }

    User(String username, String email, String password, Date lastLogin) {
        this.username = username;
        this.email = email;
        this.passHash = UtilCrypt.hash(password);
        this.lastLogin = lastLogin;
    }
}
