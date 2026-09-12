package dook.exception;

public class NoSerialisationVersionException extends DookException {
    public NoSerialisationVersionException() {
        super("Metadata file missing.");
    }
}
