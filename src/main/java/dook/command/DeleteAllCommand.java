package dook.command;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public class DeleteAllCommand extends Command {
    public DeleteAllCommand(TaskManager taskManager, Random random) {
        super(taskManager, random, Pattern.compile("^delete all$"));
    }

    @Override
    public Response execute(Matcher matcher) {
        taskManager.deleteAllTasks();
        return new Response("You run off into a new void and leave everything behind.");
    }
}
