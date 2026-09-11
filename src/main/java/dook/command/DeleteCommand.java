package dook.command;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public class DeleteCommand extends Command {
    public DeleteCommand(TaskManager taskManager, Random random) {
        super(taskManager, random, Pattern.compile("^delete\\s+(\\d+)$"));
    }

    @Override
    public Response execute(Matcher matcher) {
        int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
        Task deletedTask = taskManager.deleteTask(taskIndex);
        return new Response("Ripped off the ledger:", deletedTask);
    }
}
