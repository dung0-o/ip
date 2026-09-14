package dook;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collection;

import dook.task.Task;
import dook.task.DeadlineTask;
import dook.task.EventTask;
import dook.task.ToDoTask;

import dook.exception.UnknownTaskException;
import dook.exception.EmptyTaskListException;
import dook.exception.TaskListIndexOutOfBoundsException;

/**
 * Manages the modification and viewing of the task list.
 */
public class TaskManager {
    @FunctionalInterface
    private interface TaskParser {
        Optional<Task> parse(String input);
    }

    private final List<TaskParser> PARSERS = List.of(
        ToDoTask::parse,
        DeadlineTask::parse,
        EventTask::parse
    );

    private List<Task> tasks = new ArrayList<>();
    private FileManager io;

    /**
     * Constructs a new TaskManager with the specified file manager.
     *
     * @param  io The file manager, for storing and loading with local files.
     */
    public TaskManager(FileManager io) {
        this.io = io;
        load();
    }

    /**
     * Parses a user input into a new task.
     * Inserts it to the back of the task list.
     *
     * @param  userQuery Trimmed string input.
     * @return           The newly created task.
     * @throws UnknownTaskException If parsing fails.
     */
    public Task addTask(String userQuery) {
        for (TaskParser parser : PARSERS) {
            Optional<Task> maybeTask = parser.parse(userQuery);
            if (maybeTask.isPresent()) {
                Task newTask = maybeTask.get();
                tasks.add(newTask);
                return newTask;
            }
        }
        throw new UnknownTaskException();
    }

    /**
     * Inserts a task at the specified position.
     *
     * @param taskIndex The position to be inserted.
     * @param task      The new task.
     */
    public void addTask(int taskIndex, Task task) {
        tasks.add(taskIndex, task);
    }

    /**
     * Inserts multiple tasks to the back of the task list.
     *
     * @param tasks Multiple tasks to be inserted.
     */
    public void addTasks(Collection<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    /**
     * Retrieves the task at the specified position.
     *
     * @param  id The position in the task list.
     * @return    The task.
     * @throws TaskListIndexOutOfBoundsException If the specified position is invalid.
     */
    public Task getTask(int id) {
        try {
            return tasks.get(id);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskListIndexOutOfBoundsException();
        }
    }

    /**
     * Retrieves a copy the task list.
     *
     * @return The copy of task list in immutable list form.
     */
    public List<Task> getAllTasks() {
        return List.copyOf(tasks);
    }

    /**
     * Removes the task at the specified position.
     *
     * @param  id The position in the task list.
     * @return    The task.
     * @throws TaskListIndexOutOfBoundsException If the specified position is invalid.
     */
    public Task deleteTask(int id) {
        try {
            return tasks.remove(id);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskListIndexOutOfBoundsException();
        }
    }

    /**
     * Removes all tasks in the current task list.
     */
    public void deleteAllTasks() {
        tasks.clear();
    }

    /**
     * Removes the task at the back of the task list.
     */
    public void deleteLastTask() {
        tasks.removeLast();
    }

    /**
     * Returns the list of task as a string in pretty format.
     *
     * @return Multi-line string format of the task list.
     */
    public String listTasks() {
        if (tasks.isEmpty()) {
            throw new EmptyTaskListException();
        }

        String format = "%" + (tasks.size() / 10 + 1) + "d.";

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(format, 1))
          .append(tasks.get(0));

        for (int i = 1; i < tasks.size(); i++) {
            sb.append("\n")
              .append(String.format(format, i+1))
              .append(tasks.get(i));
        }
        return sb.toString();
    }

    /**
     * Stores the task list to local file.
     */
    public void save() {
        io.writeTasks(tasks.stream()
                           .map(task -> task.save())
                           .toList()
        );
    }

    /**
     * Loads the task list from local file.
     */
    public void load() {
        List<List<String>> content = io.readTasks();
        for (List<String> row : content) {
            try {
                Object[] constructorArgs = row.subList(2, row.size()).toArray();
                Class<?>[] paramTypes = new Class<?>[constructorArgs.length];
                Arrays.fill(paramTypes, String.class);

                Task task = (Task) Class.forName(row.get(0))
                                        .getConstructor(paramTypes)
                                        .newInstance(constructorArgs);
                boolean isDone = Boolean.parseBoolean(row.get(1));
                task.setDone(isDone);
                tasks.add(task);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
