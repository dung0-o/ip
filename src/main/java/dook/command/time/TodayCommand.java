package dook.command.time;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dook.command.Command;
import dook.command.Response;
import dook.service.TaskManager;
import dook.task.Task;

public class TodayCommand extends DateCommand {
    private static final Pattern PATTERN = Pattern.compile("^today$");

    public TodayCommand(String userQuery) {
        super(userQuery, LocalDate.now());
    }

    public static Optional<Command> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(new TodayCommand(userQuery));
    }
}
