
/**
 * The ItemInfoNode is an added shell of code on an ItemInfo class that makes it implementable as a linked list
 *      by setting links to other ItemInfoNodes.
 */
public class ItemInfoNode {
    private ItemInfo info;
    private ItemInfoNode prev;
    private ItemInfoNode next;
    /**
     * The no-arg constructor for the node object (never actually used)
     * This is the constructor mentioned in the documentation but I decided to make another with an ItemInfo data param
     *      to make implementation more convenient for me personally.
     */
    public ItemInfoNode() {
        info = null;
        prev = null;
        next = null;
    }
    /**
     * The actual constructor for an ItemInfoNode, filling in only the data of the node
     * @param x the ItemInfo object that contains all the relevant data of the single item being represented in the node
     */
    public ItemInfoNode(ItemInfo x) {
        info = x;
        prev = null;
        next = null;
    }
    /**
     * The getter method for the data of the node
     * @return the ItemInfo object being held as data in the node
     */
    public ItemInfo getInfo() {
        return info;
    }
    /**
     * The getter method for the previous link to the node.
     * @return the previous ItemInfoNode linked to the object in this theoretical horizontal list
     */
    public ItemInfoNode getPrev() {
        return prev;
    }
    /**
     * The getter method for the next link to the node.
     * @return the next ItemInfoNode linked to the object in this theoretical horizontal list
     */
    public ItemInfoNode getNext() {
        return next;
    }
    /**
     * The setter method for the data of the node.
     * @param info a new ItemInfo object being used as data.
     */
    public void setInfo(ItemInfo info) {
        this.info = info;
    }
    /**
     * The setter method for the previous link to the node.
     * @param prev a new ItemInfoNode object being used as a previous link to this current node.
     */
    public void setPrev(ItemInfoNode prev) {
        this.prev = prev;
    }
    /**
     * The setter method for the next link to the node.
     * @param next a new ItemInfoNode object being used as a next link to this current node.
     */
    public void setNext(ItemInfoNode next) {
        this.next = next;
    }
    /**
     * The implemented method returning the string representation of only the data of the node.
     * @return the string representation of the data of the node (ItemInfo object)
     */
    public String toString() {
        return info.toString();
    }
}
