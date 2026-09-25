package org.firstinspires.ftc.teamcode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataSaver {
    public static JsonObject SaveData = new JsonObject();
    static Gson gson;
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
        try (FileReader reader = new FileReader("SaveData.json"))
        {
            SaveData = gson.fromJson(reader, JsonObject.class);
            if (SaveData != null && SaveData.has(name))
            {
                jsonElement = SaveData.get(name).getAsJsonObject();
            }
        }
        catch (IOException e)
        {
            SaveData.addProperty(name, 0);
            gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter("SaveData.json"))
            {
                gson.toJson(SaveData, writer);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            jsonElement = SaveData.get(name).getAsJsonObject();
        }
        return jsonElement;
    }
}
