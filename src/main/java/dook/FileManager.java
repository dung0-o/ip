package dook;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.NoSuchFileException;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.migrator.Migrator;
import dook.exception.NoSerialisationVersionException;

/**
 * Manages the saving and loading of data from local files.
 *
 * This class handles the IOException internally,
 * provides safe and simple read/write-to-files methods.
 */
public class FileManager {
    private final int SERIALISATION_VERSION = 0;
    private final Pattern SERIALISATION_VERSION_PATTERN =
        Pattern.compile("SERIALISATION_VERSION=(\\d+)");

    private final String METADATA_FILE_NAME = "metadata.txt";
    private final String TASK_FILE_NAME = "task.txt";
    private final String DELIMITER = "|";
    private final List<Migrator> MIGRATORS = List.of();

    private Path dataDir;
    private Path taskFile;
    private Path metadataFile;

    /**
     * Constructs a new FileManager with the specified data directory.
     *
     * @param  dataDirName Relative path of the data directory.
     */
    public FileManager(String dataDirName) {
        dataDir = Paths.get(dataDirName);
        taskFile = dataDir.resolve(TASK_FILE_NAME);
        metadataFile = dataDir.resolve(METADATA_FILE_NAME);

        try {
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
            if (!Files.exists(taskFile)) {
                Files.createFile(taskFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to write content to temp file, then copy to target file.
     *
     * @param  file  Path object of target file.
     * @param  lines Content to be written.
     * @return       Whether the writing was done without issues.
     */
    public boolean tryWriteSafely(Path file, List<String> lines) {
        try {
            String fileName = file.getFileName().toString();
            Path tempFile = Files.createTempFile(dataDir, fileName, ".tmp");
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            Files.move(tempFile, file,
                       StandardCopyOption.REPLACE_EXISTING,
                       StandardCopyOption.ATOMIC_MOVE);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Saves the program serialisation version to local file.
     */
    public void writeMetadata() {
        List<String> lines = List.of(
            "SERIALISATION_VERSION=" + SERIALISATION_VERSION
        );
        tryWriteSafely(metadataFile, lines);
    }

    /**
     * Saves the serialised tasks to local file.
     * Update the serialisation version.
     *
     * @param content Serialised task list.
     */
    public void writeTasks(List<List<String>> content) {
        List<String> lines = content.stream()
                                    .map(row -> String.join(DELIMITER, row))
                                    .toList();
        if (tryWriteSafely(taskFile, lines)) {
            writeMetadata();
        }
    }

    /**
     * Get the local serialisation version from local metadata file.
     *
     * @return Serialisation version of the local files.
     * @throws NoSerialisationVersionException If local serialisation version is missing.
     */
    public int readSerialisationVersion() {
        try (BufferedReader reader = Files.newBufferedReader(metadataFile)) {
            String line = reader.readLine();
            while (line != null) {
                Matcher matcher = SERIALISATION_VERSION_PATTERN.matcher(line);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                line = reader.readLine();
            }
        } catch (NoSuchFileException e) {
            throw new NoSerialisationVersionException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new NoSerialisationVersionException();
    }

    /**
     * Get the task list in serialised form from the local file.
     * If local serialisation version is missing, do not trust the local file content.
     * If local serialisation version is outdated, perform incremental migration.
     *
     * @return Serialised task list.
     */
    public List<List<String>> readTasks() {
        List<List<String>> content = new ArrayList<>();
        int serialisationVersion;
        try {
            serialisationVersion = readSerialisationVersion();
        } catch (NoSerialisationVersionException e) {
            return content;
        }

        try (BufferedReader reader = Files.newBufferedReader(taskFile)) {
            String line = reader.readLine();
            while (line != null) {
                List<String> row = List.of(line.split(Pattern.quote(DELIMITER)));
                content.add(row);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (serialisationVersion < SERIALISATION_VERSION) {
            content = MIGRATORS.get(serialisationVersion).migrate(content);
            serialisationVersion++;
        }

        return content;
    }
}
