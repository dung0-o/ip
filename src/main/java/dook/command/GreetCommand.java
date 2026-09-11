package dook.command;

import java.util.Deque;
import java.util.Random;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;

public class GreetCommand extends Command {
    private static final Pattern PATTERN = Pattern.compile("^\\s+");
    private static final String BANNER =
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
    private static final String[] GREETINGS = {
        "The shadows just got a little colder.",
        "You should not have opened this chat.",
        "Are you alone in the room right now?"
    };

    public GreetCommand(String userQuery) {
        super(userQuery);
    }

    public static Optional<Command> parse(String userQuery) {
        Matcher matcher = PATTERN.matcher(userQuery);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(new GreetCommand(userQuery));
    }

    @Override
    public Response execute(
        TaskManager taskManager,
        Deque<Command> commandLog,
        Random random
    ) {
        String greeting = GREETINGS[random.nextInt(GREETINGS.length)];
        return new Response(BANNER + "\nI am Dook.\n" + greeting);
    }
}
