package dook.task;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;

import dook.io.SerialisedData;

/**
 * Represents the template for all tasks.
 */
public abstract class Task {
    private String description;
    private boolean isDone;

    /**
     * Represents the template for constructors for all tasks
     * with the specified description.
     * Marks the new task as not done.
     *
     * @param  description The description of the task.
     */
    protected Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Retrieves the status of the task (done or not).
     *
     * @return The current status of the task.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Modify the status of the task (done or not).
     *
     * @param isDone The desired status of the task.
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Return the serialised data of the task.
     *
     * @return The task in list of strings format.
     */
    public SerialisedData serialise() {
        return new SerialisedData(
            getClass(),
            String.valueOf(isDone),
            description
        );
    }

    /**
     * Returns pretty-print of the task,
     * includes task type, task status (done or not), and task description.
     *
     * @return The task in string format.
     */
    @Override
    public String toString() {
        return "[%s] %s".formatted(isDone ? "X" : " ", description);
    }
}
