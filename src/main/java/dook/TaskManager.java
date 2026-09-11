package dook;

import java.util.List;
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

    public void addTask(int taskIndex, Task task) {
        tasks.add(taskIndex, task);
    }

    public void addTasks(Collection<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    public Task getTask(int id) {
        try {
            return tasks.get(id);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskListIndexOutOfBoundsException();
        }
    }

    public List<Task> getAllTasks() {
        return List.copyOf(tasks);
    }

    public Task deleteTask(int id) {
        try {
            return tasks.remove(id);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskListIndexOutOfBoundsException();
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteLastTask() {
        tasks.removeLast();
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
}
