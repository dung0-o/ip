package dook.command;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public class AddCommand extends Command {
    public AddCommand(TaskManager taskManager) {
        super(taskManager, Pattern.compile(".+"));
    }

    @Override
    public void execute(Matcher matcher) {
        Task newTask = taskManager.addTask(matcher.group());
        if (newTask != null) {
            printReply("Scratched into the ledger:", newTask);
        } else {
            printReply("You shout gibberish at the void. It stares back.");
        }
    }
}
