package cpsc2150.extendedCheckers.models;

import cpsc2150.extendedCheckers.util.DirectionEnum;
import cpsc2150.extendedCheckers.views.CheckersFE;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The generic interface for our 2-dimensional checkerboard grid of characters.
 * Indexing starts at 0
 *
 * @initialization_ensures:
 *      Grid only contains blank characters, PLAYER_ONE and PLAYER_TWO characters and BLACK_TILE characters
 *      Grid is CheckerBoard.getRowNum() by CheckerBoard.getColNum()
 *      Each player has STARTING_COUNT number of pieces
 *      Each player will have pieces lining the white pieces of the first STARTING_COUNT / (CheckerBoard.getColNum() / NUM_PLAYERS) rows
 *      Each player will have a starting set of directions that they are able to move in
 *
 * @defines:
 *      checker_board: the checkerboard grid
 *      piece_count: relates the player with their number of remaining tokens
 *      viable_directions: relates the player with all the viable directions it can move in
 *      rows: number of rows on the board
 *      columns: number of columns on the board
 * 
 * @constraints:
 *      checker_board is a CheckerBoard.getRowNum() by CheckerBoard.getColNum() grid
 *      piece_count is not empty
 *      viable_directions is not empty
 *      rows > 0
 *      columns > 0
 */
public interface ICheckerBoard {


    /**
     * Denotes a piece for player one on the board.
     */
    public static final char PLAYER_ONE = CheckersFE.getPlayerOne();

    /**
     * Denotes a king piece for player one on the board.
     */
    public static final char PLAYER_ONE_KING = CheckersFE.getPlayerOneKing();

    /**
     * Denotes a piece for player two on the board.
     */
    public static final char PLAYER_TWO = CheckersFE.getPlayerTwo();

    /**
     * Denotes a king piece for player two on the board.
     */
    public static final char PLAYER_TWO_KING = CheckersFE.getPlayerTwoKing();

    /**
     * Denotes a position on the board not occupied by either player.
     */
    public static final char EMPTY_POS = ' ';

    /**
     * Denotes a black tile on the board.
     */
    public static final char BLACK_TILE = '*';

    /*
    Standard Checkers starts with 8 rows and 8 columns. Both players begin with 12 pieces each.
     */

    /**
     * Standard getter for viableDirections of a checkerBoard
     * @return the viableDirections of a checkerBoard, as a hashmap of characters and an arrayList of directionEnums
     * @pre None
     * @post getViableDirections = viableDirections AND checker_board = #checker_board
     * AND piece_count = #piece_count AND viable_directions = #viable_directions
     */
    public HashMap<Character, ArrayList<DirectionEnum>> getViableDirections();


    /**
     * Function that maps players to the proper direction they can move in, modeled by DirectionEnum
     * @param player represents a player piece, either standard or king, to be mapped to the possible directions
     * @param dir all possible directions that player could be mapped to
     * @pre player = (PLAYER_ONE OR PLAYER_TWO) and dir in DirectionEnum
     * @post checker_board = #checker_board AND piece_count = #piece_count
     * AND viable_directions = [updated dictionary to reflect the added direction]
     */
    default public void addViableDirections(char player, DirectionEnum dir){
        // Retrieve current viable directions for player
        HashMap<Character, ArrayList<DirectionEnum>> viableDirections = getViableDirections();

        // Determine valid directions based on player type
        ArrayList<DirectionEnum> validDirections = new ArrayList<>();
        if (player == PLAYER_ONE){
            validDirections.add(DirectionEnum.NE);
            validDirections.add(DirectionEnum.NW);
        }
        // Kings can move back and forth
        if (player == PLAYER_ONE_KING || player == PLAYER_TWO_KING){
            validDirections.add(DirectionEnum.NE);
            validDirections.add(DirectionEnum.NW);
            validDirections.add(DirectionEnum.SE);
            validDirections.add(DirectionEnum.SW);
        }
        if (player == PLAYER_TWO){
            validDirections.add(DirectionEnum.SE);
            validDirections.add(DirectionEnum.SW);
        }

        // Check if direction is valid for player
        if (validDirections.contains(dir)){
            // Check if player already is in the viableDirections map
            if (viableDirections.containsKey(player)){
                // If player exists, add new direction to list of viable directions
                viableDirections.get(player).add(dir);
            }
            else{
                // If player doesn't exist, create new list with direction and add to map
                ArrayList<DirectionEnum> directions = new ArrayList<>();
                directions.add(dir);
                viableDirections.put(player, directions);
            }
        }
    }

