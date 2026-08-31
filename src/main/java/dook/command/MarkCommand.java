package dook.command;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public class MarkCommand extends Command {
    public MarkCommand(TaskManager taskManager) {
        super(taskManager, Pattern.compile("^mark\\s+(\\d+)$"));
    }

    @Override
    public void execute(Matcher matcher) {
        int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
        Task task = taskManager.getTask(taskIndex);

        if (task == null) {
            printReply("You reach into the void. That task does not exist.");
        } else if (task.isDone()) {
            printReply("The grave is already sealed. This task is finished:", task);
        } else {
            task.setDone(true);
            printReply("A debt is paid. Marked as done:", task);
        }
    }
}
