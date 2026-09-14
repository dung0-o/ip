package dook;

import java.util.List;
import java.util.Deque;
import java.util.Random;
import java.util.Optional;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.regex.Matcher;

import dook.command.Command;
import dook.command.Response;
import dook.command.AddTaskCommand;
import dook.command.DeleteCommand;
import dook.command.DeleteAllCommand;
import dook.command.EmptyCommand;
import dook.command.ErrorCommand;
import dook.command.ExitCommand;
import dook.command.GreetCommand;
import dook.command.HelpCommand;
import dook.command.ListCommand;
import dook.command.MarkCommand;
import dook.command.UndoCommand;
import dook.command.UnmarkCommand;

import dook.exception.UnknownCommandException;

/**
 * Manages the processing of user inputs.
 */
public class CommandManager {
    @FunctionalInterface
    private interface CommandParser {
        Optional<Command> parse(String input);
    }

    private final List<CommandParser> PARSERS = List.of(
        GreetCommand::parse,
        ExitCommand::parse,
        EmptyCommand::parse,
        ListCommand::parse,
        MarkCommand::parse,
        UnmarkCommand::parse,
        DeleteCommand::parse,
        DeleteAllCommand::parse,
        ErrorCommand::parse,
        HelpCommand::parse,
        UndoCommand::parse,
        AddTaskCommand::parse
    );

    private Deque<Command> commandLog = new ArrayDeque<>();
    private TaskManager taskManager;
    private Random random;

    /**
     * Constructs a new CommandManager with specified task manager and random generator.
     *
     * @param  taskManager The task manager, contains task list.
     * @param  random      The random number generator.
     */
    public CommandManager(TaskManager taskManager, Random random) {
        this.taskManager = taskManager;
        this.random = random;
    }

    /**
     * Parses the user input into a command.
     * Adds the command into the command log.
     * Executes the command and gives response.
     *
     * @param  userQuery Trimmed user input.
     * @return           The response asnwering user input.
     * @throws UnknownCommandException If parsing fails.
     */
    public Response processQuery(String userQuery) {
        for (CommandParser parser : PARSERS) {
            Optional<Command> maybeCommand = parser.parse(userQuery);
            if (maybeCommand.isPresent()) {
                Command command = maybeCommand.get();
                commandLog.push(command);
                return command.execute(taskManager, commandLog, random);
            }
        }

        throw new UnknownCommandException();
    }
}
