/*
 * Represents a movie theater with 10 rows labeled alphabetically, starting front to back, and 20 columns labeled
 * numerically, starting left to right (facing the front of the theater).
 *
 * Seats will be assigned with public safety and customer satisfaction in mind, spacing groups apart.
 */
public class MovieTheaterSeats {

    private int rows;           // The number of rows in the theater
    private int cols;           // The number of columns in the theater

    // roomLeftInRow[i][0] indicates the amount of room left on the left side of the row
    // roomLeftInRow[i][1] indicates the amount of room left on the right side of the row
    public int[][] roomLeftInRow;

    public MovieTheaterSeats(int rows, int cols) {
        this.rows = Math.max(rows, 0);
        this.cols = Math.max(cols, 0);

        roomLeftInRow = new int[this.rows][2];
        for (int i = 0; i < this.rows; i++) {
            roomLeftInRow[i][0] = this.cols / 2;
            roomLeftInRow[i][1] = this.cols - (this.cols / 2);
        }
    }

    /*
     * Reserves a group of adjacent seats.
     *
     * For public safety and customer satisfaction, rewarding the earliest reservations this prioritizes:
     * - back to front
     * - one group per row
     * - 3 spaces between groups in a row
     * - centered seating
     *
     * Returns a String containing the seats that were assigned, e.g. "A1,A2,A3"
     */
    public String reserveGroupSeats(int numSeats) {

        if (numSeats > this.cols) {
            return null;
        }

        String reservedSeats = "";

        // If the front row is not empty, we know no other rows are empty since we filled up back-to-front
        if (roomLeftInRow[0][0] + roomLeftInRow[0][1] == this.cols) {
            // Iterate rows back-to-front, looking for empty rows.
            for (int i = this.rows - 1; i >= 0; i--) {
                // If the row is empty, assign the group to the center of the row
                if (roomLeftInRow[i][0] + roomLeftInRow[i][1] == this.cols) {
                    int seatsOnLeft = numSeats / 2;
                    int seatsOnRight = numSeats - seatsOnLeft;
                    int groupStart = this.cols / 2 - seatsOnLeft;

                    reservedSeats += indexToSeatPosition(i, groupStart);
                    for (int j = groupStart + 1; j < groupStart + numSeats; j++) {
                        reservedSeats += "," + indexToSeatPosition(i, j);
                    }

                    roomLeftInRow[i][0] -= seatsOnLeft + 3;  // Add 3 to leave a 3-seat buffer between groups
                    roomLeftInRow[i][1] -= seatsOnRight + 3;
                    return reservedSeats;
                }
            }
        }

        // If we didn't find an empty row, iterate back-to-front again, trying to fill in existing rows
        for (int i = this.rows - 1; i >= 0; i--) {
            // See if this row will fit the group.
            // If the group fits on both sides, add it to the side where they are closer to the middle
            // (whichever side has more room)
            if (roomLeftInRow[i][0] >= numSeats && roomLeftInRow[i][0] > roomLeftInRow[i][1]) {
                int groupStart = roomLeftInRow[i][0] - numSeats;

                reservedSeats += indexToSeatPosition(i, groupStart);
                for (int j = groupStart + 1; j < groupStart + numSeats; j++) {
                    reservedSeats += "," + indexToSeatPosition(i, j);
                }

                roomLeftInRow[i][0] -= numSeats + 3;
                return reservedSeats;
            } else if (roomLeftInRow[i][1] >= numSeats) {
                int groupStart = this.cols - roomLeftInRow[i][1];

                reservedSeats += indexToSeatPosition(i, groupStart);
                for (int j = groupStart + 1; j < groupStart + numSeats; j++) {
                    reservedSeats += "," + indexToSeatPosition(i, j);
                }

                roomLeftInRow[i][1] -= numSeats + 3;
                return reservedSeats;
            }
        }

        return null;  // Unable to find an available seat
    }

    /*
     * Converts integer indices in the seats array to their official labels (rows labeled alphabetically,
     * columns numerically)
     */
    private String indexToSeatPosition(int i, int j) {
        return (char) (i + 65) + "" + (j + 1);
    }
}
