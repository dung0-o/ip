package dook.command;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;

public class ErrorCommand extends Command {
    public ErrorCommand(TaskManager taskManager, Random random) {
        super(taskManager, random, Pattern.compile("^error$"));
    }

    @Override
    public Response execute(Matcher matcher) {
        throw new RuntimeException();
    }
}
