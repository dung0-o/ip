package dook.command;

import java.util.Deque;
import java.util.Random;
import java.util.Optional;

import dook.TaskManager;
import dook.task.Task;

public class AddTaskCommand extends Command {
    public AddTaskCommand(String userQuery) {
        super(userQuery);
    }

    public static Optional<Command> parse(String userQuery) {
        return Optional.of(new AddTaskCommand(userQuery));
    }

    @Override
    public Response execute(
        TaskManager taskManager,
        Deque<Command> commandLog,
        Random random
    ) {
        Task newTask = taskManager.addTask(userQuery);
        return new Response("Scratched into the ledger:", newTask);
    }

    @Override
    public void reverse(TaskManager taskManager) {
        taskManager.deleteLastTask();
    }
}
