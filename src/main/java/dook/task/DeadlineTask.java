package dook.task;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Represents a task with due time.
 */
public class DeadlineTask extends Task {
    private static final Pattern PATTERN =
            Pattern.compile("^deadline\\s+{{phrase}}\\s+/by\\s+{{phrase}}$"
                                    .replace("{{phrase}}", "(\\S+(?:\\s+\\S+)*)"));
    private String deadline;

    /**
     * Constructs a new Deadline with specified description and due time.
     *
     * @param  description   The description of the task.
     * @param  deadline      The due time as string.
     */
    public DeadlineTask(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    /**
     * Attempts parsing the user input into a DeadlineTask.
     * Looks for two phrases: first to be the description,
     * second to be the due time as string.
     *
     * @param  userQuery Trimmed user input.
     * @return           The new DeadlineTask (optional).
     */
    public static Optional<Task> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(new DeadlineTask(matcher.group(1), matcher.group(2)));
    }

    @Override
    public List<String> save() {
        List<String> result = super.save();
        result.addAll(List.of(
            deadline
        ));
        return result;
    }

    @Override
    public String toString() {
        return "[D]%s (by: %s)".formatted(super.toString(), deadline);
    }
}
