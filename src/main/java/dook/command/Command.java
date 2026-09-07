package dook.command;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public abstract class Command {
    protected TaskManager taskManager;
    protected Random random;
    private Pattern pattern;

    public Command(TaskManager taskManager, Random random, Pattern pattern) {
        this.taskManager = taskManager;
        this.random = random;
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public abstract Response execute(Matcher matcher);
}
