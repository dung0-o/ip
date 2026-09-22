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
