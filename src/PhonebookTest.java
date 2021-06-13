import java.io.*;
import java.util.Scanner;

/** class Phonebook01 gives us the ability to look up a phone number */
public class PhonebookTest {

    /** main declares 2 parallel arrays, calls read method, loops to search
     */
    public static void main(String[] args) throws IOException  {

        final int SIZE=100;
        String[] names = new String[SIZE];
        String[] numbers = new String[SIZE];

        int numElts = read("phonebook.text",names,numbers);

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Name? ");
        while (keyboard.hasNext()) {
            String name = keyboard.next();
            String number = lookup(names, numbers, numElts, name);
            if (number.equals(""))
                System.out.println("Not found");
            else
                System.out.println(number);
            System.out.println("Name? ");
        }
        keyboard.close();
    }

    /** read method reads from a file into the names and numbers arrays
     @param filename  name of file with input
     @param names this is an empty array to be filled
     @param numbers this is an empty array to be filled
     @return int returns the number of data elements read in (IMPORTANT)
     */
    public static int read(String filename, String[] names, String[] numbers) throws IOException  {
        int count=0; // this is the number of lines read in
        Scanner scanner = new Scanner(new File(filename));
        // read until EOF
        while(scanner.hasNext()) {
            if (count >= names.length) {
                System.out.println("Phonebook01 capacity exceeded - increase size of underlying array");
                System.exit(1);
            }
            names[count]=scanner.next();
            numbers[count]=scanner.next();
            count++;
        }
        return count;
    }

    /** lookup performs a linear search to find the name.
     @param names
     @param numbers
     @param size
     @param name
     @return String representing the phone number of the name searched
     */
    public static String lookup(String[] names, String[] numbers, int size, String name)  {
        for (int i=0; i<size; i++)
            if (names[i].equals(name))
                return numbers[i];
        // if fall out, not found
        return "";
    }
}



