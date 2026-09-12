package dook.exception;

public abstract class DookException extends RuntimeException {
    public DookException(String message) {
        super(message);
    }
}