    /**
     * Function that returns the hashmap that maps the pieces remaining per player (pieceCount)
     * @return pieceCount, a dictionary mapping a player (char) and their pieces remaining (int) in the form of a HashMap
     * @pre None
     * @post getPieceCounts = piece_count
     * AND checker_board = #checker_board AND getColNum = columns AND viable_directions = #viable_directions
     */
    public HashMap<Character, Integer> getPieceCounts();

    /**
     * Function that accesses the board and returns what is at a position.
     * @param pos position to be modified in the array, BoardPosition object type
     * @return value of the char at the index provided as a parameter
     * @pre 0 <= pos.row < ROW_NUM AND 0 <= pos.column < COL_NUM
     * @post checker_board = #checker_board AND piece_count = #piece_count AND
     * viable_directions = [viable_directions updated to reflect new piece pos] AND
     * whatsAtPos = [value of the char at the provided index] AND checker_board = #checker_board AND
     * piece_count = #piece_count AND viableDirections = #viableDirections
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * Function that gets the number of rows a checkerBoard is made of
     * @return the number of rows the board is made of as an int
     * @pre None
     * @post getRowNum = rows AND checker_board = #checker_board
     * AND piece_count = #piece_count AND viable_directions = #viable_directions
     */
    public int getRowNum();

    /**
     * Function that gets the number of columns a checkerBoard is made of
     * @return the number of columns a board is made of as an int
     * @pre None
     * @post getColNum = columns AND checker_board = #checker_board
     * AND piece_count = #piece_count AND viable_directions = #viable_directions
     */
    public int getColNum();

    /**
     * Function that modifies the board array to change a given position character
     * @param pos position to be modified in the array, BoardPosition object type
     * @param player the char value of the player for the value of the piece to be added
     * @pre player = (PLAYER_ONE OR PLAYER_TWO)
     * @pre 0 <= pos.row < rows AND 0 <= pos.column < columns
     * @post checker_board = [original checker_board with index of pos modified to be equal to player
     * AND piece_count = #piece_count AND viable_directions = [viable_directions updated to reflect new piece pos]
     */
    public void placePiece(BoardPosition pos, char player);

    /**
     * Function that states true or false if a player has won the game of checkers, happening when a
     * only one player has remaining pieces on the board.
     * @param player the char value of the player for the win to be checked
     * @return True of false for if the given player has won as a boolean
     * @pre player = (PLAYER_ONE OR PLAYER_TWO)
     * @post checker_board = #checker_board AND piece_count = #piece_count
     * AND viable_directions = #viable_directions
     * AND checkPlayerWin = [true if all pieces remaining on the board belong to the player, false if all remaining
     * pieces do not belong to the player]
     */
    default public boolean checkPlayerWin(Character player) {
        if (player == PLAYER_TWO) {
            return (getPieceCounts().get(PLAYER_ONE) == 0);
        } else {
            return (getPieceCounts().get(PLAYER_TWO) == 0);
        }
    }

    /**
     * Function that modifies the character to uppercase version if piece reaches a position on the board
     * @param posOfPlayer is a given board position that's piece is to be modified
     * @pre 0 <= posOfPlayer.row < CheckerBoard.getRowNum() AND (posOfPlayer.column = 0 OR posOfPlayer = CheckerBoard.getColNum() - 1)
     * @post checker_board = [Board with piece at posOfPlayer converted to uppercase]
     * AND piece_count = [updated version of piece_count depending on which player was crowned]
     * AND viable_directions = #viable_directions
     */
    default public void crownPiece(BoardPosition posOfPlayer){
        char piece = whatsAtPos(posOfPlayer);
        if (piece == PLAYER_ONE)
        {
            placePiece(posOfPlayer, PLAYER_ONE_KING);
        } else if (piece == PLAYER_TWO)
        {
            placePiece(posOfPlayer, PLAYER_TWO_KING);
        }

    }

