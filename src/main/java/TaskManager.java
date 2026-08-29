import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();

    public void addTask(String description) {
        tasks.add(new Task(description));
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
        String msg = String.format(format, 1) + tasks.get(0);
        for (int i = 1; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            msg += "\n" + String.format(format, i+1) + task;
        }
        return msg;
    }
}