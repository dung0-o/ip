package dook.command.system;

import java.util.List;
import java.util.Deque;
import java.util.Random;
import java.util.Optional;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.command.Command;
import dook.command.Response;
import dook.service.TaskManager;
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
     * Undoes the previous command by removing it from the command log
     * and reversing its effects.
     *
     * @param  taskManager {@inheritDoc}
     * @param  commandLog  {@inheritDoc}
     * @param  random      {@inheritDoc}
     * @return             {@inheritDoc}
     * @throws EmptyCommandLogException If there is no previous command to undo.
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
