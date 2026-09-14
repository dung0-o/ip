package dook.exception;

/**
 * Represents the template for custom exceptions.
 */
public abstract class DookException extends RuntimeException {
    public DookException(String message) {
        super(message);
    }
}
