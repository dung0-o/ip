package dook.command;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public abstract class Command {
    protected TaskManager taskManager;
    private Pattern pattern;

    public Command(TaskManager taskManager, Pattern pattern) {
        this.taskManager = taskManager;
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public abstract Response execute(Matcher matcher);
}
