
import java.util.Scanner;
/**
 * The DepartmentStore is the class having the main method/program of the assignment. This program replicates a UI for a
 *      store of items being made and manipulated in a store's inventory. Operated on a while loop and uses Scanner for input
 *      and a general Exception catch to make Scanner graceful. Uses a single ItemList and some placeholder variables to take
 *      inputs in, as well as a constantly printed string for the prompts of the UI.
 */
public class DepartmentStore {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        ItemList x = new ItemList();
        /*try {
            x.insertInfo("chip", "9321324F3", 4.29, "s13224");
            x.insertInfo("cookie", "3132D9012", 7.32, "s12345");
            x.insertInfo("drink", "D213224F3", 4.29, "s12345");
            x.insertInfo("hot dog", "D1B231B29", 7.39, "s12345");
        } catch(Exception e) {
            System.out.println("womp womp");
        }*/                                                  //used for test debugging
        String name; double price; String rfid; String origLoc; String curLoc; String varLoc;
        String prompt = "Welcome!\n" +
                "\t\n" +
                "    C - Clean store \n" +
                "    I - Insert an item into the list \n" +
                "    L - List by location \n" +
                "    M - Move an item in the store \n" +
                "    O - Checkout  \n" +
                "    P - Print all items in store \n" +
                "    R - Print by RFID tag number  \n" +
                "    U - Update inventory system   \n" +
                "    Q - Exit the program. \n\n" +
                "    Please select an option: ";
        while(true) {
            try {
                System.out.print(prompt);
                char input = norm(s.nextLine().charAt(0));
                System.out.println();
                switch (input) {
                    case 'c':
                        try {
                            System.out.println("Sure! Organizing the store. Displaying all moved items...");
                            x.cleanStore();
                        } catch(InvalidLocationException e1) {
                            System.out.println("Invalid location!! Error!!");
                        }
                        break;
                    case 'i':
                        System.out.print("Alright! Please state the name of the product: ");
                        name = s.nextLine();
                        System.out.print("Now, please state the price: ");
                        price = round(s.nextDouble());
                        s.nextLine();
                        System.out.print("Now, please state the RFID number: ");
                        rfid = s.nextLine();
                        System.out.print("Finally, please state the location you want to put the product in: ");
                        origLoc = s.nextLine();
                        if(name.length()>20) {
                            System.out.println("Sorry, the name was too long. Please print a name for the item under 20 letters and try again.");
                            continue;
                        }
                        try {
                            x.insertInfo(name, rfid, price, origLoc);
                            System.out.println("Successful! Item added!");
                        } catch (NegativePriceException ex1) {
                            System.out.println("Sorry, the price listed is negative. Please only use a positive price and try again.");
                        } catch (InvalidRFIDException ex2) {
                            System.out.println("Sorry, the RFID given is invalid. Please give a 9-letter string in hex (0-F) and try again.");
                        } catch (InvalidLocationException ex3) {
                            System.out.println("Sorry, the given location does not exist. Please state a shelf position (s12345) and try again.");
                        }
                        break;
                    case 'l':
                        System.out.print("Sure! Please state the location you want to search from: ");
                        varLoc = s.nextLine();
                        if(!ItemInfo.checkCurLocation(varLoc)) {
                            System.out.println("Sorry, this location is invalid. Please try again with a valid shelf or cart location.");
                        }
                        System.out.println("Alright. Printing all items from " + varLoc + "...");
                        x.printByLocation(varLoc);
                        break;
                    case 'm':
                        System.out.print("Alright! Please state the RFID tag of the product: ");
                        rfid = s.nextLine();
                        System.out.print("Now, please state the source location: ");
                        curLoc = s.nextLine();
                        System.out.print("Finally, please state the desired destination: ");
                        varLoc = s.nextLine();
                        try {
                            if(varLoc.equalsIgnoreCase("out")) {
                                System.out.println("Sorry, the destination cannot be out because we don't allow shoplifting! Please try again.");
                                continue;
                            }
                            if(x.moveItem(rfid, curLoc, varLoc)) {
                                System.out.println("Success! The product was found and moved.");
                            } else {
                                System.out.println("Sorry, there was no product found with the given RFID in our inventory.");
                            }
                        } catch (InvalidLocationException ex3) {
                            if(curLoc.equalsIgnoreCase("out")) {
                                System.out.println("Sorry, the source location cannot be out the store. Please try again.");
                            } else {
                                System.out.println("Sorry, the destination is invalid. Please write a valid address (cart or shelf) for the destination and try again.");
                            }
                        }
                        break;
                    case 'o':
                        System.out.print("Welcome to checkout! Please state your cart location: ");
                        curLoc = s.nextLine();
                        try {
                            double sum = x.checkOut(curLoc);
                            System.out.println("Thank you for buying! The checkout sum was $" + String.format("%.2f", sum) + ".");
                        } catch (InvalidLocationException ex1) {
                            System.out.println("Sorry, the given location is not a cart location. Please print a correct cart location (i.e. c123) and try again.");
                        }
                        break;
                    case 'p':
                        System.out.println("Alright! Printing all items...");
                        x.printAll();
                        break;
                    case 'r':
                        System.out.print("Sure! Please state the RFID tag you want to search: ");
                        rfid = s.nextLine();
                        System.out.println("Alright! Displaying all products with the tag " + rfid + "...");
                        try {
                            x.printByRFID(rfid);
                        } catch (InvalidRFIDException ex1) {
                            System.out.println("Sorry, the given string isn't valid as an RFID. Please print a 9-letter string in hex (0-F) and try again.");
                        }
                        break;
                    case 'u':
                        System.out.println("Sure! Updating inventory...");
                        x.removeAllPurchased();
                        System.out.println("Done!");
                        break;
                    case 'q':
                        System.out.println("Alright! Program terminating gracefully...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("This input is not not valid for any of the prompts. Please try again.");
                }
            } catch(Exception e0) {
                System.out.println("Invalid input!!! Please try again.");
            }
            System.out.println();
        }
    }
    /**
     * Helper method meant to normalize Scanner character inputs to lowercase letters if uppercase (halves cases)
     * @param x the character being normalized
     * @return the new normalized character (always lowercase if it's a letter)
     */
    public static char norm(char x) {
        if(x>=65&&x<=90) {
            return (char)(x+32);
        }
        return x;
    }
    /**
     * Helper method used to round price inputs to X.XX format to make usability smoother
     * @param x the price being rounded to X.XX format
     * @return the rounded price which will be used in ItemInfo constructors
     */
    public static double round(double x) {
        x = x*100;
        x = Math.round(x);
        x = x/100;
        return x;
    }
}
