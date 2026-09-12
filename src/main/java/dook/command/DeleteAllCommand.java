package dook.command;

import java.util.List;
import java.util.Deque;
import java.util.Random;
import java.util.Optional;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public class DeleteAllCommand extends Command {
    private static final Pattern PATTERN = Pattern.compile("^delete all$");
    private Collection<Task> tasks;

    public DeleteAllCommand(String userQuery) {
        super(userQuery);
    }

    public static Optional<Command> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(new DeleteAllCommand(userQuery));
    }

    @Override
    public Response execute(
        TaskManager taskManager,
        Deque<Command> commandLog,
        Random random
    ) {
        tasks = taskManager.getAllTasks();
        taskManager.deleteAllTasks();
        taskManager.save();
        return new Response("You run off into a new void and leave everything behind.");
    }

    @Override
    public void reverse(TaskManager taskManager) {
        taskManager.addTasks(tasks);
        taskManager.save();
    }
}
