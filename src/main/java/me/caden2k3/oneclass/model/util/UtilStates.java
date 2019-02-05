package me.caden2k3.oneclass.model.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Caden Kriese
 *
 * Utility for getting US state codes and state names.
 *
 * Created on 2019-02-03.
 */
public class UtilStates {
    private static HashMap<String, String> states = new HashMap<>() {{
        put("Alabama", "AL");
        put("Alaska", "AK");
        put("Arizona", "AZ");
        put("Arkansas", "AR");
        put("California", "CA");
        put("Colorado", "CO");
        put("Connecticut", "CT");
        put("Delaware", "DE");
        put("Florida", "FL");
        put("Georgia", "GA");
        put("Hawaii", "HI");
        put("Idaho", "ID");
        put("Illinois", "IL");
        put("Indiana", "IN");
        put("Iowa", "IA");
        put("Kansas", "KS");
        put("Kentucky", "KY");
        put("Louisiana", "LA");
        put("Maine", "ME");
        put("Maryland", "MD");
        put("Massachusetts", "MA");
        put("Michigan", "MI");
        put("Minnesota", "MN");
        put("Mississippi", "MS");
        put("Missouri", "MO");
        put("Montana", "MT");
        put("Nebraska", "NE");
        put("Nevada", "NV");
        put("New Hampshire", "NH");
        put("New Jersey", "NJ");
        put("New Mexico", "NM");
        put("New York", "NY");
        put("North Carolina", "NC");
        put("North Dakota", "ND");
        put("Ohio", "OH");
        put("Oklahoma", "OK");
        put("Oregon", "OR");
        put("Pennsylvania", "PA");
        put("Quebec", "QC");
        put("Rhode Island", "RI");
        put("South Carolina", "SC");
        put("South Dakota", "SD");
        put("Tennessee", "TN");
        put("Texas", "TX");
        put("Utah", "UT");
        put("Vermont", "VT");
        put("Virginia", "VA");
        put("Washington", "WA");
        put("West Virginia", "WV");
        put("Wisconsin", "WI");
        put("Wyoming", "WY");
    }};

    /**
     * Retrieves the state name from the code.
     *
     * @param code The state code.
     * @return The state name.
     * @apiNote Not the most efficient method.
     */
    public static String getStateName(String code) {
        Map.Entry<String, String> stateEntry = states.entrySet().stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase(code)).findFirst().orElse(null);
        return stateEntry == null ? null : stateEntry.getKey();
    }

    /**
     * Retrieves a state code given the name.
     *
     * @param name The name of the state.
     * @return The state code.
     */
    public static String getStateCode(String name) {
        return states.get(name);
    }
}
