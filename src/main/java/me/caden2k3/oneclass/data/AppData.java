package me.caden2k3.oneclass.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author Caden Kriese
 *
 * Created on 10/1/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class AppData {
  @SerializedName("latest-user")
  private String latestUsername;
  @SerializedName("latest-hash")
  private String latestUserHash;

  public String getLatestUserHash() {
    return latestUserHash;
  }

  public String getLatestUsername() {
    return latestUsername;
  }

  public void setLatestUserHash(String latestUserHash) {
    this.latestUserHash = latestUserHash;
  }

  public void setLatestUsername(String latestUsername) {
    this.latestUsername = latestUsername;
  }
}
