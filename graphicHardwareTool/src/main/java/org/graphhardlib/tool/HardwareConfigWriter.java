package org.graphhardlib.tool;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts a flat "port label -> value" JSON object into an FTC robot configuration
 * XML file for a single Control Hub and saves it to the Robot Controller.
 *
 * JSON keys (one hub):
 *   MOTOR 0-3, SERVO 0-5, I2C 0-3                     single-port labels
 *   ANALOG 0-1, ANALOG 2-3, DIGITAL 0-1 ... 6-7       two-channel connectors
 *   ENCODER 0-3, UART 2/4, RS485 6/8                  accepted but not written (see warnings)
 *
 * Values:  "" = unused,  "name" = device with the default type for that port,
 *          "name:Type" = device with an explicit XML type.
 * Two-channel connectors take "lowChannelName,highChannelName"; leave one side empty
 * to skip it, e.g. ",limitSwitch". A single name goes to the lower channel.
 * I2C defaults to the generic I2cDeviceSynch. Use "name:Type" (e.g. "imu:LynxEmbeddedIMU")
 * only when you need the SDK's built-in driver for a specific sensor.
 */
public final class HardwareConfigWriter {

    private static final int DEFAULT_CONTROL_HUB_ADDRESS = 173;

    private static final Pattern SINGLE = Pattern.compile("^(MOTOR|SERVO|ENCODER|UART|RS485|I2C)\\s+(\\d+)$");
    private static final Pattern PAIR = Pattern.compile("^(DIGITAL|ANALOG)\\s+(\\d+)\\s*-\\s*(\\d+)$");

    /** Highest valid port (inclusive) per category. */
    private static final Map<String, Integer> MAX_PORT = new HashMap<String, Integer>();
    /** Default XML tag when a value omits ":Type". */
    private static final Map<String, String> DEFAULT_TYPE = new HashMap<String, String>();
    /** XML tags this writer has been checked against. Others are allowed but produce a warning. */
    private static final Set<String> KNOWN_TYPES = new HashSet<String>();

    static {
        MAX_PORT.put("MOTOR", 3);
        MAX_PORT.put("SERVO", 5);
        MAX_PORT.put("I2C", 3);
        MAX_PORT.put("ANALOG", 3);
        MAX_PORT.put("DIGITAL", 7);

        DEFAULT_TYPE.put("MOTOR", "Motor");
        DEFAULT_TYPE.put("SERVO", "Servo");
        DEFAULT_TYPE.put("ANALOG", "AnalogInput");
        DEFAULT_TYPE.put("DIGITAL", "DigitalDevice");
        DEFAULT_TYPE.put("I2C", "I2cDeviceSynch");

        String[] known = {
                "Motor", "goBILDA5201SeriesMotor", "goBILDA5202SeriesMotor",
                "RevRobotics20HDHexMotor", "RevRobotics40HDHexMotor", "RevRoboticsCoreHexMotor",
                "Servo", "ContinuousRotationServo",
                "AnalogInput", "DigitalDevice", "RevTouchSensor", "RevLED",
                "LynxEmbeddedIMU", "RevColorSensorV3", "I2cDeviceSynch"
        };
        for (String k : known) KNOWN_TYPES.add(k);
    }

    private HardwareConfigWriter() {}

    // ------------------------------------------------------------------
    // Result object: everything a user needs to debug in one place
    // ------------------------------------------------------------------

    public static final class Result {
        public final List<String> errors = new ArrayList<String>();
        public final List<String> warnings = new ArrayList<String>();
        public String xml;
        public File file;

        public boolean ok() { return errors.isEmpty() && file != null; }

        /** Multi-line summary, handy for telemetry or logcat. */
        public String report() {
            StringBuilder sb = new StringBuilder();
            if (ok()) sb.append("Config written: ").append(file.getAbsolutePath()).append('\n');
            for (String e : errors) sb.append("ERROR: ").append(e).append('\n');
            for (String w : warnings) sb.append("WARN: ").append(w).append('\n');
            return sb.toString().trim();
        }
    }

    private static final class Device {
        final String name;
        final String type;
        Device(String name, String type) { this.name = name; this.type = type; }
    }

    // ------------------------------------------------------------------
    // Public API
    // ------------------------------------------------------------------

    /** Where the Robot Controller looks for configuration files. */
    public static final File DEFAULT_CONFIG_DIR = new File("/sdcard/FIRST");

