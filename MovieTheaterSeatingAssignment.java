/*
 * Program takes a seating reservation text file as a command line argument and creates a text file in the same
 * folder containing the resulting occupied seats.
 *
 * Command line format: java MovieTheaterSeatingAssignment [path_to_reservation_file].txt [name_of_output_file].txt
 *
 * Movie theater layout:
 * - There are 10 rows, labeled A through J from front (near the screen) to back.
 * - There are 20 columns, labeled 1 through 20 from left to right (assuming you are facing the screen).
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.FileReader;
import java.util.regex.Pattern;

public class MovieTheaterSeatingAssignment {

    public static final int ROWS = 10;
    public static final int COLS = 20;

    public static void main(String[] args) {

        /*
         * Process the given command line arguments for the seating reservation file:
         */
        // Make sure there is exactly one argument
        if (args.length != 2) {
            System.out.println("Incorrect number of arguments given. Please give two arguments:\n" +
                    "1) A text file with a seating reservation on each line of the form:" +
                    "\n    R#### <number of seats requested>\n" +
                    "   where #### is a four-digit number. e.g. R0001 4\n" +
                    "2) The name of the output file for the final seating assignments");
            return;
        }
        // Make sure the arg is a valid text file name (the shortest possible name is at least 5 characters)
        if (args[0].length() < 5 || !args[0].endsWith(".txt") || !args[1].endsWith(".txt")) {
            System.out.println("Please enter a valid text file name ending in '.txt'.");
            return;
        }

        Scanner input;
        BufferedWriter output;
        try {
            input = new Scanner(new FileReader(args[0]));
            output = new BufferedWriter(new FileWriter("seating_assignments.txt"));
        } catch (Exception e) {
            System.out.println("There's already a directory sharing a given file name. Please modify the directory or " +
                    "the given file name.");
            return;
        }

        /*
         * Process the file's reservation requests line-by-line and write to a file the corresponding seat assignments:
         */
        int lineNumber = 0;
        MovieTheaterSeats seats = new MovieTheaterSeats(ROWS, COLS);
        Pattern reservationFormat = Pattern.compile("R[0-9]{4} [0-9]+");
        try {
            while (input.hasNextLine()) {
                String reservation = input.nextLine();
                lineNumber += 1;

                // Make sure each reservation request has the right format using regex
                if (!reservationFormat.matcher(reservation).find()) {
                    System.out.println("Incorrect seat reservation format on line " + lineNumber +
                                    ". Make sure each line is of the form: R#### <number of seats requested>");
                    input.close();
                    output.close();
                    return;
                }

                // Parse the reservation request into an ID and the number of seats requested
                String[] tokens = reservation.trim().split(" ");
                String resID = tokens[0];
                int numSeatsRequested = Integer.parseInt(tokens[1]);

                // Compute the seat assignments based on our currently available seats
                String reservedSeats = seats.reserveGroupSeats(numSeatsRequested);
                if (reservedSeats == null) {
                    System.out.println("Reservation with ID " + resID + " on line " + lineNumber +
                            " was unable to be fulfilled.");
                }

                output.write(resID + " " + reservedSeats + "\n");
            }

            output.close();
            input.close();

            for (int[] a : seats.roomLeftInRow) {
                System.out.println(a[0] + " " + a[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
