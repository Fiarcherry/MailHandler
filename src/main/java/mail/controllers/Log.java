package mail.controllers;

import common.Config;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
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
        try (BufferedWriter out = Files.newBufferedWriter(logFilePath, Charset.forName("UTF-8"), StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            out.write(message);
            out.newLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
