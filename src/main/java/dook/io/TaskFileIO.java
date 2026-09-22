package dook.io;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.nio.file.Path;

import dook.loader.Loader;
import dook.loader.TaskLoader0;
import dook.loader.TaskLoader1;
import dook.migrator.Migrator;
import dook.migrator.TaskMigrator0;
import dook.task.Task;

/**
 * Manages the saving and loading of data from local files.
 *
 * This class handles the IOException internally,
 * provides safe and simple read/write-to-files methods.
 */
public class TaskFileIO extends FileIO {
    private static final String FILE_NAME = "task.txt";
    private static final List<Loader> LOADERS = List.of(
        TaskLoader0::load,
        TaskLoader1::load
    );
    private static final List<Migrator> MIGRATORS = List.of(
        TaskMigrator0::migrate
    );

    /**
     * Constructs a new FileManager with the specified data directory.
     *
     * @param  dataDirName Relative path of the data directory.
     */
    public TaskFileIO(Path dataDir, int appSerialisationVersion) {
        super(dataDir, appSerialisationVersion, FILE_NAME, LOADERS, MIGRATORS);
    }

    public void serialise(List<Task> tasks) {
        List<SerialisedData> content = tasks.stream()
                                            .map(task -> task.serialise())
                                            .toList();
        writeFile(content);
    }

    public List<Task> restore() {
        List<SerialisedData> content = readFile();
        List<Task> tasks = new ArrayList<>();

        for (SerialisedData data : content) {
            List<?> details = data.getDetails();
            Object[] constructorArgs = details.subList(1, details.size())
                                              .toArray();
            Class<?>[] paramTypes = Arrays.stream(constructorArgs)
                                          .map(Object::getClass)
                                          .toArray(size -> new Class<?>[size]);
            try {
                Task task = (Task) data.getKlass()
                                       .getConstructor(paramTypes)
                                       .newInstance(constructorArgs);
                task.setDone((Boolean) details.get(0));
                tasks.add(task);
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
        return tasks;
    }
}
