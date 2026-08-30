package dook;

import java.util.Scanner;

public class Dook {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        CommandManager commandManager = new CommandManager(taskManager);
        Scanner in = new Scanner(System.in);
        String userQuery = " ";

        while (true) {
            commandManager.processQuery(userQuery);
            userQuery = in.nextLine().trim();
        }
    }
}
