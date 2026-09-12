package dook.command;

import java.util.Deque;
import java.util.Random;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public class UnmarkCommand extends Command {
    private static final Pattern PATTERN = Pattern.compile("^unmark\\s+(\\d+)$");
    private int taskIndex;
    private Task task;
    private boolean wasDone;

    public UnmarkCommand(String userQuery, int taskIndex) {
        super(userQuery);
        this.taskIndex = taskIndex;
    }

    public static Optional<Command> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
        return Optional.of(new UnmarkCommand(userQuery, taskIndex));
    }

    @Override
    public Response execute(
        TaskManager taskManager,
        Deque<Command> commandLog,
        Random random
    ) {
        task = taskManager.getTask(taskIndex);
        wasDone = task.isDone();

        if (!wasDone) {
            return new Response("It waits for you, still undone. No change made:", task);
        }

        task.setDone(false);
        taskManager.save();
        return new Response("It festers in the dark. Marked as unfinished:", task);
    }

    @Override
    public void reverse(TaskManager taskManager) {
        task.setDone(wasDone);
        taskManager.save();
    }
}
