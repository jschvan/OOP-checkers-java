package cpsc2150.extendedCheckers.models;

/**
 * The BoardPosition class represents individual positions on the checkerboard. It has
 * fields for rows and columns of a checkerBoard, standard accessor methods, toString method,
 * equals method, and can add objects or double objects, creating a new object, or check if an
 * object is valid.
 */

public class BoardPosition
{
    /**
     * @invariant 0 <= row < ROW_NUM
     * @invariant 0 <= column < COL_NUM
     */

    /**
     * Row component of the BoardPosition
     */
    private int row;

    /**
     * Column component of the BoardPosition
     */
    private int column;

    /**
    * Constructor for a BoardPosition object
    *
    * @param aRow the row of BoardPosition
    * @param aCol the column of Board Position
    *
    * @pre 0 <= aRow < CheckerBoard.ROW_NUM AND 0 <= aCol < CheckerBoard.COL_NUM
    * @post row = aRow AND column = aCol
    */
    public BoardPosition(int aRow, int aCol) {
        /*
        Constructor for the BoardPosition object. This should set both the row and column instance variables to their
        default size.
         */
        this.row = aRow;
        this.column = aCol;
        
    }
    
    /**
    * Standard getter for the row of the BoardPosition
    * @return the row of the BoardPosition as an int
    * @pre None
    * @post getRow = row AND row = #row AND column = #column
    */
    public int getRow() {
        /*
        Typical accessor for the row instance variable.
         */
        return row;
    }

    /**
    * Standard getter for the column of the BoardPosition
    * @return the column of the BoardPosition as an int
    * @pre None
    * @post getColumn = column AND row = #row AND column = #column
    */
    public int getColumn() {
        /*
        Typical accessor for the column instance variable.
         */
        return column;
    }

    /**
     * Function for adding two board positions together.
     *
     * @param posOne first instance of BoardPosition
     * @param posTwo second instance of BoardPosition
     * @return a new BoardPosition containing a row equal to the sum of
     * both parameters' rows, and a column equal to the sum of both
     * parameters' columns.
     * @pre posOne != null AND posTwo != null
     * @post add = [a new BoardPosition object that has rows and columns 
     * equal to the sum of both parameters rows and columns, respectively]
     */
    public static BoardPosition add(BoardPosition posOne, BoardPosition posTwo) {
        /*
        A STATIC function for adding two BoardPositions together, passed in via parameter. This function should return
        a new BoardPosition that contains a row equal to the sum of both parameters' rows, and a column equal to the sum
        of both parameters' columns.
         */
        
        //Calculating the new object's row value
        int rowParameter = posOne.getRow() + posTwo.getRow();

        //Calculating the new object's column value
        int colParameter = posOne.getColumn() + posTwo.getColumn();

        //Creating the new BoardPosition object
        BoardPosition posThree = new BoardPosition(rowParameter, colParameter);

        return posThree;
    }

    /**
     * Function for doubling the row and column of a BoardPosition.
     *
     * @param pos instance of BoardPosition to be doubled
     * @return a new BoardPosition containing a row equal to double the
     * parameter's row, and a column equal to double the parameter's
     * column.
     * @pre pos != null
     * @post doubleBoardPosition = pos AND pos.row = 2 * #row AND pos.column = 2 * #column
     */
    public static BoardPosition doubleBoardPosition(BoardPosition pos) {
        /*
        a STATIC function for returning a new Board Position that has a row and column equal to double the row and
        column of the parameter BoardPosition.
         */

        //Calculating the new object's row value
        int rowParameter = pos.getRow() + pos.getRow();

        //Calculating the new object's column value
        int colParameter = pos.getColumn() + pos.getColumn();

        //Creating the new BoardPosition object
        BoardPosition newPos = new BoardPosition(rowParameter, colParameter);

        return newPos;
    }

    /**
     * Function that verifies if the BoardPosition is within 0 and rowBound
     * and columnBound
     *
     * @param rowBound specifies the maximum row for a BoardPosition
     * @param columnBound specifies the maximum column for a BoardPosition
     * @return true if this BoardPosition falls within 0 and both rowBound and
     * columnBound, false otherwise
     * @pre none
     * @post IF [BoardPosition is within bounds of 0 and rowbound and columnBound parameter] isValid = true
     * ELSE isValid = false AND row = #row AND column = #column
     */
    public boolean isValid(int rowBound, int columnBound) {
        /*
        returns true or false depending on if this BoardPosition is within the bounds of 0 and rowBound and columnBound
        parameter.
         */
        if ((row >= 0 && row < rowBound) && (column >= 0 && column < columnBound))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Functions that checks if the BoardPosition is equal to the
     * parameter object
     * @param obj instance of BoardPosition of type Object
     * @return true if BoardPosition is equal to obj, false otherwise
     * @pre obj != null
     * @post IF obj == null THEN equals = false
     * IF !(obj instanceof BoardPosition other) THEN equals = false
     * ELSE equals = true AND row = #row AND column = #column
     */
    public boolean equals(Object obj) {
        /*
        returns true if this BoardPosition is equal to the parameter object. Two BoardPositions are equal if their row
        and column values are the same.

        hint: it is intentional that this accepts a parameter of type Object. There is a way to check if that parameter
        Object just happens to be an instance of a BoardPosition.
         */

        boolean same = false;
        
        if (obj instanceof BoardPosition)
        {
            if (((BoardPosition) obj).column == this.column && ((BoardPosition) obj).row == this.row)
            {
                same = true;
            }
        }
        
        return same;
            
    }

    /**
    * Standard toString for a BoardPosition
    * @return a String representation of the BoardPosition in the format of "row,column"
    * @pre None
    * @post toString = [a String representation of the BoardPosition in
    * the format of "row, column] AND row = #row AND column = #column
    */
    public String toString() {
        /*
        returns a String representation of the BoardPosition in the format of "row,column"
         */
       return row + "," + column;
    }

}
