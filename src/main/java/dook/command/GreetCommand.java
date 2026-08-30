package dook.command;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import dook.TaskManager;

public class GreetCommand extends Command {
    private final String BANNER =   """
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
    private final Random RANDOM = new Random();

    public GreetCommand(TaskManager taskManager) {
        super(taskManager, Pattern.compile("^\\s+$"));
    }

    @Override
    public void execute(Matcher matcher) {
        String greeting = GREETINGS[RANDOM.nextInt(GREETINGS.length)];
        printReply("%s\nI am Dook.\n%s".formatted(BANNER, greeting));
    }
}
