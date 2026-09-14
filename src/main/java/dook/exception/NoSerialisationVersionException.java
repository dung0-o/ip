package dook.exception;

/**
 * Represents the exception when local serialisation version is missing.
 */
public class NoSerialisationVersionException extends DookException {
    public NoSerialisationVersionException() {
        super("Metadata file missing.");
    }
}
