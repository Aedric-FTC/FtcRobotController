package org.graphhardlib.tool;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GenerateConfig {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: GenerateConfig <configName> <path/to/ports.json>");
            System.exit(1);
        }
        File input = new File(args[1]);
        if (!input.exists()) {
            System.out.println("JSON file not found: " + input.getAbsolutePath());
            System.exit(1);
        }
        String json = new String(Files.readAllBytes(Paths.get(args[1])), StandardCharsets.UTF_8);
        HardwareConfigWriter.Result r =
                HardwareConfigWriter.writeFromJson(args[0], json, 173, new File("out"));
        System.out.println(r.report());
        if (!r.ok()) System.exit(1);
    }
}