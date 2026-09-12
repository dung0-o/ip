package dook.exception;

public class TaskListIndexOutOfBoundsException extends DookException {
    public TaskListIndexOutOfBoundsException() {
        super("You reach into the void. That task does not exist.");
    }
}
