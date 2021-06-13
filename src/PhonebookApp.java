import java.io.*;
import java.util.*;

// The class Name takes two strings, last and first, and turns them into a Name object
// which pieces the two strings together.
class Name {

    private String first, last;

    public Name(String last, String first) {
        this.last = last;
        this.first = first;
    }

    public Name(String first) {
        this("", first);
    }

    public String getFormal() {
        return first + " " + last;
    }

    public String getOfficial() {
        return last + ", " + first;
    }

    public boolean equals(Name other) {
        return first.equals(other.first) && last.equals(other.last);
    }

    public String toString() {
        return first + " " + last;
    }

    public String getInitials() {
        return first.substring(0, 1) + "." + last.substring(0, 1) + ".";
    }

    public static Name read(Scanner scanner) {
        if (!scanner.hasNext()) return null;
        String last = scanner.next();
        String first = scanner.next();
        return new Name(last, first);
    }
} // end Name class

// The class PhoneNumber takes a string that represents a phone number and turns it
// into a PhoneNumber object.
class PhoneNumber {
    private String phoneNumber;

    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAreaCode() {
        return phoneNumber.substring(1, 4);
    }

    public String getExchange() {
        return phoneNumber.substring(5, 8);
    }

    public String getLineNumber() {
        return phoneNumber.substring(9, 13);
    }

