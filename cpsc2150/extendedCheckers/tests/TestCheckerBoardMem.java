package cpsc2150.extendedCheckers.tests;

import cpsc2150.extendedCheckers.models.CheckerBoardMem;
import cpsc2150.extendedCheckers.models.BoardPosition;
import cpsc2150.extendedCheckers.models.CheckerBoard;
import cpsc2150.extendedCheckers.models.AbsCheckerBoard;
import cpsc2150.extendedCheckers.models.ICheckerBoard;
import cpsc2150.extendedCheckers.util.DirectionEnum;


import cpsc2150.extendedCheckers.util.DirectionEnum;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TestCheckerBoardMem {

    private ICheckerBoard makeBoard(int a)
    {
        ICheckerBoard game = new CheckerBoardMem(a);
        return game;
    }



    //Second method that returns the string representation of the 2d array
    // of characters
    //Should be used for the constructor, not tested
    private String makeString(char[][] cb)
    {
        String retValue = "";

        //Add the header line
        retValue = retValue.concat("|  | ");
        for (int i = 0; i < cb.length; i++)
        {
            if (i == cb.length-1){
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
        for (int i = 0; i < cb.length; i++)
        {
            if (i < 10) {
                retValue = retValue.concat("\n|" + i + " ");
            }
            else{
                retValue = retValue.concat("\n|" + i);
            }
            for (int j = 0; j < cb[i].length; j++)

            {
                if (j == 0) {
                    char pos = cb[i][j];

                    retValue = retValue.concat("|" + pos);
                }
                else{
                    char pos = cb[i][j];

                    retValue = retValue.concat(" |" + pos);
                }
            }
            retValue = retValue.concat(" |");
        }
        retValue = retValue.concat("\n");

        return retValue;

    }
    @Test
    public void testWhatsAtPos_1_1_x(){
        ICheckerBoard gameBoard = makeBoard(8);
        BoardPosition pos = new BoardPosition(1, 1);
        char observed = gameBoard.whatsAtPos(pos);
        char expected = 'x';
        assertEquals(observed, expected);
    }

    @Test
    public void testWhatsAtPos_minRow_2_x(){
        ICheckerBoard gameBoard = makeBoard(8);
        BoardPosition pos = new BoardPosition(0, 2);
        char observed = gameBoard.whatsAtPos(pos);
        char expected = 'x';
        assertEquals(observed, expected);
    }

    @Test
    public void testWhatsAtPos_3_maxColumn(){
        ICheckerBoard gameBoard = makeBoard(8);
        BoardPosition pos = new BoardPosition(3, 7);
        char observed = gameBoard.whatsAtPos(pos);
        char expected = ' ';
        assertEquals(observed, expected);
    }

    @Test
    public void testWhatsAtPos_maxRow_6(){
        ICheckerBoard gameBoard = makeBoard(8);
        BoardPosition pos = new BoardPosition(7, 6);
        char observed = gameBoard.whatsAtPos(pos);
        char expected = '*';
        assertEquals(observed, expected);
    }

    @Test
    public void testWhatsAtPos_6_minCol_o(){
        ICheckerBoard gameBoard = makeBoard(8);
        BoardPosition pos = new BoardPosition(6, 0);
        char observed = gameBoard.whatsAtPos(pos);
        char expected = 'o';
        assertEquals(observed, expected);
    }

    @Test
    public void getRowNumTest_8(){
        ICheckerBoard gameBoard = makeBoard(8);
        int observed = gameBoard.getRowNum();
        int expected = 8;
        assertEquals(observed, expected);
    }

    @Test
    public void getColNumTest_8(){
        ICheckerBoard gameBoard = makeBoard(8);
        int observed = gameBoard.getColNum();
        int expected = 8;
        assertEquals(observed, expected);
    }

    @Test
    public void testMovePiece_maxRow_maxCol_X(){
        ICheckerBoard gameBoard = makeBoard(8);
        BoardPosition startingPos = new BoardPosition(6, 6);
        BoardPosition nextPos = new BoardPosition(7, 7);
        DirectionEnum dir = DirectionEnum.SE;

        //Modify the board
        gameBoard.placePiece(startingPos, 'x');
        gameBoard.placePiece(nextPos, ' ');


        //Make an expected board

        char[][] expectedBoard = new char[8][8];
        //Initialize the  expected board
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 3) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 4){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }
        //Make the necessary changes to the expected board to test
        expectedBoard[6][6] = 'x';
        expectedBoard[7][7] = ' ';

        String expectedBoardOutput = makeString(expectedBoard);
        String observedBoardOutput = gameBoard.toString();


        BoardPosition observed = gameBoard.movePiece(startingPos, dir);
        BoardPosition expected = new BoardPosition(7, 7);
        assertEquals(observed, expected);
        assertEquals(expectedBoardOutput, observedBoardOutput);

    }

    @Test
    public void testMovePiece_minRow_minCol_O(){
        ICheckerBoard gameBoard = makeBoard(8);
        BoardPosition startingPos = new BoardPosition(1, 1);
        BoardPosition nextPos = new BoardPosition(0, 0);
        DirectionEnum dir = DirectionEnum.NW;

        //Modify the board
        gameBoard.placePiece(startingPos, 'O');
        gameBoard.placePiece(nextPos, ' ');

        //Make an expected board

        char[][] expectedBoard = new char[8][8];
        //Initialize the  expected board
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 3) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 4){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }
        //Make the necessary changes to the expected board to test
        expectedBoard[1][1] = 'O';
        expectedBoard[0][0] = ' ';

        String expectedBoardOutput = makeString(expectedBoard);
        String observedBoardOutput = gameBoard.toString();

        BoardPosition observed = gameBoard.movePiece(startingPos, dir);
        BoardPosition expected = new BoardPosition(0, 0);
        assertEquals(observed, expected);
        assertEquals(expectedBoardOutput, observedBoardOutput);
    }

    @Test
    public void testMovePiece_2_2_kingBackwards_O(){
        ICheckerBoard gameBoard = makeBoard(8);
        BoardPosition startingPos = new BoardPosition(2, 2);
        BoardPosition nextPos = new BoardPosition(3, 3);
        DirectionEnum dir = DirectionEnum.SE;

        //Modify the board
        gameBoard.placePiece(startingPos, 'O');
        gameBoard.placePiece(nextPos, ' ');

        //Make an expected board

        char[][] expectedBoard = new char[8][8];
        //Initialize the  expected board
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 3) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 4){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }
        //Make the necessary changes to the expected board to test
        expectedBoard[2][2] = 'O';
        expectedBoard[3][3] = ' ';

        String expectedBoardOutput = makeString(expectedBoard);
        String observedBoardOutput = gameBoard.toString();

        BoardPosition observed = gameBoard.movePiece(startingPos, dir);
        BoardPosition expected = new BoardPosition(3, 3);
        assertEquals(observed, expected);
        assertEquals(expectedBoardOutput, observedBoardOutput);
    }

    @Test
    public void testJumpPiece_maxRow_maxCol_X(){
        ICheckerBoard gameBoard = makeBoard(8);
        BoardPosition startingPos = new BoardPosition(5,5);
        BoardPosition nextPos = new BoardPosition(7,7);
        DirectionEnum dir = DirectionEnum.SE;

        // Modify the board itself
        gameBoard.placePiece(startingPos, 'x');
        gameBoard.placePiece(nextPos, ' ');

        char[][] expectedBoard = new char[8][8];
        //Initialize the  expected board
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 3) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 4){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }

        expectedBoard[5][5] = 'x';
        expectedBoard[7][7] = ' ';

        String expectedBoardOutput = makeString(expectedBoard);
        String observedBoardOutput = gameBoard.toString();
        assertEquals(observedBoardOutput,expectedBoardOutput);

        BoardPosition observed = gameBoard.jumpPiece(startingPos, dir);
        BoardPosition expected = new BoardPosition(7,7);
        assertEquals(observed,expected);
    }

    @Test
    public void testJumpPiece_minRow_minCol_O(){
        ICheckerBoard gameBoard = makeBoard(8);
        BoardPosition startingPos = new BoardPosition(2,2);
        BoardPosition nextPos = new BoardPosition(0,0);
        DirectionEnum dir = DirectionEnum.NW;

        gameBoard.placePiece(startingPos, 'o');
        gameBoard.placePiece(nextPos, ' ');

        char[][] expectedBoard = new char[8][8];
        //Initialize the  expected board
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 3) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 4){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }

        expectedBoard[2][2] = 'o';
        expectedBoard[0][0] = ' ';

        String expectedBoardOutput = makeString(expectedBoard);
        String observedBoardOutput = gameBoard.toString();
        assertEquals(observedBoardOutput,expectedBoardOutput);

        BoardPosition observed = gameBoard.jumpPiece(startingPos, dir);
        BoardPosition expected = new BoardPosition(0,0);
        assertEquals(observed,expected);
    }

    @Test
    public void testJumpPiece_3_3_kingBackwardsO(){
        ICheckerBoard gameBoard = makeBoard(8);
        BoardPosition startingPos = new BoardPosition(1,1);
        BoardPosition nextPos = new BoardPosition(3,3);
        DirectionEnum dir = DirectionEnum.SE;

        gameBoard.placePiece(startingPos, 'O');
        gameBoard.placePiece(nextPos, ' ');

        char[][] expectedBoard = new char[8][8];
        //Initialize the  expected board
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 3) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 4){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }

        expectedBoard[1][1] = 'O';

        String expectedBoardOutput = makeString(expectedBoard);
        String observedBoardOutput = gameBoard.toString();
        assertEquals(observedBoardOutput,expectedBoardOutput);

        BoardPosition observed = gameBoard.jumpPiece(startingPos, dir);
        BoardPosition expected = new BoardPosition(3,3);
        assertEquals(observed,expected);
    }

    @Test
    public void testCheckPlayerWin_PLAYER_ONE_Win() {
        ICheckerBoard gameBoard = makeBoard(8);
        gameBoard.getPieceCounts().put(ICheckerBoard.PLAYER_ONE,12);
        gameBoard.getPieceCounts().put(ICheckerBoard.PLAYER_TWO,0);

        assertTrue(gameBoard.checkPlayerWin(ICheckerBoard.PLAYER_ONE));
    }

    @Test
    public void testCheckPlayerWin_PLAYER_TWO_NoWin() {
        ICheckerBoard gameBoard = makeBoard(8);
        gameBoard.getPieceCounts().put(ICheckerBoard.PLAYER_ONE,1);
        gameBoard.getPieceCounts().put(ICheckerBoard.PLAYER_TWO,12);

        assertTrue(!gameBoard.checkPlayerWin(ICheckerBoard.PLAYER_TWO));
    }

    @Test
    public void testGetViableDirections_PLAYER_ONE(){
        ICheckerBoard gameBoard = makeBoard(8);
        HashMap<Character, ArrayList<DirectionEnum>> viableDirections = gameBoard.getViableDirections();
        ArrayList<DirectionEnum> playerOneDirections = viableDirections.get(ICheckerBoard.PLAYER_ONE);

        assertTrue(playerOneDirections.contains(DirectionEnum.SE));
        assertTrue(playerOneDirections.contains(DirectionEnum.SW));
    }


    @Test
    public void testAddViableDirections_AddDirNE_PLAYERONE(){
        ICheckerBoard gameBoard = makeBoard(8);
        gameBoard.addViableDirections(ICheckerBoard.PLAYER_ONE,DirectionEnum.NE);

        HashMap<Character, ArrayList<DirectionEnum>> viableDirections = gameBoard.getViableDirections();
        ArrayList<DirectionEnum> playerOneDirections = viableDirections.get(ICheckerBoard.PLAYER_ONE);
        assertTrue(playerOneDirections.contains(DirectionEnum.NE));
    }

    @Test
    public void testCheckerBoard_Con_10x10(){
        ICheckerBoard gameBoard = makeBoard(10);

        char[][] expectedBoard = new char[10][10];
        //Initialize the  expected board
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 4) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 5){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }

        String expected = makeString(expectedBoard);

        String observed = gameBoard.toString();

        assertEquals(expected, observed);
    }

    @Test
    public void testCheckerBoard_Con_8x8(){
        ICheckerBoard gameBoard = makeBoard(8);

        char[][] expectedBoard = new char[8][8];
        //Initialize the  expected board
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 3) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 4){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }

        String expected = makeString(expectedBoard);

        String observed = gameBoard.toString();

        assertEquals(expected, observed);

    }

    @Test
    public void testCheckerBoard_Con_16x16(){
        ICheckerBoard gameBoard = makeBoard(16);

        char[][] expectedBoard = new char[16][16];
        //Initialize the  expected board
        for (int i = 0; i < 16; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 7) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 8){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }

        String expected = makeString(expectedBoard);

        String observed = gameBoard.toString();

        assertEquals(expected, observed);

    }

    @Test
    public void testPlacePiece_6_6_x(){
        ICheckerBoard gameBoard = makeBoard(8);

        char[][] expectedBoard = new char[8][8];
        //Initialize the  expected board
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 3) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 4){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }



        BoardPosition bp = new BoardPosition(6,6);
        char player = 'x';
        gameBoard.placePiece(bp, player);
        expectedBoard[6][6] = player;

        String observed = gameBoard.toString();
        String expected = makeString(expectedBoard);

        assertEquals(expected, observed);

    }

    @Test
    public void testPlacePiece_4_1_o(){
        ICheckerBoard gameBoard = makeBoard(8);

        char[][] expectedBoard = new char[8][8];
        //Initialize the  expected board
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 3) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 4){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }

        BoardPosition bp = new BoardPosition(4,1);
        char player = 'o';
        gameBoard.placePiece(bp, player);
        expectedBoard[4][1] = player;

        String observed = gameBoard.toString();
        String expected = makeString(expectedBoard);

        assertEquals(expected, observed);

    }


    @Test
    public void testPlacePiece_minRow_5_x(){
        ICheckerBoard gameBoard = makeBoard(8);

        char[][] expectedBoard = new char[8][8];
        //Initialize the  expected board
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 3) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 4){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }

        BoardPosition bp = new BoardPosition(0,5);
        char player = 'x';
        gameBoard.placePiece(bp, player);
        expectedBoard[0][5] = player;

        String observed = gameBoard.toString();
        String expected = makeString(expectedBoard);

        assertEquals(expected, observed);

    }



    @Test
    public void testPlacePiece_2_minColumn_o(){
        ICheckerBoard gameBoard = makeBoard(8);

        char[][] expectedBoard = new char[8][8];
        //Initialize the  expected board
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 3) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 4){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }

        BoardPosition bp = new BoardPosition(2,0);
        char player = 'o';
        gameBoard.placePiece(bp, player);
        expectedBoard[2][0] = player;

        String observed = gameBoard.toString();
        String expected = makeString(expectedBoard);

        assertEquals(expected, observed);

    }

    @Test
    public void testPlacePiece_4_maxColumn_x(){
        ICheckerBoard gameBoard = makeBoard(8);

        char[][] expectedBoard = new char[8][8];
        //Initialize the  expected board
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 3) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 4){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }

        BoardPosition bp = new BoardPosition(4,7);
        char player = 'x';
        gameBoard.placePiece(bp, player);
        expectedBoard[4][7] = player;

        String observed = gameBoard.toString();
        String expected = makeString(expectedBoard);

        assertEquals(expected, observed);

    }

    @Test
    public void testPlacePiece_maxRow_2_o(){
        ICheckerBoard gameBoard = makeBoard(8);

        char[][] expectedBoard = new char[8][8];
        //Initialize the  expected board
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i+j) % 2 == 1)
                {
                    expectedBoard[i][j] = '*';
                } else if (i < 3) {
                    expectedBoard[i][j] = 'x';
                }
                else if (i > 4){
                    expectedBoard[i][j] = 'o';
                }
                else{
                    expectedBoard[i][j] = ' ';
                }
            }
        }

        BoardPosition bp = new BoardPosition(7,2);
        char player = 'o';
        gameBoard.placePiece(bp, player);
        expectedBoard[7][2] = player;

        String observed = gameBoard.toString();
        String expected = makeString(expectedBoard);

        assertEquals(expected, observed);

    }

    @Test
    public void testScanSurroundingPositions_2_4_x(){
        ICheckerBoard gameBoard = makeBoard(8);

        BoardPosition bp = new BoardPosition(2,4);

        HashMap<DirectionEnum, Character> expected = new HashMap<>();
        expected.put(DirectionEnum.NE, 'x');
        expected.put(DirectionEnum.NW, 'x');
        expected.put(DirectionEnum.SE, ' ');
        expected.put(DirectionEnum.SW, ' ');

        HashMap<DirectionEnum, Character> observed = gameBoard.scanSurroundingPositions(bp);

        assertEquals(expected, observed);

    }

    @Test
    public void testScanSurroundingPositions_minRow_5_x(){
        ICheckerBoard gameBoard = makeBoard(8);

        BoardPosition bp = new BoardPosition(0,4);

        HashMap<DirectionEnum, Character> expected = new HashMap<>();
        expected.put(DirectionEnum.SE, 'x');
        expected.put(DirectionEnum.SW, 'x');

        HashMap<DirectionEnum, Character> observed = gameBoard.scanSurroundingPositions(bp);

        assertEquals(expected, observed);

    }

    @Test
    public void testScanSurroundingPositions_6_minColumn_x(){
        ICheckerBoard gameBoard = makeBoard(8);

        BoardPosition bp = new BoardPosition(6,0);

        HashMap<DirectionEnum, Character> expected = new HashMap<>();
        expected.put(DirectionEnum.NE, 'o');
        expected.put(DirectionEnum.SE, 'o');

        HashMap<DirectionEnum, Character> observed = gameBoard.scanSurroundingPositions(bp);

        assertEquals(expected, observed);

    }

    @Test
    public void testScanSurroundingPositions_maxRow_3_o(){
        ICheckerBoard gameBoard = makeBoard(8);

        BoardPosition bp = new BoardPosition(7,3);

        HashMap<DirectionEnum, Character> expected = new HashMap<>();
        expected.put(DirectionEnum.NE, 'o');
        expected.put(DirectionEnum.NW, 'o');

        HashMap<DirectionEnum, Character> observed = gameBoard.scanSurroundingPositions(bp);

        assertEquals(expected, observed);

    }

    @Test
    public void testScanSurroundingPositions_1_maxColumn_o(){
        ICheckerBoard gameBoard = makeBoard(8);

        BoardPosition bp = new BoardPosition(1,7);

        HashMap<DirectionEnum, Character> expected = new HashMap<>();
        expected.put(DirectionEnum.NW, 'x');
        expected.put(DirectionEnum.SW, 'x');

        HashMap<DirectionEnum, Character> observed = gameBoard.scanSurroundingPositions(bp);

        assertEquals(expected, observed);

    }

    @Test
    public void testCrownPiece_MinRow_MinColumn_x() {
        ICheckerBoard gameBoard = makeBoard(8);

        BoardPosition bp = new BoardPosition(0,0);

        gameBoard.crownPiece(bp);

        String expected =
                "|  | 0| 1| 2| 3| 4| 5| 6| 7|\n" +
                        "|0 |X |* |x |* |x |* |x |* |\n" +
                        "|1 |* |x |* |x |* |x |* |x |\n" +
                        "|2 |x |* |x |* |x |* |x |* |\n" +
                        "|3 |* |  |* |  |* |  |* |  |\n" +
                        "|4 |  |* |  |* |  |* |  |* |\n" +
                        "|5 |* |o |* |o |* |o |* |o |\n" +
                        "|6 |o |* |o |* |o |* |o |* |\n" +
                        "|7 |* |o |* |o |* |o |* |o |\n";

        String observed = gameBoard.toString();

        assertEquals(expected, observed);
    }

    @Test
    public void testCrownPiece_5_1_o() {
        ICheckerBoard gameBoard = makeBoard(8);

        BoardPosition bp = new BoardPosition(5,1);

        gameBoard.crownPiece(bp);

        String expected =
                "|  | 0| 1| 2| 3| 4| 5| 6| 7|\n" +
                        "|0 |x |* |x |* |x |* |x |* |\n" +
                        "|1 |* |x |* |x |* |x |* |x |\n" +
                        "|2 |x |* |x |* |x |* |x |* |\n" +
                        "|3 |* |  |* |  |* |  |* |  |\n" +
                        "|4 |  |* |  |* |  |* |  |* |\n" +
                        "|5 |* |O |* |o |* |o |* |o |\n" +
                        "|6 |o |* |o |* |o |* |o |* |\n" +
                        "|7 |* |o |* |o |* |o |* |o |\n";

        String observed = gameBoard.toString();

        assertEquals(expected, observed);
    }

    @Test
    public void testCrownPiece_1_MaxColumn_x() {
        ICheckerBoard gameBoard = makeBoard(8);

        BoardPosition bp = new BoardPosition(1,7);

        gameBoard.crownPiece(bp);

        String expected =
                "|  | 0| 1| 2| 3| 4| 5| 6| 7|\n" +
                        "|0 |x |* |x |* |x |* |x |* |\n" +
                        "|1 |* |x |* |x |* |x |* |X |\n" +
                        "|2 |x |* |x |* |x |* |x |* |\n" +
                        "|3 |* |  |* |  |* |  |* |  |\n" +
                        "|4 |  |* |  |* |  |* |  |* |\n" +
                        "|5 |* |o |* |o |* |o |* |o |\n" +
                        "|6 |o |* |o |* |o |* |o |* |\n" +
                        "|7 |* |o |* |o |* |o |* |o |\n";

        String observed = gameBoard.toString();

        assertEquals(expected, observed);
    }

    @Test
    public void testCrownPiece_MaxRow_1_o() {
        ICheckerBoard gameBoard = makeBoard(8);

        BoardPosition bp = new BoardPosition(7,1);

        gameBoard.crownPiece(bp);

        String expected =
                "|  | 0| 1| 2| 3| 4| 5| 6| 7|\n" +
                        "|0 |x |* |x |* |x |* |x |* |\n" +
                        "|1 |* |x |* |x |* |x |* |x |\n" +
                        "|2 |x |* |x |* |x |* |x |* |\n" +
                        "|3 |* |  |* |  |* |  |* |  |\n" +
                        "|4 |  |* |  |* |  |* |  |* |\n" +
                        "|5 |* |o |* |o |* |o |* |o |\n" +
                        "|6 |o |* |o |* |o |* |o |* |\n" +
                        "|7 |* |O |* |o |* |o |* |o |\n";

        String observed = gameBoard.toString();

        assertEquals(expected, observed);
    }

    @Test
    public void testPlayerLostPieces_1_x_1() {
        ICheckerBoard gameBoard = makeBoard(8);

        int numPieces = 1;
        char player = 'x';
        HashMap<Character, Integer> pieceCounts = new HashMap<>();
        pieceCounts.put('x', 1);

        gameBoard.playerLostPieces(numPieces, player, pieceCounts);

        int expected = 0;

        int observed = pieceCounts.get('x');

        assertEquals(expected, observed);
    }

    @Test
    public void testGetPieceCounts_x_6_o_min() {
        ICheckerBoard gameBoard = makeBoard(8);

        HashMap<Character, Integer> expectedHashMap = new HashMap<>();
        expectedHashMap.put('x', 6);
        expectedHashMap.put('o', 0);

        // Remove x's
        BoardPosition pos1 = new BoardPosition(0,0);
        BoardPosition pos2 = new BoardPosition(0,2);
        BoardPosition pos3 = new BoardPosition(0,4);
        BoardPosition pos4 = new BoardPosition(0,6);
        BoardPosition pos5 = new BoardPosition(1,1);
        BoardPosition pos6 = new BoardPosition(1,3);

        gameBoard.placePiece(pos1, ' ');
        gameBoard.placePiece(pos2, ' ');
        gameBoard.placePiece(pos3, ' ');
        gameBoard.placePiece(pos4, ' ');
        gameBoard.placePiece(pos5, ' ');
        gameBoard.placePiece(pos6, ' ');

        // Remove o's
        BoardPosition pos7 = new BoardPosition(5,1);
        BoardPosition pos8 = new BoardPosition(5,3);
        BoardPosition pos9 = new BoardPosition(5,5);
        BoardPosition pos10 = new BoardPosition(5,7);
        BoardPosition pos11 = new BoardPosition(6,0);
        BoardPosition pos12 = new BoardPosition(6,2);
        BoardPosition pos13 = new BoardPosition(6,4);
        BoardPosition pos14 = new BoardPosition(6,6);
        BoardPosition pos15 = new BoardPosition(7,1);
        BoardPosition pos16 = new BoardPosition(7,3);
        BoardPosition pos17 = new BoardPosition(7,5);
        BoardPosition pos18 = new BoardPosition(7,7);

        gameBoard.placePiece(pos7, ' ');
        gameBoard.placePiece(pos8, ' ');
        gameBoard.placePiece(pos9, ' ');
        gameBoard.placePiece(pos10, ' ');
        gameBoard.placePiece(pos11, ' ');
        gameBoard.placePiece(pos12, ' ');
        gameBoard.placePiece(pos13, ' ');
        gameBoard.placePiece(pos14, ' ');
        gameBoard.placePiece(pos15, ' ');
        gameBoard.placePiece(pos16, ' ');
        gameBoard.placePiece(pos17, ' ');
        gameBoard.placePiece(pos18, ' ');

        gameBoard.playerLostPieces(6, 'x', gameBoard.getPieceCounts());
        gameBoard.playerLostPieces(12, 'o', gameBoard.getPieceCounts());
        HashMap<Character, Integer> observedHashMap = gameBoard.getPieceCounts();

        assertEquals(expectedHashMap, observedHashMap);

        String expectedBoard =
                "|  | 0| 1| 2| 3| 4| 5| 6| 7|\n" +
                        "|0 |  |* |  |* |  |* |  |* |\n" +
                        "|1 |* |  |* |  |* |x |* |x |\n" +
                        "|2 |x |* |x |* |x |* |x |* |\n" +
                        "|3 |* |  |* |  |* |  |* |  |\n" +
                        "|4 |  |* |  |* |  |* |  |* |\n" +
                        "|5 |* |  |* |  |* |  |* |  |\n" +
                        "|6 |  |* |  |* |  |* |  |* |\n" +
                        "|7 |* |  |* |  |* |  |* |  |\n";

        String observedBoard = gameBoard.toString();

        assertEquals(expectedBoard, observedBoard);
    }

    @Test
    public void testGetPieceCounts_x_max_o_3() {
        ICheckerBoard gameBoard = makeBoard(8);

        HashMap<Character, Integer> expectedHashMap = new HashMap<>();
        expectedHashMap.put('x', 12);
        expectedHashMap.put('o', 8);

        // Modify the baord
        BoardPosition pos1 = new BoardPosition(7,1);
        BoardPosition pos2 = new BoardPosition(7,3);
        BoardPosition pos3 = new BoardPosition(7,5);
        BoardPosition pos4 = new BoardPosition(7,7);

        gameBoard.placePiece(pos1, ' ');
        gameBoard.placePiece(pos2, ' ');
        gameBoard.placePiece(pos3, ' ');
        gameBoard.placePiece(pos4, ' ');

        gameBoard.playerLostPieces(4, 'o', gameBoard.getPieceCounts());
        HashMap<Character, Integer> observedHashMap = gameBoard.getPieceCounts();

        assertEquals(expectedHashMap, observedHashMap);

        String expectedBoard =
                "|  | 0| 1| 2| 3| 4| 5| 6| 7|\n" +
                        "|0 |x |* |x |* |x |* |x |* |\n" +
                        "|1 |* |x |* |x |* |x |* |x |\n" +
                        "|2 |x |* |x |* |x |* |x |* |\n" +
                        "|3 |* |  |* |  |* |  |* |  |\n" +
                        "|4 |  |* |  |* |  |* |  |* |\n" +
                        "|5 |* |o |* |o |* |o |* |o |\n" +
                        "|6 |o |* |o |* |o |* |o |* |\n" +
                        "|7 |* |  |* |  |* |  |* |  |\n";

        String observedBoard = gameBoard.toString();

        assertEquals(expectedBoard, observedBoard);
    }

    @Test
    public void testGetDirection_NE() {
        ICheckerBoard gameBoard = makeBoard(8);

        BoardPosition bp = ICheckerBoard.getDirection(DirectionEnum.NE);

        String expected = "-1,1";

        String observed = bp.toString();

        assertEquals(expected, observed);
    }


}