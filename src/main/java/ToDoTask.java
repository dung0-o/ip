import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ToDoTask extends Task {
    private static final Pattern PATTERN = Pattern.compile("^todo\\s([\\w\\d]+(\\s+[\\w\\d]+)*)$");

    public ToDoTask(String description) {
        super(description);
    }

    public ToDoTask() {
        this(null);
    }

    @Override
    public String toString() {
        return "[T]%s".formatted(super.toString());
    }

    @Override
    public Task getNewTask(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (matcher.matches()) {
            return new ToDoTask(matcher.group(1));
        } else {
            return null;
        }
    }
}
