package org.firstinspires.ftc.teamcode.Json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class json
{
    public json()
    {
        declareFilePath();
    }
    JsonObject configFile = new JsonObject();
    Gson gson = new Gson();
    String fileName;
    String filePath;
    public void nameFile(String newFileName)
    {
        fileName = newFileName;
    }
    public void declareFilePath()
    {
        if (fileName != null)
        {
            filePath = "/sdcard/FIRST/" + fileName + ".json";
        }
        else
        {
            filePath = "/sdcard/FIRST/configFile.json";
        }
    }

    public void writeJsonProperty(String identifier, Object value)
    {
        String newValue = value.toString();
        configFile.addProperty(identifier, newValue);

        try (FileWriter writer = new FileWriter(filePath))
        {
            gson.toJson(configFile, writer);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error Writing Json File " + fileName);
        }
    }
    public Object getJsonProperty(String identifier)
    {
        try (FileReader reader = new FileReader(filePath))
        {
            JsonObject configFile = gson.fromJson(reader, JsonObject.class);

            return configFile.get(identifier).getAsString();
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error Reading Json File " + fileName);
        }
    }
}