    public boolean isTollFree() {
        if (phoneNumber.substring(1, 2).equals("8")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equals(PhoneNumber other) {
        return phoneNumber.equals(other.phoneNumber);
    }

    public String toString() {
        return phoneNumber;
    }

    public static PhoneNumber read(Scanner sc) {
        if (!sc.hasNext()) return null;
        String phoneNumber = sc.next();
        return new PhoneNumber(phoneNumber);
    }
} // end PhoneNumber class

// The class PhonebookEntry takes a Name object and a PhoneNumber object, pieces them
// together and creates a PhonebookEntry object.
class PhonebookEntry {
    private Name name;
    private PhoneNumber phoneNumber;

    public PhonebookEntry(Name name, PhoneNumber phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Name getName() {
        return name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void call() {
        if (phoneNumber.isTollFree()) {
            System.out.println("Dialing (toll-free) " + name.toString() + ": " + phoneNumber.toString());
        } else {
            System.out.println("Dialing " + name.toString() + ": " + phoneNumber.toString());
        }
    }

    public String toString() {
        return name.toString() + ": " + phoneNumber.toString();
    }

    public boolean equals(Name otherName, PhoneNumber otherNumber) {
        return name.equals(otherName) && phoneNumber.equals(otherNumber);
    }

    public static PhonebookEntry read(Scanner sc) {
        if (!sc.hasNext()) return null;
        String last = sc.next();
        String first = sc.next();
        String phoneNumberInFile = sc.next();

        Name name = new Name(last, first);
        PhoneNumber phoneNumber = new PhoneNumber(phoneNumberInFile);

        return new PhonebookEntry(name, phoneNumber);
    }
} // end PhonebookEntry class


// The class PhonebookApp has the ability to either find someone's phone number if given a name
// or find someone's name if given a phone number.
    class PhonebookApp {

    /* The entire main method is placed into a try catch block which will catch both
     * the FileNotFound and Exception exceptions. First, main will initialize a PhonebookEntry object
     * array which has a maximum capacity of 100. It will then be sent to the read method. If the capacity of the
     * phone book has been exceeded, an ArrayIndexOutOfBoundsException will be caught. Once the array is filled with
     * PhonebookEntry objects from the text file "phonebook.text", the user will be prompted to enter a
     * letter corresponding with the action they want to perform, either a lookup, reverse-lookup or quit the program.
     * Then, the letter will be sent to a while loop where the program will perform the action that
     * the user requested, looping at the end if given either letters l or r again or quitting if given the letter q.
     */
        public static void main(String[] args) throws IOException {

            try {

                final int SIZE = 100;
                int lookupsPerformed = 0, reverseLookupsPerformed = 0;
                PhonebookEntry[] entries = new PhonebookEntry[SIZE];

                int numElts = read("phonebook.text", entries);

                Scanner sc = new Scanner(System.in);
                System.out.print("lookup, reverse-lookup, quit (l/r/q)? ");

                while (sc.hasNext()) {
                    String choice = sc.next();

                    if (choice.equalsIgnoreCase("l")) {
                        System.out.print("last name? ");
                        String lastName = sc.next();
                        System.out.print("first name? ");
                        String firstName = sc.next();

                        Name fullName = new Name(lastName, firstName);

                        String number = lookup(numElts, entries, fullName);
                        if (number.equals("")) {
                            System.out.println("-- Name not found");
                        } else {
                            System.out.println(firstName + " " + lastName + "'s phone number is " + number);
                        }
                        lookupsPerformed++;

                    } else if (choice.equalsIgnoreCase("r")) {
                        System.out.print("phone number (nnn-nnn-nnnn)? ");
                        String number = sc.next();

                        PhoneNumber givenNumber = new PhoneNumber(number);

                        String name = reverseLookup(numElts, entries, givenNumber);
                        if (name.equals("")) {
                            System.out.println("-- Phone number not found");
                        } else {
                            System.out.println(number + " belongs to " + name);
                        }
                        reverseLookupsPerformed++;

                    } else if (choice.equalsIgnoreCase("q")) {
                        System.out.println();
                        System.out.println(lookupsPerformed + " lookups performed");
                        System.out.print(reverseLookupsPerformed + " reverse lookups performed\n");
                        System.exit(0);

                    } else {
                        System.out.println("ERROR: Bad input. ");
                    }

                    System.out.print("\nlookup, reverse-lookup, quit (l/r/q)? ");
                } // end while loop
                sc.close();

            } catch (FileNotFoundException s) {
                System.out.println("*** IOException *** phonebook.text (No such file or directory");
            }
            catch (Exception s) {
                s.printStackTrace();
            }
        }// end main

        /** The read method will read from file "phonebook.text", turn the data into PhonebookEntry objects
         * and place those objects into the entries array.
         *
         @param filename name of file with input.
         @param entries empty array of type PhonebookEntry to be filled.
         @return an int representing the size of the entries array.
         */
        public static int read (String filename, PhonebookEntry[] entries) throws IOException{
            int count=0; // number of lines read in
            Scanner sc = new Scanner(new File(filename));
            PhonebookEntry phoneNumber;

            while(sc.hasNext()) {

                try {

                    phoneNumber = PhonebookEntry.read(sc);
                    entries[count] = phoneNumber;
                    count++;

                } catch (ArrayIndexOutOfBoundsException s) {
                    System.out.println("*** Exception *** Phonebook capacity exceeded - increase size of underlying array");
                    System.exit(0);
                }
            } // end while loop
            return count;
        } // end read

    /** The lookup method performs a linear search to find the phone number based on a given name.
     *
     * @param size the size of the PhonebookEntry array.
     * @param entries an array of type PhonebookEntry filled with PhonebookEntry objects.
     * @param fullName a Name object consisting of the last and first name given by the user.
     * @return a string representing the phone number that belongs to the given fullName.
     */
        public static String lookup (int size, PhonebookEntry[] entries, Name fullName) {
            for (int i = 0; i < size; i++) {
                Name name = entries[i].getName();
                PhoneNumber number = entries[i].getPhoneNumber();

                if (name.equals(fullName)) {
                    return number.toString();
                }
            }
            // if nothing is found
            return "";
        } // end lookup

    /** The reverseLookup method performs a linear search to find the name based on a given phone number.
     *
     * @param size size the size of the PhonebookEntry array.
     * @param entries an array of type PhonebookEntry filled with PhonebookEntry objects.
     * @param givenNumber a PhoneNumber object consisting of a phone number given by the user.
     * @return a string representing a name that belongs to the given phone number.
     */
        public static String reverseLookup (int size, PhonebookEntry[] entries, PhoneNumber givenNumber) {
            for (int i = 0; i < size; i++) {
                Name name = entries[i].getName();
                PhoneNumber number = entries[i].getPhoneNumber();

                if (number.equals(givenNumber)) {
                    return name.toString();
                }
            }

            // if nothing is found
            return "";
        } // end reverseLookup
    } // end Phonebook class