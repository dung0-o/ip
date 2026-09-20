package dook.command;

import java.util.List;
import java.util.Deque;
import java.util.Random;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dook.TaskManager;
import dook.task.Task;

public class TodayCommand extends Command {
    private static final Pattern PATTERN = Pattern.compile("^today$");
    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("EEE, d MMM yyyy");

    public TodayCommand(String userQuery) {
        super(userQuery);
    }

    public static Optional<Command> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(new TodayCommand(userQuery));
    }

    public Response execute(
        TaskManager taskManager,
        Deque<Command> commandLog,
        Random random
    ) {
        LocalDate today = LocalDate.now();
        List<Task> todayTasks = taskManager.getTasksByDate(today);
        StringBuilder sb = new StringBuilder(today.format(DATE_FORMATTER));
        for (Task task : todayTasks) {
            sb.append("\n  " + task.toStringWithTime());
        }
        return new Response(sb.toString());
    }
}
