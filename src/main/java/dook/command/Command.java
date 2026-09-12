package dook.command;

import java.util.Deque;
import java.util.Random;

import dook.TaskManager;

public abstract class Command {
    protected String userQuery;

    public Command(String userQuery) {
        this.userQuery = userQuery;
    }

    public String getUserQuery() {
        return userQuery;
    }

    public void reverse(TaskManager taskManager) {}

    public abstract Response execute(
        TaskManager taskManager,
        Deque<Command> commandLog,
        Random random
    );
}
