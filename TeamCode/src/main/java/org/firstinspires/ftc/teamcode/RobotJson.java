package org.firstinspires.ftc.teamcode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qualcomm.robotcore.util.ReadWriteFile;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.util.HashMap;

public class RobotJson {
    public static JsonObject SaveData = new JsonObject();
    static Gson gson;
    public static final File FILE = AppUtil.getInstance().getSettingsFile("SaveData.json");
    private static void writeToDisk()
    {
        gson = new GsonBuilder().setPrettyPrinting().create();
        try
        {
            ReadWriteFile.writeFile(FILE, gson.toJson(SaveData));
            RobotLog.ii("SaveData", "Saved to " + FILE.getAbsolutePath());
        }
        catch (Exception e)
        {
            RobotLog.ee("SaveData", e, "Save Failed");
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
    static JsonElement jsonElement;
    public static JsonElement load(String name)
    {
        gson = new Gson();
        if (FILE.exists()) {
            try {
                SaveData = new JsonParser().parse(ReadWriteFile.readFile(FILE)).getAsJsonObject();
                if (SaveData != null && SaveData.has(name)) {
                    jsonElement = SaveData.get(name);
                }
                else if (SaveData != null){
                    SaveData.addProperty(name, 0);
                }
            } catch (Exception e) {
                RobotLog.ee("SaveData", e, "Load Failed");
            }
        }
        else {
            RobotLog.ee("SaveData", "Load Failed");
        }
        return jsonElement;
    }
    public static void wipe()
    {
        gson = new Gson();
        ReadWriteFile.writeFile(FILE, gson.toJson(new HashMap<>()));
    }
}
