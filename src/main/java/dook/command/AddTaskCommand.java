package dook.command;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;

public class AddTaskCommand extends Command {
    public AddTaskCommand(TaskManager taskManager, Random random) {
        super(taskManager, random, Pattern.compile(".+"));
    }

    @Override
    public Response execute(Matcher matcher) {
        Task newTask = taskManager.addTask(matcher.group());
        taskManager.save();
        return new Response("Scratched into the ledger:", newTask);
    }
}
