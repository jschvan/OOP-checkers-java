package cpsc2150.extendedCheckers.models;
import cpsc2150.extendedCheckers.util.DirectionEnum;
import cpsc2150.extendedCheckers.views.CheckersFE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides a memory conscious version of the checkers game by using a map to store board information. ADD MORE?
 * @invariant pieceCount is not empty
 * @invariant viableDirections is not empty
 *
 * Correspondence checker_board = board
 * Correspondence piece_count = pieceCount
 * Correspondence viable_directions = viableDirections
 * Correspondence rows = ROW_NUM
 * Correspondence columns = COL_NUM
 */
public class CheckerBoardMem extends AbsCheckerBoard {


    /**
     * Represents the number of rows on the board.
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

    Map<Character, List<BoardPosition>> board = new HashMap<Character, List<BoardPosition>>();

    /**
     * Constructor Description
     * @param aDimension int representing both the row and column sizes for the checkerboard
     * @pre aDimension = [one of: (8 10, 12, 14, 16)]
     * @post viableDirections = [viableDirections for all starting positions]
     * AND pieceCount = [pieceCount with starting token count mapped to both players]
     * AND board = [hashmap containing a key of characters and their starting positions]
     */
    public CheckerBoardMem(int aDimension){
        ROW_NUM = aDimension;
        COL_NUM = aDimension;

        final int NUM_PLAYERS = 2;

        final char PLAYER_ONE = CheckersFE.getPlayerOne();
        final char PLAYER_ONE_KING = CheckersFE.getPlayerOneKing();
        final char PLAYER_TWO = CheckersFE.getPlayerTwo();
        final char PLAYER_TWO_KING = CheckersFE.getPlayerTwoKing();

        //Defining constants for these rows so if STARTING_PIECE_COUNT or COL_NUM changes, this constructor can stay the same
        //no mans land refers to the space in between player1 and player2

        final int NO_MANS_LAND_NUM_ROWS = 2;
        final int PLAYER1_NUM_ROWS = (aDimension - NO_MANS_LAND_NUM_ROWS)/NUM_PLAYERS;
        final int PLAYER2_NUM_ROWS = (aDimension - NO_MANS_LAND_NUM_ROWS)/NUM_PLAYERS;

        STARTING_COUNT = PLAYER1_NUM_ROWS * aDimension/2;



        //add PLAYER_ONE pieces
        List<BoardPosition> p1_list = new ArrayList<BoardPosition>();
        for(int i = 0; i < PLAYER1_NUM_ROWS; i++){
            for(int j = 0; j < COL_NUM; j++){
                if((i + j)%2 == 0) {
                    BoardPosition addBP = new BoardPosition(i, j);
                    p1_list.add(addBP);
                }
            }
        }

        board.put(PLAYER_ONE, p1_list);

        //add PLAYER_ONE_KING pieces (none for the start)
        List<BoardPosition> p1k_list = new ArrayList<BoardPosition>();
        board.put(PLAYER_ONE_KING, p1k_list);

        //add PLAYER_TWO pieces
        List<BoardPosition> p2_list = new ArrayList<BoardPosition>();
        for(int i = NO_MANS_LAND_NUM_ROWS + PLAYER1_NUM_ROWS; i < ROW_NUM; i++){
            for(int j = 0; j < COL_NUM; j++){
                if((i + j)%2 == 0) {
                    BoardPosition addBP = new BoardPosition(i, j);
                    p2_list.add(addBP);
                }
            }
        }
        board.put(PLAYER_TWO, p2_list);

        //add PLAYER_TWO_KING pieces (none for the start)
        List<BoardPosition> empty_p2k_list = new ArrayList<BoardPosition>();
        board.put(PLAYER_TWO_KING, empty_p2k_list);


        //ALL COPIED FROM ORIGINAL CheckerBoard CONSTRUCTOR
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
        for(BoardPosition bp : board.get(PLAYER_ONE)){
            if(bp.equals(pos)){
                return PLAYER_ONE;
            }
        }

        for(BoardPosition bp : board.get(PLAYER_ONE_KING)){
            if(bp.equals(pos)){
                return PLAYER_ONE_KING;
            }
        }

        for(BoardPosition bp : board.get(PLAYER_TWO)){
            if(bp.equals(pos)){
                return PLAYER_TWO;
            }
        }

        for(BoardPosition bp : board.get(PLAYER_TWO_KING)){
            if(bp.equals(pos)){
                return PLAYER_TWO_KING;
            }
        }
        if((pos.getColumn() + pos.getRow())%2 == 1){
            //MAKE CONSTANT
            return '*';
        }
        //MAKE CONSTANT
        return ' ';
    }

    public void placePiece(BoardPosition pos, char player) {
        char past_player = whatsAtPos(pos);
        if(past_player != EMPTY_POS && past_player != BLACK_TILE){
            board.get(past_player).remove(pos);
        }
        if(player != EMPTY_POS && player != BLACK_TILE) {
            board.get(player).add(pos);
        }

    }


}

