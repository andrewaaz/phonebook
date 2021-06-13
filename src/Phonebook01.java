import java.io.*;
import java.util.Scanner;

// The class Phonebook01 has the ability to either find someone's phone number if given a name
// or find someone's name if given a phone number.
public class Phonebook01 {

    /* Main initializes three arrays, firstNames, lastNames, and numbers.
     * These arrays are then sent to the read method which reads in from a file, "phonebook.text",
     * in order to fill up the arrays with the necessary information. Then, the user is prompted to enter a
     * letter corresponding with the action they want to perform, either a lookup, reverse-lookup or quit the program.
     * Then, the letter chosen will be sent to a while loop where the program will take the letter given and perform
     * the action that the user requested, looping at the end if given either letters l or r again or quitting if given the letter q.
     */

    public static void main(String[] args) throws IOException  {

        final int SIZE=100;
        int lookupsPerformed = 0, reverseLookupsPerformed = 0;
        String[] firstNames = new String[SIZE];
        String [] lastNames = new String[SIZE];
        String[] numbers = new String[SIZE];

        int numElts = read("phonebook.text",firstNames,lastNames, numbers);

        Scanner sc = new Scanner(System.in);
        System.out.print("lookup, reverse-lookup, quit (l/r/q)? ");

        while (sc.hasNext()) {
            String choice = sc.next();

            if (choice.equals("l")) {
                System.out.print("last name? ");
                String lastName = sc.next();
                System.out.print("first name? ");
                String firstName = sc.next();
                String number = lookup(firstNames, lastNames, numbers, numElts, lastName, firstName);
                if (number.equals("")) {
                    System.out.println("-- Name not found");
                } else {
                    System.out.println(firstName + " " + lastName + "'s phone number is " + number);
                }
                lookupsPerformed++;

            } else if (choice.equalsIgnoreCase("r")) {
                System.out.print("phone number (nnn-nnn-nnnn)? ");
                String number = sc.next();
                String name = reverseLookup(firstNames, lastNames, numbers, numElts, number);
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
        }
        sc.close();
    } // end main

    /** read method reads from a file into the names and numbers arrays
     @param filename  name of file with input
     @param firstNames this is an empty array to be filled
     @param lastNames this is an empty array to be filled
     @param numbers this is an empty array to be filled
     @return int returns the number of data elements read in
     */

    public static int read(String filename, String[] firstNames, String[] lastNames, String[] numbers) throws IOException  {
        int count=0; // number of lines read in
        Scanner sc = new Scanner(new File(filename));
        // read until end of file
        while(sc.hasNext()) {
            if (count >= firstNames.length) {
                System.out.println("Phonebook01 capacity exceeded - increase size of underlying array");
                System.exit(1);
            }
            lastNames[count]=sc.next();
            firstNames[count]=sc.next();
            numbers[count]=sc.next();
            count++;
        }
        return count;
    } // end read

    /** lookup performs a linear search to find the phone number.
     @param firstNames
     @param lastNames
     @param numbers
     @param size
     @param lastName
     @param firstName
     @return String representing the phone number of the name searched
     */

    public static String lookup(String[] firstNames, String[] lastNames, String[] numbers, int size, String lastName, String firstName)  {
        for (int i=0; i<size; i++)
            if (firstNames[i].equals(firstName) && lastNames[i].equals(lastName)) {
                return numbers[i];
            }
        // if nothing is found
        return "";
    } // end lookup


    /** reverseLookup performs a linear search to find the name.
     @param firstNames
     @param lastNames
     @param numbers
     @param size
     @param number
     @return String representing the name of the person that corresponds with the phone number given.
     */

    public static String reverseLookup (String[] firstNames, String[] lastNames, String[] numbers, int size, String number) {
        for (int i = 0; i < size; i++) {
            if (numbers[i].equals(number)) {
                return lastNames[i] + ", " + firstNames[i];
            }
        }
        // if nothing is found
        return "";
    } // end reverseLookup
} // end Phonebook01