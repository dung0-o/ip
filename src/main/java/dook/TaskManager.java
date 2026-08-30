package dook;

import java.util.ArrayList;
import java.util.List;

import dook.task.Task;
import dook.task.DeadlineTask;
import dook.task.EventTask;
import dook.task.ToDoTask;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private Task[] taskTypes = {
        new ToDoTask(),
        new DeadlineTask(),
        new EventTask()
    };

    public Task addTask(String userQuery) {
        for (Task taskType : taskTypes) {
            Task newTask = taskType.getNewTask(userQuery);
            if (newTask != null) {
                tasks.add(newTask);
                return newTask;
            }
        }
        return null;
    }

    public Task getTask(int id) {
        if (id >= 0 && id < tasks.size()) {
            return tasks.get(id);
        }
        return null;
    }

    public boolean isEmpty() {
        return tasks.size() == 0;
    }

    public String listTasks() {
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
