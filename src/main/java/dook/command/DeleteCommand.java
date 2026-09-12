package dook.command;

import java.util.Deque;
import java.util.Random;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public class DeleteCommand extends Command {
    private static final Pattern PATTERN = Pattern.compile("^delete\\s+(\\d+)$");
    private int taskIndex;
    private Task task;

    public DeleteCommand(String userQuery, int taskIndex) {
        super(userQuery);
        this.taskIndex = taskIndex;
    }

    public static Optional<Command> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
        return Optional.of(new DeleteCommand(userQuery, taskIndex));
    }

    @Override
    public Response execute(
        TaskManager taskManager,
        Deque<Command> commandLog,
        Random random
    ) {
        task = taskManager.deleteTask(taskIndex);
        taskManager.save();
        return new Response("Ripped off the ledger:", task);
    }

    @Override
    public void reverse(TaskManager taskManager) {
        taskManager.addTask(taskIndex, task);
        taskManager.save();
    }
}
