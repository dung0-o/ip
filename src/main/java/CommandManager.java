import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class CommandManager {
    private List<Command> commands;
    private TaskManager taskManager;

    public CommandManager(TaskManager taskManager) {
        this.taskManager = taskManager;
        commands = new ArrayList<Command>();
        commands.add(new GreetingCommand(taskManager));
        commands.add(new ExitCommand(taskManager));
        commands.add(new EmptyCommand(taskManager));
        commands.add(new ListCommand(taskManager));
        commands.add(new MarkCommand(taskManager));
        commands.add(new UnmarkCommand(taskManager));
        commands.add(new AddCommand(taskManager));
    }

    public void processQuery(String userQuery) {
        for (Command command : commands) {
            Matcher matcher = command.getPattern().matcher(userQuery);
            if (matcher.matches()) {
                command.execute(matcher);
                return;
            }
        }
    }
}