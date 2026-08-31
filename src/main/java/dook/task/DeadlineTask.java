package dook.task;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DeadlineTask extends Task {
    private static final Pattern PATTERN =
            Pattern.compile("^deadline\\s+{{phrase}}\\s+/by\\s+{{phrase}}$"
                                    .replace("{{phrase}}", "(\\S+(\\s+\\S+)*)"));
    private String deadline;

    public DeadlineTask(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    public DeadlineTask() {
        this(null, null);
    }

    @Override
    public String toString() {
        return "[D]%s (by: %s)".formatted(super.toString(), deadline);
    }

    @Override
    public Task getNewTask(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (matcher.matches()) {
            return new DeadlineTask(matcher.group(1), matcher.group(3));
        } else {
            return null;
        }
    }
}