    public static Result writeFromJson(String configName, String json) {
        return writeFromJson(configName, json, DEFAULT_CONTROL_HUB_ADDRESS, DEFAULT_CONFIG_DIR);
    }

    public static Result writeFromJson(String configName, String json, int hubAddress) {
        return writeFromJson(configName, json, hubAddress, DEFAULT_CONFIG_DIR);
    }

    /** Validates, builds, and saves into the given folder. */
    public static Result writeFromJson(String configName, String json, int hubAddress, File dir) {
        Result result = build(configName, json, hubAddress);
        if (!result.errors.isEmpty()) return result;

        File target = new File(dir, configName + ".xml");
        File temp = new File(dir, configName + ".xml.tmp");
        try {
            if (!dir.exists() && !dir.mkdirs()) {
                result.errors.add("Could not create folder " + dir.getAbsolutePath());
                return result;
            }
            Writer w = new OutputStreamWriter(new FileOutputStream(temp), Charset.forName("UTF-8"));
            try {
                w.write(result.xml);
            } finally {
                w.close();
            }
            if (target.exists() && !target.delete()) {
                result.errors.add("Could not replace existing file " + target.getAbsolutePath());
                return result;
            }
            if (!temp.renameTo(target)) {
                result.errors.add("Could not rename temp file to " + target.getAbsolutePath());
                return result;
            }
            result.file = target;
        } catch (IOException e) {
            result.errors.add("Write failed: " + e.getMessage());
        }
        return result;
    }

    /** Validates and builds the XML without touching disk (useful for dry runs). */
    public static Result build(String configName, String json, int hubAddress) {
        Result result = new Result();

        if (configName == null || !configName.matches("[A-Za-z0-9_\\- ]+")) {
            result.errors.add("configName must be non-empty and use only letters, digits, space, '_' or '-'. Got: \"" + configName + "\"");
        }

        if (json == null) {
            result.errors.add("Invalid JSON: input is null.");
            return result;
        }
        JsonObject root;
        try {
            JsonElement parsed = JsonParser.parseString(json);
            if (parsed == null || !parsed.isJsonObject()) {
                result.errors.add("Invalid JSON: the top level must be an object of port labels to names.");
                return result;
            }
            root = parsed.getAsJsonObject();
        } catch (JsonParseException e) {
            result.errors.add("Invalid JSON: " + e.getMessage());
            return result;
        }

        // category -> port -> device, kept sorted by port
        Map<String, TreeMap<Integer, Device>> byCategory = new HashMap<String, TreeMap<Integer, Device>>();
        for (String c : new String[]{"MOTOR", "SERVO", "ANALOG", "DIGITAL", "I2C"}) {
            byCategory.put(c, new TreeMap<Integer, Device>());
        }
        Set<String> allNames = new HashSet<String>();

        for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
            String key = entry.getKey();
            String label = key.trim().toUpperCase();

            JsonElement raw = entry.getValue();
            if (!raw.isJsonPrimitive() || !raw.getAsJsonPrimitive().isString()) {
                result.errors.add("\"" + key + "\": value must be a string (use \"\" for unused).");
                continue;
            }
            String value = raw.getAsString().trim();

            Matcher single = SINGLE.matcher(label);
            Matcher pair = PAIR.matcher(label);

            if (single.matches()) {
                handleSingle(key, single.group(1), Integer.parseInt(single.group(2)), value,
                        byCategory, allNames, result);
            } else if (pair.matches()) {
                handlePair(key, pair.group(1), Integer.parseInt(pair.group(2)),
                        Integer.parseInt(pair.group(3)), value, byCategory, allNames, result);
            } else {
                result.errors.add("Unknown port label \"" + key + "\". Expected things like \"MOTOR 0\", "
                        + "\"SERVO 3\", \"I2C 1\", \"DIGITAL 2-3\", \"ANALOG 0-1\".");
            }
        }

        if (!result.errors.isEmpty()) return result;

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>\n");
        xml.append("<Robot type=\"FirstInc-Revolution\">\n");
        xml.append("  <LynxUsbDevice name=\"Control Hub Portal\" serialNumber=\"(embedded)\" parentModuleAddress=\"")
                .append(hubAddress).append("\">\n");
        xml.append("    <LynxModule name=\"Control Hub\" port=\"").append(hubAddress).append("\">\n");

