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
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.loader.Loader;
import dook.migrator.Migrator;
import dook.exception.NoSerialisationVersionException;

/**
 * Represents the template for managing the saving and loading of data from local files.
 *
 * This class handles the IOException internally, provides safe and simple read/write-to-files methods.
 */
public abstract class FileIO {
    private static final String METADATA_FILE_NAME = "metadata.txt";
    private static final String SERIALISATION_VERSION_KEY = "SERIALISATION_VERSION=";
    private static final Pattern SERIALISATION_VERSION_PATTERN =
        Pattern.compile(SERIALISATION_VERSION_KEY + "(\\d+)");

    private Path dataDir;
    private Path filePath;
    private Path metadataFile;
    private List<Loader> loaders;
    private List<Migrator> migrators;
    private int appSerialisationVersion;

    protected FileIO(
        Path dataDir,
        int appSerialisationVersion,
        String fileName,
        List<Loader> loaders,
        List<Migrator> migrators
    ) {
        this.dataDir = dataDir;
        this.appSerialisationVersion = appSerialisationVersion;
        this.loaders = loaders;
        this.migrators = migrators;

        filePath = dataDir.resolve(fileName);

        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        metadataFile = dataDir.resolve(METADATA_FILE_NAME);
    }

    private boolean tryWriteSafely(Path file, List<String> lines) {
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

    private void writeMetadata() {
        List<String> lines = List.of(
            SERIALISATION_VERSION_KEY + appSerialisationVersion
        );
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
            e.printStackTrace();
        }
        return List.of();
    }

    private int readSerialisationVersion() {
        List<String> lines = tryReadSafely(metadataFile);
        for (String line : lines) {
            Matcher matcher = SERIALISATION_VERSION_PATTERN.matcher(line);
            if (matcher.matches()) {
                int dataSerialisationVersion = Integer.parseInt(matcher.group(1));
                if (dataSerialisationVersion > appSerialisationVersion) {
                    throw new NoSerialisationVersionException();
                }
                return dataSerialisationVersion;
            }
        }
        throw new NoSerialisationVersionException();
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
        int dataSerialisationVersion;
        try {
            dataSerialisationVersion = readSerialisationVersion();
        } catch (NoSerialisationVersionException e) {
            e.printStackTrace();
            return List.of();
        }

        List<String> lines = tryReadSafely(filePath);
        List<SerialisedData> content = loaders.get(dataSerialisationVersion).load(lines);

        for (int version = dataSerialisationVersion; version < appSerialisationVersion; version++) {
            content = migrators.get(version).migrate(content);
        }

        return content;
    }
}
