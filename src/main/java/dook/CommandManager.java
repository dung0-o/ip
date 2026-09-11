package dook;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
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
import dook.command.UnmarkCommand;

import dook.exception.UnknownCommandException;

public class CommandManager {
    private List<Command> commands;
    private TaskManager taskManager;

    public CommandManager(TaskManager taskManager, Random random) {
        this.taskManager = taskManager;
        commands = new ArrayList<Command>();
        commands.add(new GreetCommand(taskManager, random));
        commands.add(new ExitCommand(taskManager, random));
        commands.add(new EmptyCommand(taskManager, random));
        commands.add(new ListCommand(taskManager, random));
        commands.add(new MarkCommand(taskManager, random));
        commands.add(new UnmarkCommand(taskManager, random));
        commands.add(new DeleteCommand(taskManager, random));
        commands.add(new DeleteAllCommand(taskManager, random));
        commands.add(new ErrorCommand(taskManager, random));
        commands.add(new HelpCommand(taskManager, random));
        commands.add(new AddTaskCommand(taskManager, random));
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
