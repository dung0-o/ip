package dook.command;

import java.util.Deque;
import java.util.Random;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;

/**
 * Represents the command for viewing the current task list in pretty format.
 */
public class ListCommand extends Command {
    private static final Pattern PATTERN = Pattern.compile("^list$");

    /**
     * Constructs a new ListCommand with specified user input.
     *
     * @param  userQuery Trimmed user input.
     */
    public ListCommand(String userQuery) {
        super(userQuery);
    }

    /**
     * Attempts parsing user input into a new ListCommand.
     *
     * @param  userQuery Trimmed user input.
     * @return           The new ListCommand (optional).
     */
    public static Optional<Command> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(new ListCommand(userQuery));
    }

    /**
     * Returns the pretty-print format of the task list as a response.
     *
     * @param  taskManager {@inheritDoc}
     * @param  commandLog  {@inheritDoc}
     * @param  random      {@inheritDoc}
     * @return             {@inheritDoc}
     */
    @Override
    public Response execute(
        TaskManager taskManager,
        Deque<Command> commandLog,
        Random random
    ) {
        return new Response(taskManager.listTasks());
    }
}
