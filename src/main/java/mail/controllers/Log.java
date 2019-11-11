package mail.controllers;

import common.Config;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

public class Log {

    private static final String LOG_FILE_NAME = "Log.txt";

    private static Path logFilePath;
    private static FileAttribute<Set<PosixFilePermission>> attr;

    static {
        logFilePath = Paths.get("").toAbsolutePath().resolve(Config.getLogFileName());
    }

    public static void write(String message) {
        try (BufferedWriter out = Files.newBufferedWriter(logFilePath, StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            out.write(message);
            out.newLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
