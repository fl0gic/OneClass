package me.caden2k3.oneclass.data;

import lombok.Getter;

/**
 * Created by Caden Kriese on 9/27/18.
 *
 * License is specified by the distributor which this file was written for. Otherwise it can be
 * found in the LICENSE file. If there is no license file the code is then completely copyrighted
 * and you must contact me before using it IN ANY WAY.
 */
public class DataManager {
  private DataManager() {}

  @Getter
  private static DataManager instance = new DataManager();

  @Getter
  User currentUser;

  public void init() {

  }
}
