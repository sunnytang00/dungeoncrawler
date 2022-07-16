package dungeonmania.util.json;

import java.io.InputStream;
import org.json.JSONObject;
import org.json.JSONTokener;

import dungeonmania.util.FileLoader;

public class JSONConfig {

    private static String fileName; 

    public static void setConfig(String fileName) {
        JSONConfig.fileName = fileName;
    }

    public static int getConfig(String field) throws IllegalArgumentException {
        InputStream is = FileLoader.class.getResourceAsStream("/configs/" + fileName + ".json");
        if (is == null) { throw new IllegalArgumentException(); }
        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        System.out.println("Config:" +  object.getInt(field));
        return object.getInt(field);
    }
}
