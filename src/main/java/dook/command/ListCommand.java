package dook.command;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;

public class ListCommand extends Command {
    public ListCommand(TaskManager taskManager) {
        super(taskManager, Pattern.compile("^list$"));
    }

    @Override
    public Response execute(Matcher matcher) {
        return new Response(taskManager.listTasks());
    }
}
