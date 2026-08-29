import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EmptyCommand extends Command {
    public EmptyCommand(TaskManager taskManager) {
        super(taskManager, Pattern.compile("^$"));
    }

    @Override
    public void execute(Matcher matcher) {
        printReply("Silence won't keep you safe anymore.");
    }
}
