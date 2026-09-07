package dook.exception;

public class UnknownCommandException extends DookException {
    public UnknownCommandException() {
        super(  """
                You broke this world physics and reached a forbidden space.
                Well done, you forced my hand. I will be coming for you personally.""");
    }
}
