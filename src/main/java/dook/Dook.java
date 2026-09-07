package dook;

import java.util.Random;
import java.util.Scanner;

import dook.command.Response;
import dook.exception.DookException;

public class Dook {
    public static void main(String[] args) {
        Random random = new Random();
        try {
            long seed = Long.parseLong(args[0]);
            random.setSeed(seed);
        } catch (Exception e) {}

        TaskManager taskManager = new TaskManager();
        CommandManager commandManager = new CommandManager(taskManager, random);
        ChatInterface ui = new ChatInterface();
        Scanner in = new Scanner(System.in);
        String userQuery = " ";
        Response response;

        while (true) {
            try {
                response = commandManager.processQuery(userQuery);
                ui.printResponse(response);

            } catch (DookException e) {
                response = new Response(e.getMessage());
                ui.printResponse(response);

            } catch (Exception e) {
                ui.printError("A swarm of bugs circles around you. They screech:", e);
            }

            userQuery = in.nextLine().trim();
        }
    }
}
