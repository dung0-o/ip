package dook.task;

import java.util.regex.Matcher;

public abstract class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "[%s] %s".formatted(isDone ? "X" : " ", description);
    }

    public abstract Task getNewTask(String userQuery);
}
