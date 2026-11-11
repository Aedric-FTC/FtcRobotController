package org.graphhardlib.tool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;

public class JSONSaver {
    public static JsonObject SaveData = new JsonObject();
    static Gson gson;
    public static final String FILE_PATH = "D:\\Aedric\\Documents\\GitHub\\FtcRobotController\\graphicHardwareTool\\src\\main\\java\\org\\graphhardlib\\tool\\saved\\profile";
    public static final String SETTINGS_PATH = "D:\\Aedric\\Documents\\GitHub\\FtcRobotController\\graphicHardwareTool\\src\\main\\java\\org\\graphhardlib\\tool\\saved\\GlobalSettings.json";
    static
    {
        File file = new File(SETTINGS_PATH);
        if (file.exists())
        {
            try (FileReader reader = new FileReader(file))
            {
                JsonObject existing = new Gson().fromJson(reader, JsonObject.class);
                if (existing != null)
                {
                    SaveData = existing;
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private static void writeToDisk()
    {
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(SETTINGS_PATH))
        {
            gson.toJson(SaveData, writer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void save(String name, int value)
    {
        SaveData.addProperty(name, value);
        writeToDisk();
    }
    public static void save(String name, double value)
    {
        SaveData.addProperty(name, value);
        writeToDisk();
    }
    public static void save(String name, float value)
    {
        SaveData.addProperty(name, value);
        writeToDisk();
    }
    public static void save(String name, Number value)
    {
        SaveData.addProperty(name, value);
        writeToDisk();
    }
    public static void save(String name, boolean value)
    {
        SaveData.addProperty(name, value);
        writeToDisk();
    }
    public static void save(String name, String value)
    {
        SaveData.addProperty(name, value);
        writeToDisk();
    }
    public static void save(String hub, int profile, Map<String, JTextField> inputData)
    {
        HashMap<String, String> data = new HashMap<>();
        for (Map.Entry<String, JTextField> entry : inputData.entrySet()) {
            data.put(entry.getKey(), entry.getValue().getText());
        }
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(FILE_PATH + profile + "\\" +  hub + ".json"))
        {
            gson.toJson(data, writer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    static JsonElement loadGlobal(String name)
    {
        Gson reader = new Gson();
        try (FileReader fileReader = new FileReader(SETTINGS_PATH)) {
            JsonObject profileData = reader.fromJson(fileReader, JsonObject.class);
            if (profileData != null && profileData.has(name)) {
                return profileData.get(name);
            }
            return null; // your old default, without touching SaveData
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static JsonElement load(String hub, int profile, String name)
    {
        Gson reader = new Gson();
        try (FileReader fileReader = new FileReader(FILE_PATH + profile + "\\" + hub + ".json")) {
            JsonObject profileData = reader.fromJson(fileReader, JsonObject.class);
            if (profileData != null && profileData.has(name)) {
                return profileData.get(name);
            }
            return new JsonPrimitive("Device"); // your old default, without touching SaveData
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void wipe()
    {
        gson = new Gson();
        //FileWriter(gson.toJson(new HashMap<>()));
    }
}
