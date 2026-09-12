package dook.command;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public class UnmarkCommand extends Command {
    public UnmarkCommand(TaskManager taskManager, Random random) {
        super(taskManager, random, Pattern.compile("^unmark\\s+(\\d+)$"));
    }

    @Override
    public Response execute(Matcher matcher) {
        int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
        Task task = taskManager.getTask(taskIndex);

        if (!task.isDone()) {
            return new Response("It waits for you, still undone. No change made:", task);
        }

        task.setDone(false);
        taskManager.save();
        return new Response("It festers in the dark. Marked as unfinished:", task);
    }
}
