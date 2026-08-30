package dook.command;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public class UnmarkCommand extends Command {
    public UnmarkCommand(TaskManager taskManager) {
        super(taskManager, Pattern.compile("^unmark\\s(\\d+)$"));
    }

    @Override
    public void execute(Matcher matcher) {
        int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
        Task task = taskManager.getTask(taskIndex);

        if (task == null) {
            printReply("You reach into the void. That task does not exist.");
        } else if (!task.isDone()) {
            printReply("It waits for you, still undone. No change made:", task);
        } else {
            task.setDone(false);
            printReply("It festers in the dark. Marked as unfinished:", task);
        }
    }
}
