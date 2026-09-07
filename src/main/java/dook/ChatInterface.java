package dook;

import dook.command.Response;

public class ChatInterface {
    private final int BAR_LENGTH = 72;
    private final String DIVIDER = "_".repeat(BAR_LENGTH) + "\n\n> ";

    public void printResponse(Response response) {
        if (response.task() == null) {
            System.out.print("\n" + response.message() + "\n" + DIVIDER);
        } else {
            System.out.print("\n" + response.message() + "\n  " + response.task() + "\n" + DIVIDER);
        }
    }

    public void printError(String msg, Exception e) {
        System.out.print("\n" + msg + "\n  ");
        e.printStackTrace();
        System.out.print(DIVIDER);
    }
}
