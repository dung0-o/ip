package dook.service;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import dook.io.TaskFileIO;

/**
 * Manages the local files.
 */
public class FileManager {
    private static final int APP_SERIALISATION_VERSION = 1;
    private static final Path LOG_DIR = Paths.get("logs");
    private static final String LOG_FILENAME = "logs/dook.log";
    private static final Logger LOGGER =
        Logger.getLogger(FileManager.class.getName());

    private static Logger rootLogger = Logger.getLogger("");

    private TaskFileIO taskFileIO;

    /**
     * Constructs a new FileManager with the specified data directory.
     *
     * @param  dataDirName Relative path of the data directory.
     */
    public FileManager(String dataDirName) {
        configLogger();

        Path dataDir = Paths.get(dataDirName);

        try {
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Data folder is missing and cannot be created.", e);
        }

        taskFileIO = new TaskFileIO(dataDir, APP_SERIALISATION_VERSION);
    }

    public TaskFileIO getTaskFileIO() {
        return taskFileIO;
    }

    private static void configLogger() {
        for (Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
            handler.close();
        }

        try {
            Files.createDirectories(LOG_DIR);
            FileHandler fileHandler = new FileHandler(LOG_FILENAME, false);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);
            rootLogger.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
