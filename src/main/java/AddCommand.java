import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class AddCommand extends Command {
    public AddCommand(TaskManager taskManager) {
        super(taskManager, Pattern.compile(".+"));
    }

    @Override
    public void execute(Matcher matcher) {
        String userQuery = matcher.group();
        taskManager.addTask(userQuery);
        printReply("Scratched into the ledger: " + userQuery);
    }
}
