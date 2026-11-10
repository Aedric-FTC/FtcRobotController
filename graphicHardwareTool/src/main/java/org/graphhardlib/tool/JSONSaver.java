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

public class JSONSaver {
    public JSONSaver() {
        SaveData = new JsonObject();
    }
    public static JsonObject SaveData = new JsonObject();
    static Gson gson;
    private static void writeToDisk(String name, Object value)
    {
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("graphicHardwareTool/src/main/java/org/graphhardlib/tool/Profiles.json"))
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
        writeToDisk(name, value);
    }
    public static void save(String name, double value)
    {
        SaveData.addProperty(name, value);
        writeToDisk(name, value);
    }
    public static void save(String name, float value)
    {
        SaveData.addProperty(name, value);
        writeToDisk(name, value);
    }
    public static void save(String name, Number value)
    {
        SaveData.addProperty(name, value);
        writeToDisk(name, value);
    }
    public static void save(String name, boolean value)
    {
        SaveData.addProperty(name, value);
        writeToDisk(name, value);
    }
    public static void save(String name, String value)
    {
        SaveData.addProperty(name, value);
        writeToDisk(name, value);
    }
    static JsonElement jsonElement;
    public static JsonElement load(String name)
    {
        SaveData = new JsonObject();
        gson = new Gson();
        try (FileReader reader = new FileReader("graphicHardwareTool/src/main/java/org/graphhardlib/tool/Profiles.json")){
            SaveData = gson.fromJson(reader, JsonObject.class);
            if (SaveData != null && SaveData.has(name)) {
                jsonElement = SaveData.get(name).getAsJsonPrimitive();
            }
            else if (SaveData != null){
                SaveData.addProperty(name, "Devicen't Name");
                jsonElement = SaveData.get(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonElement;
    }
    /*public static void wipe()
    {
        gson = new Gson();
        ReadWriteFile.writeFile(FILE, gson.toJson(new HashMap<>()));
        RobotLog.ii("SaveData", "Data Wiped");
    }*/
}
