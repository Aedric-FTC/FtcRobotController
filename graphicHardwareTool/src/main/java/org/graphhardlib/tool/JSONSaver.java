package org.graphhardlib.tool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
    private static void writeToDisk()
    {
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(FILE_PATH))
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
    static JsonElement jsonElement;
    public static JsonElement load(String hub, int profile, String name)
    {
        gson = new Gson();
        try (FileReader reader = new FileReader(FILE_PATH + profile + "\\" +  hub + ".json")) {
            SaveData = gson.fromJson(reader, JsonObject.class);
            if (SaveData != null && SaveData.has(name)) {
                jsonElement = SaveData.get(name).getAsJsonPrimitive();
            }
            else if (SaveData != null){
                SaveData.addProperty(name, "Device");
                //writeToDisk();
                System.out.println(profile + name);
                jsonElement = SaveData.get(profile + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonElement;
    }
    public static void wipe()
    {
        gson = new Gson();
        //FileWriter(gson.toJson(new HashMap<>()));
    }
}
