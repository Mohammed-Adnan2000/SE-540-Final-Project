package edu.depaul;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileLogger implements Logger {
    private String logFilePath;

    public FileLogger(String filePath) {
        this.logFilePath = filePath;
    }

    private void writeToFile(String message) {
        try {
            Files.write(Paths.get(logFilePath), (message + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    @Override
    public void log(String message) {
        writeToFile("LOG: " + message);
    }

    @Override
    public void error(String message) {
        writeToFile("ERROR: " + message);
    }

    @Override
    public void debug(String message) {
        writeToFile("DEBUG: " + message);
    }
}
