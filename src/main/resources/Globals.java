package main.resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Globals {

    private static final Properties PROPERTIES = new Properties();
    private static final String CONFIG_PROPERTIES_PATH = "src/main/resources/config.properties";

    static {
        try {
            PROPERTIES.load(new FileInputStream(CONFIG_PROPERTIES_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static void setProperty(String key, String value) {
        PROPERTIES.setProperty(key, value);

        try {
            FileOutputStream output = new FileOutputStream(CONFIG_PROPERTIES_PATH);
            PROPERTIES.store(output, null);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String PLAYER_OPACITY = "player.opacity";
    public static final String IP_ADDRESS = "ip.address";
    public static final String PORT = "port";

}
