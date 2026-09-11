package dook.command;

import java.util.List;
import java.util.Deque;
import java.util.Random;
import java.util.Optional;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;

public class HelpCommand extends Command {
    private record Pair(String command, String description) {}

    private static final Pattern PATTERN = Pattern.compile("^help$");
    private static final List<Pair> COMMAND_DESCRIPTIONS = new ArrayList<>(List.of(
        new Pair("bye",             "Exit this nightmare."),
        new Pair("list",            "View your overwhelmingly long task list."),
        new Pair("mark [NUMBER]",   "Lie to yourself that the task is done."),
        new Pair("unmark [NUMBER]", "Shamefully mark that task as unfinished."),
        new Pair("delete [NUMBER]", "Run away from the task, permanently."),
        new Pair("todo [PHRASE]",   "Add a to-do task on to your already long list."),

        new Pair("deadline [PHRASE] /by [PHRASE]",
                    "Add a deadline task, then procrastinate."),

        new Pair("event [PHRASE] /from [PHRASE] /to [PHRASE]",
                    "Add an event task that you will surely bail out last minute."),

        new Pair("error",           "Summon a nasty bug swarm to test the bug catcher."),
        new Pair("delete all",      "Relieve yourself from all burdens.")
    ));
    private static String helpMessage;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("You hurriedly scan the book as the light grows dim:");

        for (Pair pair : COMMAND_DESCRIPTIONS) {
            sb.append("\n    " + pair.command())
              .append("\n      " + pair.description())
              .append("\n");
        }

        sb.setLength(sb.length() - 1);
        helpMessage = sb.toString();
    }

    public HelpCommand(String userQuery) {
        super(userQuery);
    }

    public static Optional<Command> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(new HelpCommand(userQuery));
    }

    @Override
    public Response execute(
        TaskManager taskManager,
        Deque<Command> commandLog,
        Random random
    ) {
        return new Response(helpMessage);
    }
}
