package dook.exception;

public class UnknownTaskException extends DookException {
    public UnknownTaskException() {
        super("Gibberish words only draw the monster nearer.\nTry screaming `help` instead.");
    }
}
