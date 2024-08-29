
/**
 * This exception is used when a location (original or current) is being created or modified for an item,
 *      and it's invalid (i.e. for original, not a shelf location and for current, not a shelf or cart location or "out").
 *      This method is enough to represent both types of locations pretty easily.
 */
public class InvalidLocationException extends Exception {
    public InvalidLocationException() {

    }
}