        for (String category : new String[]{"MOTOR", "SERVO", "ANALOG", "DIGITAL", "I2C"}) {
            for (Map.Entry<Integer, Device> e : byCategory.get(category).entrySet()) {
                Device d = e.getValue();
                xml.append("      <").append(d.type)
                        .append(" name=\"").append(esc(d.name)).append("\"")
                        .append(" port=\"").append(e.getKey()).append("\"");
                if (category.equals("I2C")) xml.append(" bus=\"").append(e.getKey()).append("\"");
                xml.append(" />\n");
            }
        }

        xml.append("    </LynxModule>\n");
        xml.append("  </LynxUsbDevice>\n");
        xml.append("</Robot>\n");
        result.xml = xml.toString();
        return result;
    }

    // ------------------------------------------------------------------
    // Internals
    // ------------------------------------------------------------------

    private static void handleSingle(String key, String category, int port, String value,
                                     Map<String, TreeMap<Integer, Device>> byCategory,
                                     Set<String> allNames, Result result) {
        if (value.isEmpty()) return;

        if (category.equals("ENCODER") || category.equals("UART") || category.equals("RS485")) {
            result.warnings.add("\"" + key + "\" is set but " + category + " ports aren't written to the config. "
                    + (category.equals("ENCODER")
                    ? "Encoders use the motor on the matching port (MOTOR " + port + ")."
                    : "Ignored."));
            return;
        }

        int max = MAX_PORT.get(category);
        if (port > max) {
            result.errors.add("\"" + key + "\": port " + port + " is out of range (0-" + max + ").");
            return;
        }
        Device d = parseDevice(key, category, value, allNames, result);
        if (d != null) byCategory.get(category).put(port, d);
    }

    private static void handlePair(String key, String category, int low, int high, String value,
                                   Map<String, TreeMap<Integer, Device>> byCategory,
                                   Set<String> allNames, Result result) {
        if (high != low + 1 || low % 2 != 0 || high > MAX_PORT.get(category)) {
            result.errors.add("\"" + key + "\" is not a valid connector. Pairs run 0-1, 2-3"
                    + (category.equals("DIGITAL") ? ", 4-5, 6-7." : "."));
            return;
        }
        if (value.isEmpty()) return;

        String[] parts = value.split(",", -1);
        if (parts.length > 2) {
            result.errors.add("\"" + key + "\": too many entries. Use \"lowChannel,highChannel\" (at most one comma).");
            return;
        }
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();
            if (part.isEmpty()) continue;
            Device d = parseDevice(key, category, part, allNames, result);
            if (d != null) byCategory.get(category).put(low + i, d);
        }
    }

    /** Parses "name" or "name:Type". Returns null (after recording an error) if invalid. */
    private static Device parseDevice(String key, String category, String text,
                                      Set<String> allNames, Result result) {
        String name = text;
        String type = null;
        int colon = text.indexOf(':');
        if (colon >= 0) {
            name = text.substring(0, colon).trim();
            type = text.substring(colon + 1).trim();
        }

        if (name.isEmpty()) {
            result.errors.add("\"" + key + "\": device name is empty in \"" + text + "\".");
            return null;
        }
        if (type == null || type.isEmpty()) type = DEFAULT_TYPE.get(category);
        if (type == null) {
            result.errors.add("\"" + key + "\": " + category + " devices need an explicit type, "
                    + "e.g. \"" + name + ":LynxEmbeddedIMU\".");
            return null;
        }
        if (!type.matches("[A-Za-z][A-Za-z0-9_]*")) {
            result.errors.add("\"" + key + "\": \"" + type + "\" is not a valid XML tag name.");
            return null;
        }
        if (!allNames.add(name)) {
            result.errors.add("\"" + key + "\": device name \"" + name
                    + "\" is already used. Names must be unique across the whole robot.");
            return null;
        }
        if (!KNOWN_TYPES.contains(type)) {
            result.warnings.add("\"" + key + "\": type \"" + type + "\" is not in the checked list. "
                    + "Compare it to a config exported from the Driver Station if the robot rejects the file.");
        }
        return new Device(name, type);
    }

    private static String esc(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '&': sb.append("&amp;"); break;
                case '<': sb.append("&lt;"); break;
                case '>': sb.append("&gt;"); break;
                case '"': sb.append("&quot;"); break;
                case '\'': sb.append("&apos;"); break;
                default: sb.append(c);
            }
        }
        return sb.toString();
    }
}