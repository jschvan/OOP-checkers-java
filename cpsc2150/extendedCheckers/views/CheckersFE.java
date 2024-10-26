package cpsc2150.extendedCheckers.views;

import cpsc2150.extendedCheckers.models.BoardPosition;
import cpsc2150.extendedCheckers.models.CheckerBoardMem;
import cpsc2150.extendedCheckers.models.CheckerBoard;
import cpsc2150.extendedCheckers.models.ICheckerBoard;
import cpsc2150.extendedCheckers.util.DirectionEnum;

import java.util.*;

import static cpsc2150.extendedCheckers.models.ICheckerBoard.EMPTY_POS;


/**
 * This is the class that controls the flow of the game including the alternating of players
 * and prompting for inputs. It creates a new CheckerBoard object, and runs the game on it
 * until it is won. It contains methods to check that positions are valid, take in user input,
 * and check that directions are valid.
 *
 * @invariant P1_TURN is true OR P2_TURN is true
 */
public class CheckersFE
{
    public static final boolean P1_TURN = true;
    public static final boolean P2_TURN = false;


    private static char PLAYER_ONE = 'x';
    private static char PLAYER_TWO = 'o';

    private static char PLAYER_ONE_KING = 'X';
    private static char PLAYER_TWO_KING = 'O';

    private static final List<Integer> VALID_BOARD_SIZES = Arrays.asList(8, 10, 12, 14, 16);
    
    /**
     * Function to return char representing player one token
     * @return char representing player one token
     * @pre none
     * @post getPlayerOne = PLAYER_ONE
     */
    public static char getPlayerOne()
    {
        return PLAYER_ONE;
    }

    /**
     * Function to return char representing player two token
     * @return char representing player two token
     * @pre none
     * @post getPlayerTwo = PLAYER_TWO
     */
    public static char getPlayerTwo()
    {
        return PLAYER_TWO;
    }

    /**
     * Function to return char representing player one king token
     * @return char representing player one king token
     * @pre none
     * @post getPlayerOneKing = PLAYER_ONE_KING
     */
    public static char getPlayerOneKing() {
        return PLAYER_ONE_KING;
    }

    /**
     * Function to return char representing player two king token
     * @return char representing player two king token
     * @pre none
     * @post getPlayerTwoKing = PLAYER_TWO_KING
     */
    public static char getPlayerTwoKing() {
        return PLAYER_TWO_KING;
    }

