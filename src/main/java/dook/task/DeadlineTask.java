package dook.task;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DeadlineTask extends Task {
    private static final Pattern PATTERN =
            Pattern.compile("^deadline\\s+{{phrase}}\\s+/by\\s+{{phrase}}$"
                                    .replace("{{phrase}}", "(\\S+(?:\\s+\\S+)*)"));
    private String deadline;

    public DeadlineTask(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    public static Optional<Task> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(new DeadlineTask(matcher.group(1), matcher.group(2)));
    }

    @Override
    public String toString() {
        return "[D]%s (by: %s)".formatted(super.toString(), deadline);
    }
}
