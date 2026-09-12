package dook;

import java.util.Random;
import java.util.Scanner;

import dook.command.Response;
import dook.exception.DookException;

public class Dook {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final ChatInterface UI = new ChatInterface();

    private Random random;
    private FileManager io;
    private TaskManager taskManager;
    private CommandManager commandManager;

    public Dook(Random random, FileManager io) {
        this.random = random;
        this.io = io;

        taskManager = new TaskManager(io);
        commandManager = new CommandManager(taskManager, random);
    }

    public void run() {
        String userQuery = " ";
        Response response;

        while (true) {
            try {
                response = commandManager.processQuery(userQuery);
                UI.printResponse(response);

            } catch (DookException e) {
                response = new Response(e.getMessage());
                UI.printResponse(response);

            } catch (Exception e) {
                UI.printError("A swarm of bugs circles around you. They screech:", e);
            }

            userQuery = SCANNER.nextLine().trim();
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        if (args.length > 0 && args[0].matches("-?\\d+")) {
            long seed = Long.parseLong(args[0]);
            random.setSeed(seed);
        }

        String dataDirName = args.length > 1 ? args[1] : "data";
        FileManager io = new FileManager(dataDirName);

        Dook dook = new Dook(random, io);
        dook.run();
    }
}
