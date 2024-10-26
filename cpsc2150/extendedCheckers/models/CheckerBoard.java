package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;
import cpsc2150.extendedCheckers.views.CheckersFE;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the checkerboard itself. It contains the location and
 * color of each square, as well as the size of the board.
 * @invariant board is a ROW_NUM by COL_NUM grid
 * @invariant pieceCount is not empty
 * @invariant viableDirections is not empty
 *
 * Correspondence checker_board = board
 * Correspondence piece_count = pieceCount
 * Correspondence viable_directions = viableDirections
 * Correspondence rows = ROW_NUM
 * Correspondence columns = COL_NUM
 */
public class CheckerBoard extends AbsCheckerBoard
{


    /**
     * A 2D array of characters used to represent our checkerboard.
     */
    private char[][] board;

    /**
     * A HashMap, with a Character key and an Integer value, that is used to map a player's char to the number of
     * tokens that player still has left on the board.
     */
    private HashMap<Character, Integer> pieceCount;

    /**
     * A HashMap, with a Character key and an ArrayList of DirectionEnums value, used to map a player (and its king
     * representation) to the directions that player can viably move in. A non-kinged (standard) piece can only move
     * in the diagonal directions away from its starting position. A kinged piece can move in the same directions the
     * standard piece can move in plus the opposite directions the standard piece can move in.
     */
    private HashMap<Character, ArrayList<DirectionEnum>> viableDirections;

    /**
     * Represents the number of columns on the board.
     */
    private final int ROW_NUM;

    /**
     * Represents the number of columns on the board.
     */
    private final int COL_NUM;

    /**
     * Represents the number of pieces each player starts with.
     */
    private final int STARTING_COUNT;

