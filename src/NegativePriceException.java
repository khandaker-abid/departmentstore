
/**
 * This exception is used when a price is being created or modified for an item, and it's negative, because a negative price is impossible.
 */
public class NegativePriceException extends Exception{
    public NegativePriceException() {

    }
}
