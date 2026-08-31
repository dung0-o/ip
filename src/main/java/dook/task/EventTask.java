package dook.task;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EventTask extends Task {
    private static final Pattern PATTERN =
            Pattern.compile("^event\\s+{{phrase}}\\s+/from\\s+{{phrase}}\\s+/to\\s+{{phrase}}$"
                                    .replace("{{phrase}}", "(\\S+(\\s+\\S+)*)"));
    private String startDatetime;
    private String endDatetime;

    public EventTask(String description, String startDatetime, String endDatetime) {
        super(description);
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
    }

    public EventTask() {
        this(null, null, null);
    }

    @Override
    public String toString() {
        return "[E]%s (from: %s to: %s)".formatted(super.toString(), startDatetime, endDatetime);
    }

    @Override
    public Task getNewTask(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (matcher.matches()) {
            return new EventTask(matcher.group(1), matcher.group(3), matcher.group(5));
        } else {
            return null;
        }
    }
}
