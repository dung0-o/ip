package dook.command;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;

public class ExitCommand extends Command {
    public ExitCommand(TaskManager taskManager, Random random) {
        super(taskManager, random, Pattern.compile("^bye$"));
    }

    @Override
    public Response execute(Matcher matcher) {
        System.out.println("\nGoodbye for now.");
        System.exit(0);
        return null;
    }
}
