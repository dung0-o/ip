package dook.command;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;

public class GreetCommand extends Command {
    private final String BANNER =
        """
                        ▓█████▄  ▒█████   ▒█████   ██ ▄█▀
                        ▒██▀ ██▌▒██▒  ██▒▒██▒  ██▒ ██▄█▒
                        ░██   █▌▒██░  ██▒▒██░  ██▒▓███▄░
                        ░▓█▄   ▌▒██   ██░▒██   ██░▓██ █▄
                        ░▒████▓ ░ ████▓▒░░ ████▓▒░▒██▒ █▄
                         ▒▒▓  ▒ ░ ▒░▒░▒░ ░ ▒░▒░▒░ ▒ ▒▒ ▓▒
                         ░ ▒  ▒   ░ ▒ ▒░   ░ ▒ ▒░ ░ ░▒ ▒░
                         ░ ░  ░ ░ ░ ░ ▒  ░ ░ ░ ▒  ░ ░░ ░
                           ░        ░ ░      ░ ░  ░  ░
                         ░
        """;
    private final String[] GREETINGS = {
        "The shadows just got a little colder.",
        "You should not have opened this chat.",
        "Are you alone in the room right now?"
    };

    public GreetCommand(TaskManager taskManager, Random random) {
        super(taskManager, random, Pattern.compile("^\\s+$"));
    }

    @Override
    public Response execute(Matcher matcher) {
        String greeting = GREETINGS[random.nextInt(GREETINGS.length)];
        return new Response(BANNER + "\nI am Dook.\n" + greeting);
    }
}