    /**
     * Standard Constructor for a CheckerBoard object, constructs a CheckerBoard object that represents
     * the standard game board for checkers.
     * @param aDimension the integer dimension of the board used in the construction
     * @pre aDimension = [one of: (8 10, 12, 14, 16)]
     * @Post viableDirections = [viableDirections for all starting positions]
     * AND pieceCount = [pieceCount with starting token count mapped to both players]
     * AND board = [board with all indices representing the color, playability, or emptiness of each tile]
     */
    public CheckerBoard(int aDimension) {
        /*
        Constructor for the CheckerBoard object. The constructor should initialize the three instance variables to
        a new data structure of their respective type. Furthermore, the constructor should use the pieceCount HashMap
        to map the starting count of tokens to each player, and use the viableDirections HashMap to map the players to
        their respective starting directions (SE and SW for player one, NE and NW for player two). Finally, the
        constructor should also initialize all the indices within the checkerboard to either a player char, an asterisk
        (a 'black, non-playable' position), or a space (the 'empty' position)
         */

        //Initializing the board and all the positions
        //This constructor doesn’t check if we have used all the pieces from starting_piece_count, but it
        //relies on the number of starting pieces, and the number of rows and columns to fill the board
        //However, for certain ratios between Starting_piece_count and COL_NUM, the pieces get //messed up,
        // only if (starting_piece_count/ (COL_NUM/2) is not a whole number or is <1

        ROW_NUM = aDimension;
        COL_NUM = aDimension;

        board = new char[ROW_NUM][COL_NUM];

        final char PLAYER_ONE = CheckersFE.getPlayerOne();
        final char PLAYER_ONE_KING = CheckersFE.getPlayerOneKing();
        final char PLAYER_TWO = CheckersFE.getPlayerTwo();
        final char PLAYER_TWO_KING = CheckersFE.getPlayerTwoKing();

        final int NUM_PLAYERS = 2;

        //Defining constants for these rows so if STARTING_PIECE_COUNT or COL_NUM changes, this constructor can stay the same
        //no mans land refers to the space in between player1 and player2

        final int NO_MANS_LAND_NUM_ROWS = 2;
        final int PLAYER1_NUM_ROWS = (aDimension - NO_MANS_LAND_NUM_ROWS)/NUM_PLAYERS;
        final int PLAYER2_NUM_ROWS = (aDimension - NO_MANS_LAND_NUM_ROWS)/NUM_PLAYERS;

        //is this magic number? to use NUM_PLAYERS would be innacurate
        //the goal of this statement is generate a number equal to every other square in a players area\
        //do we need a constant to halve?
        STARTING_COUNT = PLAYER1_NUM_ROWS * aDimension/2;



        //Creating an array that contains whether rows are player1, player2, or
        //no mans land. This will allow us to use an if-statement with equals condition
        //to quickly decide which player piece to use
        final int PLAYER1_ROW_SPECIFIER = 1;
        final int PLAYER2_ROW_SPECIFIER = 2;
        final int NO_MANS_LAND_SPECIFIER = 3;

        int[] rowDecider = new int[ROW_NUM];
        int k = 0;
        for (; k < PLAYER1_NUM_ROWS; k++) {
            rowDecider[k] = PLAYER1_ROW_SPECIFIER;
        }
        for (; k < PLAYER1_NUM_ROWS + NO_MANS_LAND_NUM_ROWS; k++) {
            rowDecider[k] = NO_MANS_LAND_SPECIFIER;
        }
        for (; k < PLAYER1_NUM_ROWS + NO_MANS_LAND_NUM_ROWS + PLAYER2_NUM_ROWS; k++) {
            rowDecider[k] = PLAYER2_ROW_SPECIFIER;
        }

        //Row Decider should now contain an array of ints with the index
        // correlating to the row number, and the int correlating to which player's
        //pieces belong on that row, or no mans land
        //Placing the correct pieces in the correct board locations
        for (int i = 0; i < ROW_NUM; i++) {
            for (int j = 0; j < COL_NUM; j++) {
                //All odd tiles are black tiles, while evens are playable
                if ((i + j) % 2 == 1) {
                    board[i][j] = BLACK_TILE;
                } else {
                    if (rowDecider[i] == PLAYER1_ROW_SPECIFIER) {
                        board[i][j] = PLAYER_ONE;
                    }
                    if (rowDecider[i] == PLAYER2_ROW_SPECIFIER) {
                        board[i][j] = PLAYER_TWO;
                    }
                    if (rowDecider[i] == NO_MANS_LAND_SPECIFIER) {
                        board[i][j] = EMPTY_POS;
                    }
                }
            }
        }

            //Indentation is off here but I can't seem to fix it

            //Now Creating the viableDirections hashmap
            viableDirections = new HashMap<>();

            //Creating the viable directions for Player one
            ArrayList<DirectionEnum> playerOneDirections = new ArrayList<DirectionEnum>();
            playerOneDirections.add(DirectionEnum.SE);
            playerOneDirections.add(DirectionEnum.SW);
            viableDirections.put(PLAYER_ONE, playerOneDirections);


            //Creating the viable directions for Player two
            ArrayList<DirectionEnum> playerTwoDirections = new ArrayList<DirectionEnum>();
            playerTwoDirections.add(DirectionEnum.NW);
            playerTwoDirections.add(DirectionEnum.NE);
            viableDirections.put(PLAYER_TWO, playerTwoDirections);


            //Creating the viable directions for both players king pieces
            ArrayList<DirectionEnum> kingDirections = new ArrayList<DirectionEnum>();
            kingDirections.add(DirectionEnum.SE);
            kingDirections.add(DirectionEnum.SW);
            kingDirections.add(DirectionEnum.NW);
            kingDirections.add(DirectionEnum.NE);
            viableDirections.put(PLAYER_ONE_KING, kingDirections);
            viableDirections.put(PLAYER_TWO_KING, kingDirections);

            //Viable Directions should now contain a hashmap with all the characters
            //and the viable directions for each character


            //Create the pieceCount hashmap
            pieceCount = new HashMap<>();
            pieceCount.put(PLAYER_ONE, STARTING_COUNT);
            pieceCount.put(PLAYER_TWO, STARTING_COUNT);


    }


    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections() {
        return viableDirections;
    }


    public HashMap<Character, Integer> getPieceCounts() {
        return pieceCount;
    }

    public int getRowNum() {
        return ROW_NUM;
    }


    public int getColNum() {
        return COL_NUM;
    }


    public char whatsAtPos(BoardPosition pos) {
        /*
        an "accessor" for the board 2D array. Returns what is at the position given by the row and col of the BoardPosition
        parameter.
         */
        return board[pos.getRow()][pos.getColumn()];
    }

    public void placePiece(BoardPosition pos, char player) {
        /*
        A "mutator" for the board 2D array. This should be used for setting a given 2D index within the board 2D array,
         given by the row and col of the parameter BoardPosition, equal to the char given by player.
         */
        board[pos.getRow()][pos.getColumn()] = player;
    }

}