    public static void main(String[] args)
    {

        boolean play_again = true;

        //default value for the dimension of the board
        int dimension = 8;
        Scanner keyboard = new Scanner(System.in);
        String welcome_message = "Welcome to Checkers!";
        System.out.println("Player 1, enter your piece: ");
        PLAYER_ONE = Character.toLowerCase(keyboard.nextLine().charAt(0));
        PLAYER_ONE_KING = Character.toUpperCase(PLAYER_ONE);
        System.out.println("Player 2, enter your piece: ");
        PLAYER_TWO = Character.toLowerCase(keyboard.nextLine().charAt(0));
        PLAYER_TWO_KING = Character.toUpperCase(PLAYER_TWO);
        while (PLAYER_ONE == PLAYER_TWO){
            System.out.println("Select a new piece for player 2: ");
            PLAYER_TWO = Character.toLowerCase(keyboard.nextLine().charAt(0));
            PLAYER_TWO_KING = Character.toUpperCase(PLAYER_TWO);
        }

        //logic to determine fast or memory game
        System.out.println("Do you want a fast game (F/f) or a memory efficient game (M/m)?");
        boolean game_type_loop_control = false;
        ICheckerBoard gameBoard = null;

        //integer to store the game type of the checkerboard. 1 is memory, 2 is speed
        int game_type = 0;
        while(!game_type_loop_control){
            char game_type_char = Character.toLowerCase(keyboard.nextLine().charAt(0));
            if(game_type_char == 'm'){
                game_type = 1;
                game_type_loop_control = true;
            }
            else if(game_type_char == 'f'){
                game_type = 2;
                game_type_loop_control = true;
            }
            else{
                System.out.println("Invalid input, try again.");
            }
        }

        System.out.println("How big should the board be? " +
                "It can be 8x8, 10x10, 12x12, 14x14, or 16x16. Enter one number:");
        boolean size_loop_control = false;
        while(!size_loop_control){
            int boardSizeUserInput = keyboard.nextInt();
            if(VALID_BOARD_SIZES.contains(boardSizeUserInput)){
                dimension = boardSizeUserInput;
                size_loop_control = true;
            }
            else{
                System.out.println("Invalid input, try again.");
            }
        }
        while (play_again == true){

            //now that we have dimension, we can declare the gameboard
            if(game_type == 1){
                gameBoard = new CheckerBoardMem(dimension);
            }

            else if(game_type == 2){
                gameBoard = new CheckerBoard(dimension);
            }


            boolean turn = true;

            boolean win = false;

            System.out.println(welcome_message);
            while(!win) {
                //print board
                String board_string = gameBoard.toString();
                System.out.println(board_string);

                //take in user input and make sure its valid
                BoardPosition boardInput = userInput(turn);
                while (!checkValidPosition(boardInput, gameBoard, turn)) {
                   boardInput = userInput(turn);
                }

                //Lets us know which playerPiece is at the input
                char playerPiece = gameBoard.whatsAtPos(boardInput);
                
                System.out.println("Which direction would you like to move in? (NE,NW,SE,SW): ");
                Scanner scanner = new Scanner(System.in);
                String dirInput = scanner.nextLine().toUpperCase();

                DirectionEnum direction = null;

                while (!isDirection(dirInput)) {
                   System.out.println("Not a direction. Which direction would you like to move in? (NE,NW,SE,SW): ");
                   dirInput = scanner.nextLine().toUpperCase();
               }
               direction = DirectionEnum.valueOf(dirInput);


               while (!checkIfValidMove(boardInput, gameBoard, direction) &&
                       !checkIfValidJump(boardInput, gameBoard, direction)){
                   System.out.print("Invalid direction. Please Chose a new one: ");
                   dirInput = scanner.nextLine().toUpperCase();
                   direction = DirectionEnum.valueOf(dirInput);
               }


               char piece = gameBoard.whatsAtPos(boardInput);
               ArrayList<DirectionEnum> viableDirectionsForPiece = gameBoard.getViableDirections().get(piece);

                // Make sure that the direction selected is viable
               while (!viableDirectionsForPiece.contains(direction))
               {
                   System.out.println("Piece can not move this direction. Enter a new one direction: ");
                   dirInput = scanner.nextLine().toUpperCase();


                   while (!isDirection(dirInput)) {
                       dirInput = scanner.nextLine().toUpperCase();
                   }


                   direction = DirectionEnum.valueOf(dirInput);
               }

               if(checkIfValidMove(boardInput, gameBoard,direction)){
                   gameBoard.movePiece(boardInput,direction);
               }
               if(checkIfValidJump(boardInput,gameBoard,direction)){
                   gameBoard.jumpPiece(boardInput,direction);
               }
                
                win = gameBoard.checkPlayerWin(playerPiece);
                if (win)
                {
                    if (turn)
                    {
                        System.out.println("Player 1 wins!");
                    }
                    else
                    {
                        System.out.println("Player 2 wins!");
                    }
                }

                turn = !turn;
            }

            System.out.println("Enter 1 to play again or 0 to quit.");
            Scanner in = new Scanner(System.in);
            int play_again_input = in.nextInt();

            if(play_again_input != 1) {
                play_again = false;
            }
        }
    }

