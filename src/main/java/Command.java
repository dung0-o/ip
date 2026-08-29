import java.util.regex.Pattern;
import java.util.regex.Matcher;

public abstract class Command {
    private final String BAR = "\n" + "_".repeat(60) + "\n";
    protected TaskManager taskManager;
    protected Pattern pattern;

    public Command(TaskManager taskManager, Pattern pattern) {
        this.taskManager = taskManager;
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    protected void printReply(String msg) {
        System.out.print("\n" + msg + BAR + "\n> ");
    }

    protected void printReply(String msg, Task task) {
        System.out.print("\n" + msg + "\n  " + task + BAR + "\n> ");
    }

    public abstract void execute(Matcher matcher);
}
