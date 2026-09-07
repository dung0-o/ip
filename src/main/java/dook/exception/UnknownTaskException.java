package dook.exception;

public class UnknownTaskException extends DookException {
    public UnknownTaskException() {
        super(  """
                You broke this world physics and reached a forbidden space.
                Well done, you forced my hand. I will be coming for you personally.""");
    }
}