    /**
     * This function moves a piece on the board by using the startingPos and then using the DirectionEum to specify
     * which direction to move to.
     * @param startingPos position of piece to be modified and moved
     * @param dir direction to be moved with the enum
     * @return returns BoardPosition value of the destination of the piece moved
     * @pre 0 <= startingPos.row < this.getRowNum() AND 0 <= startingPos.column < this.getColNum()
     * AND dir!=null
     * AND [the piece at startingPos is a player character]
     * AND [the piece at the desired position to move is a valid and empty square]
     * AND [the selected character can move in dir (not backwards for a non-king)]
     * @post checker_board = [the board modified to have moved the piece in the given direction of the dir enum]
     * AND piece_count = #piece_count AND viable_directions = [viable_directions updated to reflect new piece pos]
     * movePiece = [the new position where the piece has moved]
     */
    default public BoardPosition movePiece(BoardPosition startingPos, DirectionEnum dir){
        char piece = whatsAtPos(startingPos);

        //figure out aRow and aColumn change based on dir
        int rowPlus = 0;
        int colPlus = 0;
        if (dir == DirectionEnum.NE) {
            rowPlus = -1;
            colPlus = 1;
        }
        if (dir == DirectionEnum.NW) {
            rowPlus = -1;
            colPlus = -1;
        }
        if (dir == DirectionEnum.SE) {
            rowPlus = 1;
            colPlus = 1;
        }
        if (dir == DirectionEnum.SW) {
            rowPlus = 1;
            colPlus = -1;
        }
        int aRow = startingPos.getRow() + rowPlus;
        int aColumn = startingPos.getColumn() + colPlus;

        BoardPosition goal = new BoardPosition(aRow, aColumn);
        placePiece(goal, piece);
        placePiece(startingPos, EMPTY_POS);
        if (goal.getRow() == this.getRowNum() - 1 || goal.getRow() == 0) {
            crownPiece(goal);
        }
        return goal;

    }

    /**
     * Function that moves a piece on the board two positions in the direction passed,thus jumping a piece.
     * The function then removes the jumped piece.
     * @param startingPos position of piece to be modified and moved
     * @param dir direction to be moved with the enum
     * @return returns BoardPosition value of the destination of the piece moved
     * @pre 0 <= startingPos.row < this.getRowNum() AND 0 <= startingPos.column < this.getColNum()
     * AND dir!=null AND [the piece at startingPos is a player character]
     * AND [the piece to the direction of starting position is on the other team]
     * AND [the piece at the destination position is on the board and an empty position]
     * AND [the selected character can move in dir (not backwards for a non-king)]
     * @post checker_board = [the checker_board modified to have moved the piece in the given direction of the dir enum
     * and the removed jumped piece]
     * AND piece_count = [modified to removed one piece from the enemy]
     * AND viable_directions = [viable_directions updated to reflect new piece pos]
     * AND jumpPiece = [the new position where the piece has moved]
     */
    default public BoardPosition jumpPiece(BoardPosition startingPos, DirectionEnum dir){
        // Starting position
        char piece = whatsAtPos(startingPos);

        //figure out aRow and aColumn change based on dir
        int rowFinal = 0;
        int colFinal = 0;

        //figure out position of potential piece getting jumped
        int colJump = 0;
        int rowJump = 0;

        if (dir == DirectionEnum.NE) {
            rowFinal = -2;
            colFinal = 2;

            rowJump = -1;
            colJump = 1;
        }
        if (dir == DirectionEnum.NW) {
            rowFinal = -2;
            colFinal = -2;

            rowJump = -1;
            colJump = -1;
        }
        if (dir == DirectionEnum.SE) {
            rowFinal = 2;
            colFinal = 2;

            rowJump = 1;
            colJump = 1;
        }
        if (dir == DirectionEnum.SW) {
            rowFinal = 2;
            colFinal = -2;

            rowJump = 1;
            colJump = -1;
        }

        // Getting row and column positions for piece jumping goal
        int finalPosRow = startingPos.getRow() + rowFinal;
        int finalPosCol = startingPos.getColumn() + colFinal;

        // Getting row and column positions for piece being jumped
        int jumpedRow = startingPos.getRow() + rowJump;
        int jumpedCol = startingPos.getColumn() + colJump;

        // Create boardPositions for both of the pieces in question
        BoardPosition goal = new BoardPosition(finalPosRow, finalPosCol);
        BoardPosition jumpGoal = new BoardPosition(jumpedRow, jumpedCol);

        // Check what the piece being jumped is and setting what the opposing player should be
        char opposingPlayer;
        if (piece == PLAYER_ONE || piece == PLAYER_ONE_KING) {
            opposingPlayer = PLAYER_TWO;
        } else {
            opposingPlayer = PLAYER_ONE;
        }


        placePiece(goal, piece);
        placePiece(startingPos, EMPTY_POS);
        placePiece(jumpGoal, EMPTY_POS);
        // Update opponents piece count
        playerLostPieces(1, opposingPlayer, getPieceCounts());

        if (goal.getRow() == this.getRowNum() - 1 || goal.getRow() == 0) {
            crownPiece(goal);
        }


        return goal;
    }

