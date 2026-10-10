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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataSaver {
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
    public static void saveThis(String name, int value)
    {
        SaveData.addProperty(name, value);
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("SaveData.json"))
        {
            gson.toJson(SaveData, writer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void saveThis(String name, double value)
    {
        SaveData.addProperty(name, value);
        writeToDisk();
    }
    public static void saveThis(String name, float value)
    {
        SaveData.addProperty(name, value);
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("SaveData.json"))
        {
            gson.toJson(SaveData, writer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void saveThis(String name, boolean value)
    {
        SaveData.addProperty(name, value);
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("SaveData.json"))
        {
            gson.toJson(SaveData, writer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void saveThis(String name, String value)
    {
        SaveData.addProperty(name, value);
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("SaveData.json"))
        {
            gson.toJson(SaveData, writer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    static JsonElement jsonElement;
    public static JsonElement loadThis(String name)
    {
        gson = new Gson();
        if (FILE.exists()) {
            try {
                SaveData = new JsonParser().parse(ReadWriteFile.readFile(FILE)).getAsJsonObject();
                if (SaveData != null && SaveData.has(name)) {
                    jsonElement = SaveData.get(name);
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
}
