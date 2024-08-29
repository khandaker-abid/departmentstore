
/**
 * The ItemList class represents an actual list of ItemListNodes (visualized as a horizontal snake of connected nodes)
 *      with a head and tail node. A cursor node is also implemented as a data field to make the implementation easier.
 *      A finalized string of a table format is added to make printing lists far simpler.
 * For the analysis of method complexities, I included a general order and for some a more technical order where I decided
 *      to add an r variable for the number of characters in a string being used for the method to be more accurate. It
 *      should be noted that by order of complexity I assumed that this was only referring to time. Otherwise, note that
 *      ALL METHODS BEING JUDGED ARE ALSO O(1) SPACE COMPLEXITY. Also, n is generally the number of nodes in an ItemList.
 */
public class ItemList {
    private ItemInfoNode head;
    private ItemInfoNode tail;
    private ItemInfoNode cur;
    final private String table = String.format("%-23s%-12s%-15s%-21s%-21s", "Name", "Price", "RFID", "Original Location", "Current Location") +"\n---------------------------------------------------------------------------------------";
    /**
     * A no-arg constructor of a list and the only constructor necessary
     */
    public ItemList() {
        head = null;
        tail = null;
        cur = null;
    }
    /**
     * Inserts an ItemInfoNode to the ItemList by taking parameters to make an ItemInfo object and an ItemInfoNode object out of that.
     *      Sets current location data field of ItemInfo object as initPosition as that's the current location too logically.
     *      Throws exceptions via constructor before insertion.
     *      O(n) time because iterates thru list, technically O(n*r) if you count r as the number of characters in RFID strings being compared
     * @param name the name of the item
     * @param rfidTag the RFID of the item
     * @param price the price of the item
     * @param initPosition the original position of the item being sent to
     * @throws InvalidLocationException when the initial position isn't valid as an original location (check ItemInfo constructor)
     * @throws InvalidRFIDException when the given RFID isn't valid (check ItemInfo constructor)
     * @throws NegativePriceException when the given price is negative (not possible)
     */
    public void insertInfo (String name, String rfidTag, double price, String initPosition) throws InvalidLocationException, InvalidRFIDException, NegativePriceException{
        ItemInfoNode x = new ItemInfoNode(new ItemInfo(name, price, rfidTag, initPosition));
        if (head == null) {
            head = x;
            tail = x;
            cur = x;
        } else {
            cur = head;
            while(cur!=null && x.getInfo().getRfidTagNumber().compareTo(cur.getInfo().getRfidTagNumber())>0) {
                cur = cur.getNext();
            }
            if (cur == head) {
                x.setNext(head);
                head.setPrev(x);
                head = x;
                return;
            }
            if(cur == null) {
                tail.setNext(x);
                x.setPrev(tail);
                tail = x;
                return;
            }
            x.setNext(cur);
            x.setPrev(cur.getPrev());
            cur.getPrev().setNext(x);
            cur.setPrev(x);
        }
    }
    /**
     * Removes all ItemInfoNodes in the ItemList that are purchased (i.e. has a current location of "out")
     * O(n) time because iterates thru list
     */
    public void removeAllPurchased() {
        if(head == null) {
            return;
        }
        cur = head;
        while(cur!=null) {
            if(cur.getInfo().getCurrentLocation().equals("out")) {
                ItemInfoNode x = cur;
                System.out.println(cur);
                cur = cur.getNext();
                remove(x);
            } else {
                cur = cur.getNext();
            }
        }
    }
    /**
     * A method that searches the ItemList for an ItemInfo object with the same parameter RFID in the source location.
     *      Then, moves it from the source location to the destination.
     *      O(n) time because iterates thru list, technically O(n*r) if you count r as number of characters in currentLocation data field
     * @param rfidTag the given RFID being checked for
     * @param source the source location of the node being checked for
     * @param dest the new destination of the moved item
     * @return true if an item was found and thus moved, false if not
     * @throws InvalidLocationException if either the source is "out" or the destination is not a valid location to be sent
     *      to (same conditions as a current location)
     */
    public boolean moveItem(String rfidTag, String source, String dest) throws InvalidLocationException {
        if(head == null) {
            return false;
        }
        if(source.equalsIgnoreCase("out") || !ItemInfo.checkCurLocation(dest)) {
            throw new InvalidLocationException();
        }
        cur = head;
        while(cur!=null) {
            if(cur.getInfo().getRfidTagNumber().equals(rfidTag) && cur.getInfo().getCurrentLocation().equals(source)) {
                cur.getInfo().setCurrentLocation(dest);
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }
    /**
     * Prints a table with the data of all nodes in the ItemList
     * O(n) time because iterates thru list
     */
    public void printAll() {
        System.out.println(table);
        cur = head;
        while(cur!=null) {
            System.out.println(cur);
            cur = cur.getNext();
        }
    }
    /**
     * Prints a table with the data of all nodes in the ItemList in a given location
     * O(n) time because iterates thru list, technically O(n*r) if you count r as number of characters in currentLocation data field
     * @param location the location being searched for nodes
     */
    public void printByLocation(String location) {
        System.out.println(table);
        cur = head;
        while(cur!=null) {
            if(cur.getInfo().getCurrentLocation().equalsIgnoreCase(location)) {
                System.out.println(cur);
            }
            cur = cur.getNext();
        }
    }
    /**
     * Organizes store by checking all nodes in the ItemList if they were moved and if so putting them pack to their original spot
     * O(n) time because iterates thru list, technically O(n*r) if you count r as number of characters in currentLocation data field
     * @throws InvalidLocationException never actually, but must be thrown as the exception is throwable as part of one of the setCurrentLocation method
     */
    public void cleanStore() throws InvalidLocationException {
        System.out.println(table);
        cur = head;
        while(cur!=null) {
            if(ItemInfo.checkOrigLocation(cur.getInfo().getCurrentLocation()) && !cur.getInfo().getCurrentLocation().equals(cur.getInfo().getOriginalLocation())) {
                System.out.println(cur);
                cur.getInfo().setCurrentLocation(cur.getInfo().getOriginalLocation());
            }
            cur = cur.getNext();
        }
    }
    /**
     * Takes a cart location and purchases/prints all items in that cart (converts currentLocation of nodes in the location to "out")
     * O(n) time because iterates thru list, technically O(n*r) if you count r as number of characters in currentLocation data field
     * @param cartNumber the source location being searched for in the ItemListNodes in the ItemList
     * @return the total number of money spent to purchase the items
     * @throws InvalidLocationException when the given cartNumber isn't a valid cart string (not in the format of "cXXX")
     */
    public double checkOut(String cartNumber) throws InvalidLocationException {
        System.out.println(table);
        cur = head;
        double sum = 0;
        if(!isCart(cartNumber)) {
            throw new InvalidLocationException();
        }
        while(cur!=null) {
            if(cur.getInfo().getCurrentLocation().equalsIgnoreCase(cartNumber)) {
                cur.getInfo().setCurrentLocation("out");
                sum += cur.getInfo().getPrice();
                System.out.println(cur);
            }
            cur = cur.getNext();
        }
        return sum;
    }
    /**
     * Prints all ItemInfoNodes in the ItemList that match the given RFID
     * @param rfid the given string meant to be an RFID being searched and checked
     * @throws InvalidRFIDException when the given string isn't a valid RFID (check ItemInfo constructor)
     */
    public void printByRFID(String rfid) throws InvalidRFIDException {
        if(!ItemInfo.checkRFID(rfid)) {
            throw new InvalidRFIDException();
        }
        System.out.println(table);
        cur = head;
        while(cur!=null) {
            if(cur.getInfo().getRfidTagNumber().equalsIgnoreCase(rfid)) {
                System.out.println(cur);
            }
            cur = cur.getNext();
        }
    }
    /**
     * Helper method used to easily remove an ItemInfoNode from the ItemList
     * @param x the ItemInfoNode being removed
     */
    public void remove(ItemInfoNode x) {
        if(x == head) {
            x.getNext().setPrev(null);
            head = x.getNext();
        } else if (x == tail) {
            x.getPrev().setNext(null);
            tail = x.getPrev();
        } else {
            x.getPrev().setNext(x.getNext());
            x.getNext().setPrev(x.getPrev());
        }
    }
    /**
     * Helper method checking if a string is a valid cart location (starts with 'c' and has 3 digits after)
     * @param x the string being tested
     * @return true if the string is a valid cart location, false if not
     */
    public static boolean isCart(String x) {
        if(x.charAt(0)!='c') {
            return false;
        }
        if(x.length()!=4) {
            return false;
        }
        for(int i = 1; i < x.length(); i++) {
            if(x.charAt(i)<48||x.charAt(i)>57) {
                return false;
            }
        }
        return true;
    }
}
