package me.caden2k3.oneclass.model.util;

import com.google.gson.Gson;
import lombok.NonNull;
import me.caden2k3.oneclass.model.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author Caden Kriese
 *
 * Created on 10/3/18.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class UtilFile {

    private static Gson gson = new Gson();

    /**
     * Reads text from a file.
     *
     * @param file The file to be read from.
     * @return A string with all the lines of the file.
     */
    @NonNull public static String read(File file) {
        try {
            return FileUtils.readFileToString(file, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            UtilLog.error(ex);
        }

        return null;
    }

    /**
     * Writes text to a file.
     *
     * @param string The string to be written to a file.
     * @param file   The file to be written to.
     */
    @NonNull public static void write(String string, File file) {
        try {
            FileUtils.writeStringToFile(file, string, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            UtilLog.error(ex);
        }
    }

    /**
     * Writes an object to a file, serializing it with {@link com.google.gson.Gson#toJson(Object)}.
     *
     * @param object The object to be written to a file
     * @param file   The file to be written to.
     */
    public @NonNull static void write(Object object, File file) {
        write(gson.toJson(object), file);
    }

    public @NonNull
    static void writeAndCreate(String string, File file) {
        try {
            if (!file.exists() && !file.createNewFile())
                throw new IOException("File creation failed.");

            write(string, file);
        } catch (Exception ex) {
            UtilLog.error(ex);
        }
    }

    @NonNull public static void writeAndCreate(Object object, File file) {
        writeAndCreate(gson.toJson(object), file);
    }

    private static String findKey() {
        File keyFile = new File(Properties.DATA_FOLDER_PATH+"/.cpt");
        String key = null;

        try {
            if (keyFile.exists())
                key = FileUtils.readFileToString(keyFile, "UTF-8");
            if (key == null) {
                key = RandomStringUtils.randomAscii(50);

                FileUtils.writeStringToFile(keyFile, key, "UTF-8");
            }
        } catch (IOException ex) {
            UtilLog.error(ex);
        }

        return key;
    }
}
