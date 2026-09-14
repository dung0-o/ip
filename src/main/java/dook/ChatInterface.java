package dook;

import java.util.Scanner;

import dook.command.Response;

/**
 * Handles taking the user inputs and printing the responses.
 */
public class ChatInterface {
    private final int BAR_LENGTH = 72;
    private final String DIVIDER = "_".repeat(BAR_LENGTH) + "\n\n> ";
    private final Scanner SCANNER = new Scanner(System.in);

    /**
     * Formats and prints the response answering user input.
     *
     * @param response The response answering user input.
     */
    public void printResponse(Response response) {
        if (response.hasTask()) {
            System.out.print("\n" + response.message() + "\n  " + response.task() + "\n" + DIVIDER);
        } else {
            System.out.print("\n" + response.message() + "\n" + DIVIDER);
        }
    }

    /**
     * Formats and prints the exception messages.
     *
     * @param msg Main message.
     * @param e   The exception.
     */
    public void printError(String msg, Exception e) {
        System.out.print("\n" + msg + "\n  ");
        e.printStackTrace();
        System.out.print(DIVIDER);
    }

    /**
     * Cleans and retrieves the user input.
     *
     * @return Trimmed user input.
     */
    public String getUserQuery() {
        return SCANNER.nextLine().trim();
    }
}
