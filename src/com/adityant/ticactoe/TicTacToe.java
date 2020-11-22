package com.adityant.ticactoe;

import java.util.Random;

public class TicTacToe {
    static GameBoard gameBoard;
    static int currentPlayer = 1;
    static String gameMode = null;
    static String playerMode = null;

    public static void main(String[] args) {
        new ModeSelector();
    }

    static void beginGame() {

        try {
            new GameWindow();
            GameWindow.updateTurnIndicatorLabel();
            GameWindow.clearBoardIcons();
        } catch (IllegalArgumentException e) {   //Catch missing image files
            System.out.println("One or more image files is missing from the resource directory");
            shutdownApplication();
        }

        gameBoard = new GameBoard();
        Random rand = new Random();

        gameBoard.displayBoard();

        if (playerMode.equals("Player 2 (or Computer)"))
            currentPlayer = 2;

        if (gameMode.equals("Player vs Computer") && playerMode.equals("Player 2 (or Computer)")) {
            Tile p = new Tile(rand.nextInt(3), rand.nextInt(3));
            gameBoard.placeAMove(p, currentPlayer % 2 + 1);
            gameBoard.displayBoard();
            currentPlayer++;
        }
    }

    static void submitmove(Tile userMove) {

        gameBoard.placeAMove(userMove, currentPlayer % 2 + 1); //2 for O and O is the user
        gameBoard.displayBoard();
        currentPlayer++;

        if (gameBoard.isGameOver()) {
            getGameResult();
            return;
        }

        if (gameMode.equals("Player vs Computer")) {
            gameBoard.alphaBetaMinimax(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 1);
            for (TilesAndScores pas : gameBoard.rootsChildrenScore)
                System.out.println("Point: " + pas.tile + " Score: " + pas.score);

            gameBoard.placeAMove(gameBoard.returnBestMove(), currentPlayer % 2 + 1);
            gameBoard.displayBoard();
            currentPlayer++;

            if (gameBoard.isGameOver()) getGameResult();

        }

        GameWindow.updateTurnIndicatorLabel();
    }

    private static void getGameResult() {

        GameBoard.Result gameResult = gameBoard.getGameResult();

        if (gameResult == GameBoard.Result.OWON) {
            GameWindow.winnerDetectedMessage("Player 1");
        } else if (gameResult == GameBoard.Result.XWON) {
            if (gameMode.equals("Player vs Player"))
                GameWindow.winnerDetectedMessage("Player 2");
            else
                GameWindow.winnerDetectedMessage("Computer");
        } else {
            GameWindow.drawDetectedMessage();
        }

    }


    static void shutdownApplication() {
        System.exit(0);
    }
}