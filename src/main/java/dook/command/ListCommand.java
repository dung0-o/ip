package dook.command;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;

public class ListCommand extends Command {
    public ListCommand(TaskManager taskManager) {
        super(taskManager, Pattern.compile("^list$"));
    }

    @Override
    public void execute(Matcher matcher) {
        if (!taskManager.isEmpty()) {
            printReply(taskManager.listTasks());
        } else {
            printReply("You are alone in this darkness.");
        }
    }
}
