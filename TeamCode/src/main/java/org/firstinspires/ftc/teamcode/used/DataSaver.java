package org.firstinspires.ftc.teamcode.used;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataSaver {
    static Gson gson;
    public static JsonObject saveData = new JsonObject();
    public void saveThis(String label, double value) {
        gson = new GsonBuilder().setPrettyPrinting().create();
        saveData.addProperty(label, value);
        try(FileWriter writer = new FileWriter("TeamCode/src/java/org/firstinspires/ftc/teamcode/saveData/SaveData.json"))
        {
            gson.toJson(saveData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveThis(String label, int value) {
        gson = new GsonBuilder().setPrettyPrinting().create();
        saveData.addProperty(label, value);
        try(FileWriter writer = new FileWriter("TeamCode/src/java/org/firstinspires/ftc/teamcode/saveData/SaveData.json"))
        {
            gson.toJson(saveData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveThis(String label, boolean value) {
        gson = new GsonBuilder().setPrettyPrinting().create();
        saveData.addProperty(label, value);
        try(FileWriter writer = new FileWriter("TeamCode/src/java/org/firstinspires/ftc/teamcode/saveData/SaveData.json"))
        {
            gson.toJson(saveData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveThis(String label, String value) {
        gson = new GsonBuilder().setPrettyPrinting().create();
        saveData.addProperty(label, value);
        try(FileWriter writer = new FileWriter("TeamCode/src/java/org/firstinspires/ftc/teamcode/saveData/SaveData.json"))
        {
            gson.toJson(saveData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonElement element;
    public static JsonElement loadThis(String label)
    {
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileReader reader = new FileReader("TeamCode/src/java/org/firstinspires/ftc/teamcode/saveData/SaveData.json")) {
            saveData = gson.fromJson(reader, JsonObject.class);

            if (saveData != null && saveData.has(label))
            {
                element = saveData.get(label).getAsJsonPrimitive();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            element = null;
        }
        return element;
    }
}
