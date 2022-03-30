# MovieTheaterSeatingAssignment

Build Instructions:
-
1) Clone/move MovieTheaterSeatingAssignment.java and MovieTheaterSeats.java to the directory where you desire to create the output file.
2) Create a seating reservation request .txt file, where each line is of the following format where #### is a four-digit number:
```aidl
R#### <number of seats to be reserved>
e.g. R0009 6
```
3) With Java 8+ installed, compile MovieTheaterSeatingAssignment.java with the following command:
```aidl
javac MovieTheaterSeatingAssignment.java
```
4) Create a seating assignment by running a command of the format:
```aidl
java MovieTheaterSeatingAssignment <path_to_seating_reservation_request_file>.txt <name_of_output_file>.txt

```

Assumptions:
- 
- Customer satisfaction and safety prioritizes:
  - Empty rows will be filled before partially filled rows
  - Three spaces are required between groups in the same row
  - The seats closest to the center
- Once no more seats are available, further reservations are ignored
- Assumes the given reservation ID is valid/in order. Will use the same ID in the output file