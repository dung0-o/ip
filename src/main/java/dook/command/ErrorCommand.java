package dook.command;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;

public class ErrorCommand extends Command {
    public ErrorCommand(TaskManager taskManager) {
        super(taskManager, Pattern.compile("^error$"));
    }

    @Override
    public Response execute(Matcher matcher) {
        throw new RuntimeException();
    }
}
