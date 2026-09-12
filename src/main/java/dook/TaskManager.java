package dook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dook.task.Task;
import dook.task.DeadlineTask;
import dook.task.EventTask;
import dook.task.ToDoTask;

import dook.exception.UnknownTaskException;
import dook.exception.EmptyTaskListException;
import dook.exception.TaskListIndexOutOfBoundsException;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private Task[] taskTypes = {
        new ToDoTask(),
        new DeadlineTask(),
        new EventTask()
    };

    private FileManager io;

    public TaskManager(FileManager io) {
        this.io = io;
        load();
    }

    public Task addTask(String userQuery) {
        for (Task taskType : taskTypes) {
            Task newTask = taskType.getNewTask(userQuery);
            if (newTask != null) {
                tasks.add(newTask);
                return newTask;
            }
        }
        throw new UnknownTaskException();
    }

    public Task getTask(int id) {
        try {
            return tasks.get(id);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskListIndexOutOfBoundsException();
        }
    }

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

    public void save() {
        io.writeTasks(tasks.stream()
                            .map(task -> task.save())
                            .toList()
        );
    }

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
