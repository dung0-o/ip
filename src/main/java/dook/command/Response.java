package dook.command;

import dook.task.Task;

public record Response(String message, Task task) {
    public Response(String message) {
        this(message, null);
    }
}
