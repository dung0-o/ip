package dook;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;

import dook.command.Command;
import dook.command.Response;
import dook.command.AddTaskCommand;
import dook.command.EmptyCommand;
import dook.command.ErrorCommand;
import dook.command.ExitCommand;
import dook.command.GreetCommand;
import dook.command.HelpCommand;
import dook.command.ListCommand;
import dook.command.MarkCommand;
import dook.command.UnmarkCommand;

import dook.exception.UnknownCommandException;

public class CommandManager {
    private List<Command> commands;
    private TaskManager taskManager;

    public CommandManager(TaskManager taskManager) {
        this.taskManager = taskManager;
        commands = new ArrayList<Command>();
        commands.add(new GreetCommand(taskManager));
        commands.add(new ExitCommand(taskManager));
        commands.add(new EmptyCommand(taskManager));
        commands.add(new ListCommand(taskManager));
        commands.add(new MarkCommand(taskManager));
        commands.add(new UnmarkCommand(taskManager));
        commands.add(new ErrorCommand(taskManager));
        commands.add(new HelpCommand(taskManager));
        commands.add(new AddTaskCommand(taskManager));
    }

    public Response processQuery(String userQuery) {
        for (Command command : commands) {
            Matcher matcher = command.getPattern().matcher(userQuery);
            if (matcher.matches()) {
                return command.execute(matcher);
            }
        }
        throw new UnknownCommandException();
    }
}
