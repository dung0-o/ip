package dook.command;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public abstract class Command {
    private final String BAR = "_".repeat(60);
    protected TaskManager taskManager;
    private Pattern pattern;

    public Command(TaskManager taskManager, Pattern pattern) {
        this.taskManager = taskManager;
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    protected void printReply(String msg) {
        System.out.print("\n" + msg + "\n" + BAR + "\n\n> ");
    }

    protected void printReply(String msg, Task task) {
        System.out.print("\n" + msg + "\n  " + task + "\n" + BAR + "\n\n> ");
    }

    public abstract void execute(Matcher matcher);
}