    /**
     * Function subtracts numPieces amount of tokens from the pieceCounts HashMap.
     * @param numPieces the int quantity of pieces to be removed
     * @param player the char value of the player whose piece count will be modified
     * @param pieceCounts passing the dictionary hashmap that tracks the player and corresponding piece count
     * @pre 0 < numPieces < pieceCounts[player] AND player = (PLAYER_ONE OR PLAYER_TWO)
     * @post piece_count = [modified to removed numPieces count of pieces from the player]
     * AND checker_board = #checker_board AND viable_directions = #viable_directions
     */
    default public void playerLostPieces(int numPieces, char player, HashMap<Character, Integer> pieceCounts){
        // Get the current count of pieces for a player
        int currPieceCount = pieceCounts.get(player);
        // Decrease the count of pieces for the player
        currPieceCount -= numPieces;
        // Update the count of pieces for the player in the HashMap
        pieceCounts.put(player, currPieceCount);
    }

    /**
     * Function that scans all positions surrounding startingPos parameter
     * @param startingPos individual BoardPosition representing the starting point of a potential movement
     * @return returns a HashMap where each direction surrounding the specified startingPos is mapped to a
     * character representing the state of the tile it points to
     * @pre 0 <= startingPos.row < this.getRowNum() AND 0 <= startingPos.column < this.getColNum()
     * @post checker_board = #checker_board AND piece_count = #piece_count
     * AND viable_directions = #viable_directions
     * AND scanSurroundingPositions = [HashMap were each direction surrounding a specific startingPos is mapped]
     */
    default public HashMap<DirectionEnum, Character> scanSurroundingPositions(BoardPosition startingPos){
        HashMap<DirectionEnum, Character> surrounding_pos = new HashMap<DirectionEnum, Character>();
        int row = startingPos.getRow();
        int col = startingPos.getColumn();

        //NE scan
        if(row > 0 && col < this.getRowNum()){
            BoardPosition add_pos = getDirection(DirectionEnum.NE);
            BoardPosition moved_position = BoardPosition.add(startingPos, add_pos);
            if (moved_position.isValid(this.getRowNum(), this.getColNum())) {
                char pos_char = whatsAtPos(moved_position);
                surrounding_pos.put(DirectionEnum.NE, pos_char);
            }
        }

        //NW scan
        if(row > 0 && col < this.getRowNum()) {
            BoardPosition add_pos = getDirection(DirectionEnum.NW);
            BoardPosition moved_position = BoardPosition.add(startingPos, add_pos);
            if (moved_position.isValid(this.getRowNum(), this.getColNum())) {
                char pos_char = whatsAtPos(moved_position);
                surrounding_pos.put(DirectionEnum.NW, pos_char);
            }

        }
        //SE scan
        if(row >= 0 && col < this.getRowNum()){
            BoardPosition add_pos = getDirection(DirectionEnum.SE);
            BoardPosition moved_position = BoardPosition.add(startingPos, add_pos);
            if (moved_position.isValid(this.getRowNum(), this.getColNum())) {
                char pos_char = whatsAtPos(moved_position);
                surrounding_pos.put(DirectionEnum.SE, pos_char);
            }
        }

        //SW scan
        if(row >= 0 && col < this.getRowNum()){
            BoardPosition add_pos = getDirection(DirectionEnum.SW);
            BoardPosition moved_position = BoardPosition.add(startingPos, add_pos);
            if (moved_position.isValid(this.getRowNum(), this.getColNum())) {
                char pos_char = whatsAtPos(moved_position);
                surrounding_pos.put(DirectionEnum.SW, pos_char);
            }
        }
        return surrounding_pos;
    }

    /**
     * Function that creates a new BoardPosition based on a movement in the direction of dir parameter. It adds or subtracts
     * values from the row and column position to indicate the change in position.
     * @param dir direction for movement of piece
     * @return a new BoardPosition that can be added to any piece so that it moves one position in the
     * direction of dir
     * @pre dir in DirectionEnum
     * @post getDirection = [new BoardPosition which can be added to any piece so that it moves in the direction of dir]
     */
    public static BoardPosition getDirection(DirectionEnum dir){
        return switch (dir) {
            case NE -> new BoardPosition(-1, 1);
            case NW -> new BoardPosition(-1, -1);
            case SE -> new BoardPosition(1, 1);
            case SW -> new BoardPosition(1, -1);
        };
    }

}
