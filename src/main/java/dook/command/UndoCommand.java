package dook.command;

import java.util.List;
import java.util.Deque;
import java.util.Random;
import java.util.Optional;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;
import dook.task.Task;
import dook.exception.EmptyCommandLogException;

public class UndoCommand extends Command {
    private static final Pattern PATTERN = Pattern.compile("^undo$");

    public UndoCommand(String userQuery) {
        super(userQuery);
    }

    public static Optional<Command> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(new UndoCommand(userQuery));
    }

    @Override
    public Response execute(
        TaskManager taskManager,
        Deque<Command> commandLog,
        Random random
    ) {
        commandLog.pop();
        if (commandLog.size() == 0) {
            throw EmptyCommandLogException();
        }

        Command lastCommand = commandLog.pop();
        lastCommand.reverse(taskManager);
        return new Response("History was rewritten. Past mistake was amended:\n  "
                            + lastCommand.getUserQuery());
    }
}
