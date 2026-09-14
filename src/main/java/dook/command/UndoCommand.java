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

/**
 * Represents the command for undoing the previous commands.
 */
public class UndoCommand extends Command {
    private static final Pattern PATTERN = Pattern.compile("^undo$");

    /**
     * Constructs a new UndoCommand with the specified user input.
     *
     * @param  userQuery Trimmed user input.
     */
    public UndoCommand(String userQuery) {
        super(userQuery);
    }

    /**
     * Attempts parsing user input into a new UndoCommand.
     *
     * @param  userQuery Trimmed user input.
     * @return           The new UndoCommand (optional).
     */
    public static Optional<Command> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(new UndoCommand(userQuery));
    }

    /**
     * Removes and undoes the execution of the last command in the command log.
     *
     * @param  taskManager {@inheritDoc}
     * @param  commandLog  {@inheritDoc}
     * @param  random      {@inheritDoc}
     * @return             {@inheritDoc}
     * @throws EmptyCommandLogException If the command log is empty.
     */
    @Override
    public Response execute(
        TaskManager taskManager,
        Deque<Command> commandLog,
        Random random
    ) {
        commandLog.pop();
        if (commandLog.size() == 0) {
            throw new EmptyCommandLogException();
        }

        Command lastCommand = commandLog.pop();
        lastCommand.reverse(taskManager);
        return new Response("History was rewritten. Past mistake was amended:\n  "
                            + lastCommand.getUserQuery());
    }
}
