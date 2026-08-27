import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static final String BAR = "\n" + "_".repeat(60) + "\n";
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
        ArrayList<Task> tasks = new ArrayList<>();

        Pattern markCommandPattern = Pattern.compile("^mark\\s(\\d+)$");
        Pattern unmarkCommandPattern = Pattern.compile("^unmark\\s(\\d+)$");
        Matcher matcher;

        System.out.print(BANNER);
        printDookMsg("I am Dook.\n" + GREETINGS[random.nextInt(GREETINGS.length)]);

        String userQuery = in.nextLine().trim();
        while (!userQuery.equals("bye")) {
            if (userQuery.equals("list")) {
                String msg = "1." + tasks.get(0);
                for (int i = 1; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    msg += "\n" + (i+1) + "." + task;
                }
                printDookMsg(msg);

            } else if ((matcher = markCommandPattern.matcher(userQuery)).matches()) {
                int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
                if (taskIndex >= 0 && taskIndex < tasks.size()) {
                    Task task = tasks.get(taskIndex);
                    if (!task.isDone()) {
                        task.setDone(true);
                        printDookMsg("A debt is paid. Marked as done:\n  " + task);
                    } else {
                        printDookMsg("The grave is already sealed. This task is finished:\n  " + task);
                    }
                } else {
                    printDookMsg("You reach into the void. That task does not exist.");
                }


            } else if ((matcher = unmarkCommandPattern.matcher(userQuery)).matches()) {
                int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
                if (taskIndex >= 0 && taskIndex < tasks.size()) {
                    Task task = tasks.get(taskIndex);
                    if (task.isDone()) {
                        task.setDone(false);
                        printDookMsg("It festers in the dark. Marked as unfinished:\n  " + task);
                    } else {
                        printDookMsg("It waits for you, still undone. No change made:\n  " + task);
                    }
                } else {
                    printDookMsg("You reach into the void. That task does not exist.");
                }

            } else {
                tasks.add(new Task(userQuery));
                printDookMsg("Scratched into the ledger: " + userQuery);
            }
            userQuery = in.nextLine();
        }
        System.out.println("\nGoodbye for now.");
    }
}
