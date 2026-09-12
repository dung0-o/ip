package dook;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class FileManager {
    private final String TASK_FILE_NAME = "task.txt";
    private final String DELIMITER = "|";
    private Path dataDir;
    private Path taskFile;

    public FileManager(String dataDirName) {
        dataDir = Paths.get(dataDirName);
        taskFile = dataDir.resolve(TASK_FILE_NAME);

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

    public void writeTasks(List<List<String>> content) {
        try (BufferedWriter writer = Files.newBufferedWriter(taskFile)) {
            for (List<String> row : content) {
                String line = String.join(DELIMITER, row);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> readTasks() {
        List<List<String>> content = new ArrayList<>();
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
        return content;
    }
}
