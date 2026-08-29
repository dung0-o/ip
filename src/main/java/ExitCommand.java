import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ExitCommand extends Command {
    public ExitCommand(TaskManager taskManager) {
        super(taskManager, Pattern.compile("^bye$"));
    }

    @Override
    public void execute(Matcher matcher) {
        System.out.println("\nGoodbye for now.");
        System.exit(0);
    }
}
