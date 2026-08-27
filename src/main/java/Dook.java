import java.util.Scanner;

public class Dook {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String banner = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String chat_bar = "____________________________________________________________\n";

        System.out.println(banner);
        System.out.print(chat_bar + "Hello! I'm Dook.\nWhat can I do for you?\n\n> ");
        in.nextLine();
        System.out.println(chat_bar + "Bye. Hope to see you again soon!");
    }
}
