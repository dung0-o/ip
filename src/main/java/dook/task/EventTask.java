package dook.task;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EventTask extends Task {
    private static final Pattern PATTERN =
            Pattern.compile("^event\\s+{{phrase}}\\s+/from\\s+{{phrase}}\\s+/to\\s+{{phrase}}$"
                                    .replace("{{phrase}}", "(\\S+(?:\\s+\\S+)*)"));
    private String startDatetime;
    private String endDatetime;

    public EventTask(String description, String startDatetime, String endDatetime) {
        super(description);
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
    }

    public static Optional<Task> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(new EventTask(matcher.group(1), matcher.group(2), matcher.group(3)));
    }

    @Override
    public List<String> save() {
        List<String> result = super.save();
        result.addAll(List.of(
            startDatetime,
            endDatetime
        ));
        return result;
    }

    @Override
    public String toString() {
        return "[E]%s (from: %s to: %s)".formatted(super.toString(), startDatetime, endDatetime);
    }
}
