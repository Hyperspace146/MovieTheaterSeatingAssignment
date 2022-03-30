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
import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;

public class MovieTheaterSeatingAssignment {

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
        MovieTheaterSeats seats = new MovieTheaterSeats(10, 20);
        try {
            while (input.hasNextLine()) {
                String reservation = input.nextLine();
                lineNumber += 1;

                // Tokenize the current reservation request and make sure the format is valid
                String[] tokens = reservation.trim().split(" ");
                if (tokens.length != 2) {
                    System.out.println("Incorrect number of arguments found in text file on line " + lineNumber + ". Make" +
                            "sure the seat reservation request is of the form: R#### <number of seats requested>");
                    input.close();
                    output.close();
                    return;
                }

                String resIDString = tokens[0];
                String numSeatsRequestedString = tokens[1];
                int resID;
                int numSeatsRequested;

                // Make sure the reservationID is of the format R####
                if (resIDString.length() != 5 || resIDString.charAt(0) != 'R') {
                    System.out.println("Incorrect reservation ID format. Make sure each line is of the " +
                            "form: R#### <number of seats requested>");
                    input.close();
                    output.close();
                    return;
                }

                // Parse the id and number of seats as integers
                try {
                    resID = Integer.parseInt(resIDString.substring(1));
                    numSeatsRequested = Integer.parseInt(numSeatsRequestedString);
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect seat reservation format. Make sure each line is of the " +
                            "form: R#### <number of seats requested>");
                    input.close();
                    output.close();
                    return;
                }


                // Compute the seat assignments based on our currently available seats
                String reservedSeats = seats.reserveGroupSeats(numSeatsRequested);
                if (reservedSeats == null) {
                    System.out.println("Reservation with ID " + resIDString + " was unable to be fulfilled.");
                }

                try {
                    output.write(resIDString + " " + reservedSeats + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }

            output.close();
            input.close();

            for (int[] a : seats.roomLeftInRow) {
                System.out.println(a[0] + " " + a[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
