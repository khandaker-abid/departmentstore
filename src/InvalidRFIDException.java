
/**
 * This exception is used when an RFID is being created or modified for an item, and it's invalid (i.e. not a 9-digit string, not a hex code)
 */
public class InvalidRFIDException extends Exception{
    public InvalidRFIDException() {

    }
}
