package dook.command;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public class MarkCommand extends Command {
    public MarkCommand(TaskManager taskManager, Random random) {
        super(taskManager, random, Pattern.compile("^mark\\s+(\\d+)$"));
    }

    @Override
    public Response execute(Matcher matcher) {
        int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
        Task task = taskManager.getTask(taskIndex);

        if (task.isDone()) {
            return new Response("The grave is already sealed. This task is finished:", task);
        }

        task.setDone(true);
        return new Response("A debt is paid. Marked as done:", task);
    }
}
