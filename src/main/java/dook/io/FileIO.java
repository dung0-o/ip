package dook.io;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.logging.Level;
import java.util.logging.Logger;

import dook.io.loader.Loader;
import dook.io.migrator.Migrator;

/**
 * Represents the template for managing the saving and loading of data from local files.
 *
 * This class handles the IOException internally, provides safe and simple read/write-to-files methods.
 */
public abstract class FileIO {
    private static final String METADATA_FILE_NAME = "metadata.txt";
    private static final String VERSION_KEY = "SERIALISATION_VERSION=";
    private static final Pattern VERSION_PATTERN =
        Pattern.compile(VERSION_KEY + "(\\d+)");

    private static final Logger LOGGER =
        Logger.getLogger(FileIO.class.getName());

    private Path dataDir;
    private Path filePath;
    private Path metadataFile;
    private List<Loader> loaders;
    private List<Migrator> migrators;
    private int appVersion;

    protected FileIO(
        Path dataDir,
        int appVersion,
        String fileName,
        List<Loader> loaders,
        List<Migrator> migrators
    ) {
        this.dataDir = dataDir;
        this.appVersion = appVersion;
        this.loaders = loaders;
        this.migrators = migrators;

        filePath = dataDir.resolve(fileName);

        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

        } catch (IOException e) {
            LOGGER.log(
                Level.WARNING,
                "File %s is missing and cannot be created".formatted(fileName),
                e
            );
        }

        metadataFile = dataDir.resolve(METADATA_FILE_NAME);
    }

    private boolean tryWriteSafely(Path file, List<String> lines) {
        String fileName = file.getFileName().toString();

        try {
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
            LOGGER.log(Level.WARNING, "Cannot write to file " + fileName, e);
        }
        return false;
    }

    private void writeMetadata() {
        List<String> lines = List.of(VERSION_KEY + appVersion);
        tryWriteSafely(metadataFile, lines);
    }

    private List<String> tryReadSafely(Path file) {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            List<String> lines = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            return lines;
        } catch (IOException e) {
            LOGGER.log(
                Level.WARNING,
                "Cannot read from file " + file.getFileName().toString(),
                e
            );
        }
        return List.of();
    }

    private OptionalInt readSerialisationVersion() {
        List<String> lines = tryReadSafely(metadataFile);
        for (String line : lines) {
            Matcher matcher = VERSION_PATTERN.matcher(line);
            if (matcher.matches()) {
                int dataVersion = Integer.parseInt(matcher.group(1));
                if (dataVersion > appVersion) {
                    LOGGER.warning("App serialisation version is outdated to read data.");
                    return OptionalInt.empty();
                }

                return OptionalInt.of(dataVersion);
            }
        }

        LOGGER.warning("No serialisation version found, skipping loading the data.");
        return OptionalInt.empty();
    }

    /**
     * Saves the serialised data to local file.
     * Update the serialisation version.
     *
     * @param content Serialised data.
     */
    protected void writeFile(List<SerialisedData> content) {
        List<String> lines = content.stream()
                                    .map(data -> data.toString())
                                    .toList();
        if (tryWriteSafely(filePath, lines)) {
            writeMetadata();
        }
    }

    /**
     * Retrieves serialised data from the local file.
     * If local serialisation version is missing, do not trust the local file content.
     * If local serialisation version is outdated, perform incremental migration.
     *
     * @return Serialised data.
     */
    protected List<SerialisedData> readFile() {
        OptionalInt maybeVersion = readSerialisationVersion();
        if (maybeVersion.isEmpty()) {
            return List.of();
        }

        int dataVersion = maybeVersion.getAsInt();

        List<String> lines = tryReadSafely(filePath);
        List<SerialisedData> content = loaders.get(dataVersion).load(lines);

        for (int version = dataVersion; version < appVersion; version++) {
            content = migrators.get(version).migrate(content);
            LOGGER.info("Migrating data from version %d to %d.".formatted(
                version, version + 1
            ));
        }

        return content;
    }
}
