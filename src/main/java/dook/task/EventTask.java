package dook.task;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.io.SerialisedData;

/**
 * Represents a task with time frame.
 */
public class EventTask extends Task {
    private static final Pattern PATTERN =
            Pattern.compile("^event\\s+{{phrase}}\\s+/from\\s+{{phrase}}\\s+/to\\s+{{phrase}}$"
                                    .replace("{{phrase}}", "(\\S+(?:\\s+\\S+)*)"));
    private String startDatetime;
    private String endDatetime;

    /**
     * Constructs a new EventTask with specified description,
     * start time and end time.
     *
     * @param  description   The description of the task.
     * @param  startDatetime The start time as string.
     * @param  endDatetime   The end time as string.
     */
    public EventTask(String description, String startDatetime, String endDatetime) {
        super(description);
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
    }

    /**
     * Attempts parsing the user input into an EventTask.
     * Looks for three phrases: first to be the description,
     * second to be the start time as string,
     * third to be the end time as string.
     *
     * @param  userQuery Trimmed user input.
     * @return           The new EventTask (optional).
     */
    public static Optional<Task> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(new EventTask(matcher.group(1), matcher.group(2), matcher.group(3)));
    }

    @Override
    public SerialisedData serialise() {
        SerialisedData data = super.serialise();
        data.add(startDatetime, endDatetime);
        return data;
    }

    @Override
    public String toString() {
        return "[E]%s (from: %s to: %s)".formatted(super.toString(), startDatetime, endDatetime);
    }
}
