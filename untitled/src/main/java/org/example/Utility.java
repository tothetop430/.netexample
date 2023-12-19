package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utility {
    public static String read(String filePath) throws IOException {
        String content= new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        return content;
    }
}
