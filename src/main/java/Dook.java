import java.util.Random;
import java.util.Scanner;

public class Dook {
    public static final String BANNER = """

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
    public static final String BAR = "\n" + "_".repeat(48) + "\n";
    public static final String[] GREETINGS = {
        "The shadows just got a little colder.",
        "You should not have opened this chat.",
        "Are you alone in the room right now?"
    };

    public static void printDookMsg(String msg) {
        System.out.print("\n" + msg + BAR + "\n> ");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Random random = new Random();

        System.out.print(BANNER);
        printDookMsg("I am Dook.\n" + GREETINGS[random.nextInt(GREETINGS.length)]);

        String userQuery = in.nextLine();
        while (!userQuery.equals("bye")) {
            printDookMsg(userQuery);
            userQuery = in.nextLine();
        }
        System.out.println("\nGoodbye for now.");
    }
}
