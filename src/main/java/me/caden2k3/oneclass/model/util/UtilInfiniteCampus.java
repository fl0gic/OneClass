package me.caden2k3.oneclass.model.util;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Caden Kriese
 *
 * Created on 2019-02-01.
 *
 * This code is copyright © Caden Kriese 2019
 */
public class UtilInfiniteCampus {
    private static Gson gson = new Gson();

    public static String searchDistrict(String districtName, String state) {
        districtName = districtName.replace(" ", "%20");
        try {
            String jsonReturn = UtilReader.readFrom("https://mobile.infinitecampus.com/mobile/searchDistrict?query=" + districtName + "&state=" + state);
            Map dataMap = (Map) ((List) new Gson().fromJson(jsonReturn, Map.class).get("data")).get(0);

            Double id = (Double) dataMap.get("id");
            String code = (String) dataMap.get("district_code");
            String name = (String) dataMap.get("district_name");
            String appName = (String) dataMap.get("district_app_name");

            return "ID: " + id + "\n" +
                    "Code: " + code + "\n" +
                    "Name: " + name + "\n" +
                    "App Name: " + appName;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
