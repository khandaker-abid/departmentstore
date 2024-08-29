
/**
 * The ItemInfo class is a class representing all info on a single item being sold in the main store.
 */
public class ItemInfo {
    private String name;
    private double price;
    private String rfidTagNumber;
    private String originalLocation;
    private String currentLocation;
    /**
     * No-arg constructor (never used, just added for consistency)
     */
    public ItemInfo() {
        name = null;
        price = 0;
        rfidTagNumber = null;
        originalLocation = null;
        currentLocation = null;
    }
    /**
     * The main constructor method representing all data fields to create a single item (the only constructor actually being used)
     * @param a the name of the item being sold
     * @param b the price of the item
     * @param c the RFID tag number of the item (used to organize items in the store)
     * @param d the location of the item being sent to. Can only be a shelf as its starting position, starting with s and having 5 digits ("s12345" is valid)
     * @throws NegativePriceException when the price is negative (can't be negative)
     * @throws InvalidLocationException when the location d is invalid (i.e. not 6-digit string, doesn't start with 's' implying not a shelf location)
     * @throws InvalidRFIDException when the RFID is invalid (i.e. not a 9-digit string, not a hex code)
     */
    public ItemInfo(String a, double b, String c, String d) throws NegativePriceException, InvalidLocationException, InvalidRFIDException {
        if (b < 0) {
            throw new NegativePriceException();
        } else if(!checkRFID(c)) {
            throw new InvalidRFIDException();
        } else if(!checkOrigLocation(d)) {
            throw new InvalidLocationException();
        }
        name = a;
        price = b;
        rfidTagNumber = c;
        originalLocation = d;
        currentLocation = d;
    }
    /**
     * Getter method for the name of the item
     * @return the name of the item
     */
    public String getName() {
        return name;
    }
    /**
     * Getter method for the price of the item
     * @return the price of the item
     */
    public double getPrice() {
        return price;
    }
    /**
     * Getter method for the RFID of the item
     * @return the RFID of the item
     */
    public String getRfidTagNumber() {
        return rfidTagNumber;
    }
    /**
     * Getter method for the original location of the item
     * @return the original location of the item
     */
    public String getOriginalLocation() {
        return originalLocation;
    }
    /**
     * Getter method for the current location of the item
     * @return the current location of the item
     */
    public String getCurrentLocation() {
        return currentLocation;
    }
    /**
     * Setter method for the name of the item
     * @param name the new name of the item
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Setter method for the price of the item
     * @param price the new price of the item
     * @throws NegativePriceException when the new price is negative
     */
    public void setPrice(double price) throws NegativePriceException{
        if (price < 0) {
            throw new NegativePriceException();
        }
        this.price = price;
    }
    /**
     * Setter method for the RFID of the item
     * @param rfidTagNumber the new RFID of the item
     * @throws InvalidRFIDException when the RFID is invalid (i.e. not a 9-digit string, not a hex code)
     */
    public void setRfidTagNumber(String rfidTagNumber) throws InvalidRFIDException {
        if(!checkRFID(rfidTagNumber)) {
            throw new InvalidRFIDException();
        }
        this.rfidTagNumber = rfidTagNumber;
    }
    /**
     * Setter method for the original location of the item
     * @param originalLocation the new original location of the item
     * @throws InvalidLocationException when the original location isn't plausible (i.e. not 6-digit string, doesn't start with 's' implying not a shelf location)
     */
    public void setOriginalLocation(String originalLocation) throws InvalidLocationException {
        if(!checkOrigLocation(originalLocation)) {
            throw new InvalidLocationException();
        }
        this.originalLocation = originalLocation;
    }
    /**
     * Setter method for the current location of the item
     * @param currentLocation the new current location of the item
     * @throws InvalidLocationException when the current item isn't plausible (not a shelf or cart location or "out")
     */
    public void setCurrentLocation(String currentLocation) throws InvalidLocationException {
        if(!checkCurLocation(currentLocation)) {
            throw new InvalidLocationException();
        }
        this.currentLocation = currentLocation;
    }
    /**
     * Helper method to check if a string is a valid RFID number
     * @param x the string being tested
     * @return true if it is, false if not
     */
    public static boolean checkRFID(String x) {
        if(x.length()!=9) {
            return false;
        }
        for(int i = 0; i < x.length(); i++) {
            if (!((x.charAt(i)>47&&x.charAt(i)<58)||(x.charAt(i)>64&&x.charAt(i)<71)||(x.charAt(i)>96&&x.charAt(i)<103))) {
                return false;
            }
        }
        return true;
    }
    /**
     * Helper method to check if a string is a valid original location
     * @param x the string being tested
     * @return true if it is, false if not
     */
    public static boolean checkOrigLocation(String x) {
        if(x.length()!=6) {
            return false;
        }
        if(x.charAt(0) != 's') {
            return false;
        }
        for(int i = 1; i < x.length(); i++) {
            if(x.charAt(i)<48||x.charAt(i)>57) {
                return false;
            }
        }
        return true;
    }
    /**
     * Helper method to check if a string is a valid current location
     * @param x the string being tested
     * @return true if it is, false if not
     */
    public static boolean checkCurLocation(String x) {
        if(x.charAt(0)=='s') {
            if(x.length()!=6) {
                return false;
            }
            for(int i = 1; i < x.length(); i++) {
                if(x.charAt(i)<48||x.charAt(i)>57) {
                    return false;
                }
            }
            return true;
        } else if(x.charAt(0)=='c') {
            if(x.length()!=4) {
                return false;
            }
            for(int i = 1; i < x.length(); i++) {
                if(x.charAt(i)<48||x.charAt(i)>57) {
                    return false;
                }
            }
            return true;
        } else {
            return x.equalsIgnoreCase("out");
        }
    }
    /**
     * Implemented method returning the string representation of all data fields of this item
     */
    public String toString() {
        return (String.format("%-23s%-12.2f%-15s%-21s%-21s", name, price, rfidTagNumber, originalLocation, currentLocation));
    }
}