import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GreetingCommand extends Command {
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

    public GreetingCommand(TaskManager taskManager) {
        super(taskManager, Pattern.compile("^\\s+$"));
    }

    @Override
    public void execute(Matcher matcher) {
        printReply(BANNER + "\nI am Dook.\n" + GREETINGS[RANDOM.nextInt(GREETINGS.length)]);
    }
}
