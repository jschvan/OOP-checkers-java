package cpsc2150.extendedCheckers.models;

/**
 * This abstract class provides the toString() method to be overridden, but contains no private data.
 */
public abstract class AbsCheckerBoard implements ICheckerBoard {

    /**
     * Standard toString for a CheckerBoard
     * @return a String summary of the checkerBoard displaying its contents
     * @pre None
     * @post toString = [a string representation of the checkerboard with all
     * the pieces on it and their current positions, with a header line to display all the column numbers
     * and a header column to display all the row numbers]
     */
    public String toString()
    {
        /*
        returns a String representation of the checkerboard with all the pieces on it and their current positions. there
        should be a "header" line to display all the column numbers and a "header column" that displays all the row
        numbers. In essence, it should look like this:

        |  | 0| 1| 2| 3| 4| 5| 6| 7|
        |0 |x |* |x |* |x |* |x |* |
        |1 |* |x |* |x |* |x |* |x |
        |2 |x |* |x |* |x |* |x |* |
        |3 |* |  |* |  |* |  |* |  |
        |4 |  |* |  |* |  |* |  |* |
        |5 |* |o |* |o |* |o |* |o |
        |6 |o |* |o |* |o |* |o |* |
        |7 |* |o |* |o |* |o |* |o |

        THIS FUNCTION DOES NOT PRINT TO THE CONSOLE OR MAKE ANY KIND OF SYSTEM.OUT.PRINTLN CALLS
         */
        String retValue = "";

        //Add the header line
        retValue = retValue.concat("|  | ");
        for (int i = 0; i < this.getColNum(); i++)
        {
            if (i == this.getColNum()-1){
                retValue = retValue.concat(i + "|");
            }
            else if(i < 9) {
                retValue = retValue.concat(i + "| ");
            }
            else{
                retValue = retValue.concat(i + "|");
            }
        }

        //Add the body
        for (int i = 0; i < this.getRowNum(); i++)
        {
            if (i < 10) {
                retValue = retValue.concat("\n|" + i + " ");
            }
            else{
                retValue = retValue.concat("\n|" + i);
            }
            for (int j = 0; j < this.getColNum(); j++)

            {
                if (j == 0) {
                    BoardPosition bp = new BoardPosition(i, j);
                    char pos = this.whatsAtPos(bp);

                    retValue = retValue.concat("|" + pos);
                }
                else{
                    BoardPosition bp = new BoardPosition(i, j);
                    char pos = this.whatsAtPos(bp);

                    retValue = retValue.concat(" |" + pos);
                }
            }
            retValue = retValue.concat(" |");
        }
        retValue = retValue.concat("\n");

        return retValue;

    }
}
