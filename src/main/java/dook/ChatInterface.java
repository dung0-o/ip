package dook;

import java.util.Scanner;

import dook.command.Response;

public class ChatInterface {
    private final int BAR_LENGTH = 72;
    private final String DIVIDER = "_".repeat(BAR_LENGTH) + "\n\n> ";
    private final Scanner SCANNER = new Scanner(System.in);

    public void printResponse(Response response) {
        if (response.hasTask()) {
            System.out.print("\n" + response.message() + "\n  " + response.task() + "\n" + DIVIDER);
        } else {
            System.out.print("\n" + response.message() + "\n" + DIVIDER);
        }
    }

    public void printError(String msg, Exception e) {
        System.out.print("\n" + msg + "\n  ");
        e.printStackTrace();
        System.out.print(DIVIDER);
    }

    public String getUserQuery() {
        return SCANNER.nextLine().trim();
    }
}
