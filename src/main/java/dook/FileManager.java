package dook;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import dook.io.TaskFileIO;

/**
 * Manages the local files.
 */
public class FileManager {
    private final int APP_SERIALISATION_VERSION = 0;
    private TaskFileIO taskFileIO;

    /**
     * Constructs a new FileManager with the specified data directory.
     *
     * @param  dataDirName Relative path of the data directory.
     */
    public FileManager(String dataDirName) {
        Path dataDir = Paths.get(dataDirName);

        try {
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        taskFileIO = new TaskFileIO(dataDir, APP_SERIALISATION_VERSION);
    }

    public TaskFileIO getTaskFileIO() {
        return taskFileIO;
    }
}
