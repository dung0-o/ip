package dook.command;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;

public class HelpCommand extends Command {
    private record Pair(String command, String description) {}

    private final List<Pair> COMMAND_DESCRIPTIONS = new ArrayList<>(List.of(
        new Pair("bye",             "Exit this nightmare."),
        new Pair("list",            "View your overwhelmingly long task list."),
        new Pair("mark [NUMBER]",   "Lie to yourself that the task is done."),
        new Pair("unmark [NUMBER]", "Shamefully mark that task as unfinished."),
        new Pair("todo [PHRASE]",   "Add a to-do task on to your already long list."),

        new Pair("deadline [PHRASE] /by [PHRASE]",
                    "Add a deadline task, then procrastinate."),

        new Pair("event [PHRASE] /from [PHRASE] /to [PHRASE]",
                    "Add an event task that you will surely bail out last minute."),

        new Pair("error",   "Summon a nasty bug swarm to test the bug catcher.")
    ));

    public HelpCommand(TaskManager taskManager) {
        super(taskManager, Pattern.compile("^help$"));
    }

    @Override
    public Response execute(Matcher matcher) {
        StringBuilder sb = new StringBuilder();
        sb.append("You hurriedly scan the book as the light grows dim:");

        for (Pair pair : COMMAND_DESCRIPTIONS) {
            sb.append("\n    " + pair.command())
              .append("\n      " + pair.description())
              .append("\n");
        }

        sb.setLength(sb.length() - 1);
        return new Response(sb.toString());
    }
}
