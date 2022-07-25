package dungeonmania.util;

import java.io.InputStream;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONConfig {

    private static String fileName; 

    public static void setConfig(String fileName) {
        JSONConfig.fileName = fileName;
    }

    public static double getConfig(String field) throws IllegalArgumentException {
        InputStream is = FileLoader.class.getResourceAsStream("/configs/" + fileName + ".json");
        if (is == null) { throw new IllegalArgumentException(); }
        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        return object.getDouble(field);
    }
}
