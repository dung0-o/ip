package dook.command;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;

public class ListCommand extends Command {
    public ListCommand(TaskManager taskManager, Random random) {
        super(taskManager, random, Pattern.compile("^list$"));
    }

    @Override
    public Response execute(Matcher matcher) {
        return new Response(taskManager.listTasks());
    }
}
