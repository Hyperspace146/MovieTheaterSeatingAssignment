# MovieTheaterSeatingAssignment

Takes a text file containing movie theater seating reservations as a command line argument and creates a text file in the same folder containing the resulting occupied seats. Seats are assigned by prioritizing customer satisfaction and public safety.

Movie theater layout:
- 10 rows, labeled A through J from front (near the screen) to back.
- 20 columns, labeled 1 through 20 from left to right (assuming you are facing the screen).

Build Instructions:
-
1) Clone/move MovieTheaterSeatingAssignment.java and MovieTheaterSeats.java to the directory where you desire to create the output file.
2) Create a seating reservation request .txt file, where each line is of the following format where #### is a four-digit number:
```
R#### <number of seats to be reserved>
e.g. R0009 6
```
3) With Java 8+ installed, compile MovieTheaterSeatingAssignment.java and MovieTheaterSeats.java with the following command:
```
javac MovieTheaterSeatingAssignment.java MovieTheaterSeats.java
```
4) Create a seating assignment by running a command of the format:
```
java MovieTheaterSeatingAssignment <path_to_seating_reservation_file>.txt <name_of_output_file>.txt
```

Assumptions:
- 
- Customer satisfaction and safety prioritizes:
  - Empty rows before partially filled rows
  - The seats closest to the center
  - Three spaces are required between groups in the same row
- Once no more seats are available, further reservations are ignored
- Assumes the given reservation ID is valid/in order. Will use the same ID in the output file