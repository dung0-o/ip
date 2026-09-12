package dook.task;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ToDoTask extends Task {
    private static final Pattern PATTERN = Pattern.compile("^todo\\s+(\\S+(?:\\s+\\S+)*)$");

    public ToDoTask(String description) {
        super(description);
    }

    public static Optional<Task> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(new ToDoTask(matcher.group(1)));
    }

    @Override
    public String toString() {
        return "[T]%s".formatted(super.toString());
    }
}