    /**
     * Function that validates given user input to determine if it is a direction in the direction enum
     * @param value user input given to be checked if it is a value
     * @return the boolean value of if the given input is a valid direction or not
     * @pre none
     * @post isDirection = [true if passed string corresponds to an existing direction in the direction enum and false
     * if not]
     */
    private static boolean isDirection(String value) {
        for (DirectionEnum direction : DirectionEnum.values()) {
            if (direction.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function that checks to see if the passed user position was valid and provides a response if input is invalid
     * @param pos BoardPosition object for position to be checked
     * @param gameBoard ICheckerBoard object that is to be checked as valid or not
     * @param player boolean variable to determine which player is being checked for
     * @return the boolean value of if the given input is a valid board position
     * @pre none
     * @post checkValidPosition = [true if passed BoardPosition is a valid position for the given ICheckerBoard and given
     * player]
     */
    private static boolean checkValidPosition(BoardPosition pos, ICheckerBoard gameBoard, boolean player)
    {
        boolean val = true;

        //Checking if the user entered a position that wasn't on the board
        if (!pos.isValid(gameBoard.getRowNum(), gameBoard.getColNum()))
        {
            System.out.println("Please enter a position on the board.");
            val = false;
            return val;
        }

        //Checking if the user entered a piece that wasn't theirs or an empty position/non-playable tile
        if (player)
        {
            if (gameBoard.whatsAtPos(pos) != PLAYER_ONE && gameBoard.whatsAtPos(pos) != PLAYER_ONE_KING)
            {
                System.out.println("Please chose one of your pieces.");
                val = false;
            }
        }
        else {
            if (gameBoard.whatsAtPos(pos) != PLAYER_TWO && gameBoard.whatsAtPos(pos) != PLAYER_TWO_KING)
            {
                System.out.println("Please chose one of your pieces.");
                val = false;
            }
        }
        if (!checkIfPieceMoves(pos, gameBoard)){
           System.out.println("Select a piece with possible moves.");
           val = false;
           return val;
       }

        return val;
    }
    
    /**
     * Function that checks if the piece specified by its position can legally move or jump.
     * @param pos represents a position of a piece on the checkerboard
     * @param gameBoard represents the entire checkerboard and all pieces on it
     * @return the boolean value of if a piece can move legally
     * @pre none
     * @post checkIfPieceMoves = [true if the pieced specified by pos can legally move, false otherwise]
     */
   private static boolean checkIfPieceMoves(BoardPosition pos, ICheckerBoard gameBoard) {
       HashMap<Character, ArrayList<DirectionEnum>> viableDirections = gameBoard.getViableDirections();
       if (viableDirections.containsKey(gameBoard.whatsAtPos(pos))) {
           for (DirectionEnum dir : viableDirections.get(gameBoard.whatsAtPos(pos))) {
               // Figure out row and column changes based on direction
               int rowMove = 0;
               int colMove = 0;
               int rowJump = 0;
               int colJump = 0;
               switch (dir) {
                   case NE:
                       rowMove = -1;
                       colMove = 1;
                       rowJump = -2;
                       colJump = 2;
                       break;
                   case NW:
                       rowMove = -1;
                       colMove = -1;
                       rowJump = -2;
                       colJump = -2;
                       break;
                   case SE:
                       rowMove = 1;
                       colMove = 1;
                       rowJump = 2;
                       colJump = 2;
                       break;
                   case SW:
                       rowMove = 1;
                       colMove = -1;
                       rowJump = 2;
                       colJump = -2;
                       break;
               }
              // Calculate new position after move
               int newRowMove = pos.getRow() + rowMove;
               int newColMove = pos.getColumn() + colMove;
               // Calculate new position after jump
               int newRowJump = pos.getRow() + rowJump;
               int newColJump = pos.getColumn() + colJump;

               // Create boardPositions for the move goal
               BoardPosition moveGoal = new BoardPosition(newRowMove, newColMove);
               if (moveGoal.isValid(gameBoard.getRowNum(), gameBoard.getColNum()) && gameBoard.whatsAtPos(moveGoal) == EMPTY_POS)
               {
                   return true;
               }

               // Create boardPositions for the jump goal
               BoardPosition jumpGoal = new BoardPosition(newRowJump, newColJump);

               // Make sure that the target position is empty and is a valid move
               if (jumpGoal.isValid(gameBoard.getRowNum(), gameBoard.getColNum()) && gameBoard.whatsAtPos(jumpGoal) == EMPTY_POS) {
                   // Make sure there is a piece to jump over
                   if (gameBoard.whatsAtPos(moveGoal) != EMPTY_POS) {
                       // Check what the piece being jumped is and setting what the opposing player should be
                       char piece = gameBoard.whatsAtPos(pos);
                       char pieceBeingJumped = gameBoard.whatsAtPos(moveGoal);
                       char opposingPlayer;
                       if (piece == PLAYER_ONE || piece == PLAYER_ONE_KING) {
                           opposingPlayer = PLAYER_TWO;
                       }
                       else {
                           opposingPlayer = PLAYER_ONE;
                       }

                       // Make sure that the piece being jumped is an opposing players piece
                       if (pieceBeingJumped == opposingPlayer || pieceBeingJumped == Character.toUpperCase(opposingPlayer)) {
                           return true;
                       }
                   }
               }
           }
       }
       return false;
   }

    /**
     * Function that checks if the piece specified by its position can legally move in a certain direction excluding possible jump moves.
     * @param pos represents a position of a piece on the checkerboard
     * @param gameBoard represents the entire checkerboard and all pieces on it
     * @param direction potential direction that piece could move in
     * @return the boolean value of if a piece can move legally in direction excluding jumps
     * @pre none
     * @post checkIfValidMove = [true if piece can legally move in specified direction excluding jumps, false otherwise]
     */
   private static boolean checkIfValidMove(BoardPosition pos, ICheckerBoard gameBoard, DirectionEnum direction) {
       boolean validMove = false;


       HashMap<Character, ArrayList<DirectionEnum>> viableDirections = gameBoard.getViableDirections();
       if (viableDirections.containsKey(gameBoard.whatsAtPos(pos))) {


           if (!viableDirections.get(gameBoard.whatsAtPos(pos)).contains(direction)) {
               return false;
           }
           //figure out aRow and aColumn change based on dir
           // Figure out row and column changes based on direction
           int rowMove = 0;
           int colMove = 0;
           switch (direction) {
               case NE:
                   rowMove = -1;
                   colMove = 1;
                   break;
               case NW:
                   rowMove = -1;
                   colMove = -1;
                   break;
               case SE:
                   rowMove = 1;
                   colMove = 1;
                   break;
               case SW:
                   rowMove = 1;
                   colMove = -1;
                   break;
           }
           // Calculate new position after move
           int newRowMove = pos.getRow() + rowMove;
           int newColMove = pos.getColumn() + colMove;


           BoardPosition moveGoal = new BoardPosition(newRowMove, newColMove);
           if (moveGoal.isValid(gameBoard.getRowNum(), gameBoard.getColNum()) && gameBoard.whatsAtPos(moveGoal) == EMPTY_POS)
           {
               validMove = true;
           }


       }
       return validMove;
   }

    /**
     * Function that checks if a piece specified by its position can legally jump in a certain direction.
     * @param pos represents a position of a piece on the checkerboard
     * @param gameBoard represents the entire checkerboard and all pieces on it
     * @param direction potential direction that piece could jump
     * @return the boolean value of if a piece can jump legally in direction
     * @pre none
     * @post checkIfValidJump = [true if piece can legally jump in specified direction, false otherwise]
     */
   private static boolean checkIfValidJump(BoardPosition pos, ICheckerBoard gameBoard, DirectionEnum direction) {
       boolean validMove = false;

       HashMap<Character, ArrayList<DirectionEnum>> viableDirections = gameBoard.getViableDirections();
       if (viableDirections.containsKey(gameBoard.whatsAtPos(pos))) {


           if (!viableDirections.get(gameBoard.whatsAtPos(pos)).contains(direction)) {
               return false;
           }

           int rowMove = 0;
           int colMove = 0;
           int rowJump = 0;
           int colJump = 0;
           switch (direction) {
               case NE:
                   rowMove = -1;
                   colMove = 1;
                   rowJump = -2;
                   colJump = 2;
                   break;
               case NW:
                   rowMove = -1;
                   colMove = -1;
                   rowJump = -2;
                   colJump = -2;
                   break;
               case SE:
                   rowMove = 1;
                   colMove = 1;
                   rowJump = 2;
                   colJump = 2;
                   break;
               case SW:
                   rowMove = 1;
                   colMove = -1;
                   rowJump = 2;
                   colJump = -2;
                   break;
           }
           // Calculate new position after move
           int newRowMove = pos.getRow() + rowMove;
           int newColMove = pos.getColumn() + colMove;
           // Calculate new position after jump
           int newRowJump = pos.getRow() + rowJump;
           int newColJump = pos.getColumn() + colJump;

           // Create boardPositions for both of the pieces in question
           BoardPosition pieceGettingJumped = new BoardPosition(newRowMove, newColMove);
           BoardPosition jumpGoal = new BoardPosition(newRowJump, newColJump);

           // Make sure that the target position is empty and is a valid move
           if (jumpGoal.isValid(gameBoard.getRowNum(), gameBoard.getColNum()) && gameBoard.whatsAtPos(jumpGoal) == EMPTY_POS) {
               // Make sure there is a piece to jump over
               if (gameBoard.whatsAtPos(pieceGettingJumped) != EMPTY_POS) {
                   // Check what the piece being jumped is and setting what the opposing player should be
                   char piece = gameBoard.whatsAtPos(pos);
                   char pieceBeingJumped = gameBoard.whatsAtPos(pieceGettingJumped);
                   char opposingPlayer;
                   if (piece == PLAYER_ONE || piece == PLAYER_ONE_KING) {
                       opposingPlayer = PLAYER_TWO;
                   }
                   else {
                       opposingPlayer = PLAYER_ONE;
                   }


                   // Make sure that the piece being jumped is an opposing players piece
                   if (pieceBeingJumped == opposingPlayer || pieceBeingJumped == Character.toUpperCase(opposingPlayer)) {
                       validMove = true;
                   }
               }
           }
       }
       return validMove;
   }


    /**
     * Function that prompts user for their turn input and returns the position that the user inputed
     * @param turn_indicator boolean variable to determine which player is being prompted
     * @return the BoardPosition that is produced from the user input
     * @pre none
     * @post userInput = [a BoardPosition object that is created from the user input]
     */
    private static BoardPosition userInput(boolean turn_indicator){
        char player_char;

        if(turn_indicator == P1_TURN){
            player_char = PLAYER_ONE;
        }
        else{
            player_char = PLAYER_TWO;
        }
        String output_text = "player " + player_char + ", which piece do you want to move? " +
                "Enter the row followed by a space followed by the column.";
        System.out.println(output_text);
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        String[] userInputSeperated = userInput.split(" ");

        int row = Integer.parseInt(userInputSeperated[0]);
        int col = Integer.parseInt(userInputSeperated[1]);
        return new BoardPosition(row, col);

    }
}
