package dook.command;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;

public class EmptyCommand extends Command {
    public EmptyCommand(TaskManager taskManager, Random random) {
        super(taskManager, random, Pattern.compile("^$"));
    }

    @Override
    public Response execute(Matcher matcher) {
        return new Response("Silence won't keep you safe anymore.");
    }
}
