package dook.command;

import java.util.List;
import java.util.Deque;
import java.util.Random;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dook.TaskManager;
import dook.task.Task;
import dook.parser.DateTimeParser;

public class DateCommand extends Command {
    private static final Pattern PATTERN = Pattern.compile("^date\\s+(\\S+(?:\\s+\\S+)*)$");
    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("EEE, d MMM yyyy");

    private LocalDate date;

    public DateCommand(String userQuery, LocalDate date) {
        super(userQuery);
        this.date = date;
    }

    public static Optional<Command> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }

        Optional<LocalDateTime> maybeDateTime = DateTimeParser.parseStartTime(matcher.group(1));
        if (maybeDateTime.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new DateCommand(userQuery, maybeDateTime.get().toLocalDate()));
    }

    public Response execute(
        TaskManager taskManager,
        Deque<Command> commandLog,
        Random random
    ) {
        List<Task> tasksOnDate = taskManager.getTasksByDate(date);
        StringBuilder sb = new StringBuilder(date.format(DATE_FORMATTER));
        for (Task task : tasksOnDate) {
            sb.append("\n  " + task.toStringWithTime());
        }

        if (tasksOnDate.isEmpty()) {
            sb.append("\n  No tasks found. Take a day off.");
        }

        return new Response(sb.toString());
    }
}
