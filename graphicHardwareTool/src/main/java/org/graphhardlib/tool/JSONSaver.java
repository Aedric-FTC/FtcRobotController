package org.graphhardlib.tool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;

public class JSONSaver {
    public static JsonObject SaveData = new JsonObject();
    static Gson gson;
    public static final String PROJECT_ROOT = System.getProperty("user.dir");

    // 2. Build cross-platform paths using Paths.get()
    public static final String HUB_PATH = Paths.get(PROJECT_ROOT,
            "graphicHardwareTool", "src", "main", "java", "org", "graphhardlib", "tool", "saved", "profile"
    ).toString();
    public static final String DEVICE_PATH = Paths.get(PROJECT_ROOT,
            "graphicHardwareTool", "src", "main", "java", "org", "graphhardlib", "tool", "saved", "profile"
    ).toString();

    public static final String SETTINGS_PATH = Paths.get(PROJECT_ROOT,
            "graphicHardwareTool", "src", "main", "java", "org", "graphhardlib", "tool", "saved", "GlobalSettings.json"
    ).toString();
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
    private static void writeToDisk(String filePath, int profile, boolean device)
    {
        gson = new GsonBuilder().setPrettyPrinting().create();
        if (!device)
        {
            try (FileWriter writer = new FileWriter(filePath))
            {
                gson.toJson(SaveData, writer);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public static void save(String name, int value, String path, int profile)
    {
        SaveData.addProperty(name, value);
        writeToDisk(path, profile, false);
    }
    public static void save(String name, double value, String path, int profile)
    {
        SaveData.addProperty(name, value);
        writeToDisk(path, profile, false);
    }
    public static void save(String name, float value, String path, int profile)
    {
        SaveData.addProperty(name, value);
        writeToDisk(path, profile, false);
    }
    public static void save(String name, Number value, String path, int profile)
    {
        SaveData.addProperty(name, value);
        writeToDisk(path, profile, false);
    }
    public static void save(String name, boolean value, String path, int profile)
    {
        SaveData.addProperty(name, value);
        writeToDisk(path, profile, false);
    }
    public static void save(String name, String value, String path, int profile, boolean device)
    {
        SaveData.addProperty(name, value);
        writeToDisk(path, profile, device);
    }
    public static void save(String hub, int profile, Map<String, JTextField> inputData)
    {
        HashMap<String, String> data = new HashMap<>();
        for (Map.Entry<String, JTextField> entry : inputData.entrySet()) {
            data.put(entry.getKey(), entry.getValue().getText());
        }
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(new File(HUB_PATH + profile,  hub + ".json")))
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
        try (FileReader fileReader = new FileReader(new File(HUB_PATH + profile,  hub + ".json"))) {
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
    private static File deviceFile(String hub, int profile)
    {
        return new File(DEVICE_PATH + profile, hub + "Devices.json");   // ctrlDevices.json / expDevices.json
    }
    public static void saveDropdowns(String hub, int profile, Map<String, JComboBox<String>> inputData)
    {
        Map<String, String> data = new LinkedHashMap<>();
        for (Map.Entry<String, JComboBox<String>> entry : inputData.entrySet()) {
            Object selected = entry.getValue().getSelectedItem();
            data.put(entry.getKey(), selected == null ? "" : selected.toString());
        }

        File file = deviceFile(hub, profile);
        file.getParentFile().mkdirs();   // in case the profile folder doesn't exist yet
        gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(file))
        {
            gson.toJson(data, writer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void loadDropdowns(String hub, int profile)
    {
        File file = deviceFile(hub, profile);
        if (!file.exists()) return;

        try (FileReader reader = new FileReader(file))
        {
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> data = new Gson().fromJson(reader, type);
            if (data == null) return;

            data.forEach((label, value) -> {
                JComboBox<String> box = MainWindow.dropDowns.get(label);
                if (box != null && value != null && !value.isEmpty()) box.setSelectedItem(value);
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void wipe()
    {
        gson = new Gson();
        //FileWriter(gson.toJson(new HashMap<>()));
    }
}
