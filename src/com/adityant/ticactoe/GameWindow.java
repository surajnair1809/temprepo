package com.adityant.ticactoe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

class GameWindow {
    static GridButton[] button = new GridButton[9];
    private static JLabel indicatorLabel = new JLabel();
    static JFrame windowFrame;

    //JFrame Constructor
    GameWindow() {
        //windowFrame Properties, main window containing all elements
        windowFrame = new JFrame();
        windowFrame.setSize(410, 510);
        windowFrame.setBackground(Color.white);
        windowFrame.setResizable(false);
        windowFrame.setLocationRelativeTo(null);	//centers the window
        windowFrame.setTitle("TicTacToe");
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setVisible(true);

        //indicatorPanel Properties (will contain turn indicator)
        JPanel indicatorPanel = new JPanel();
        indicatorPanel.setSize(400, 50);
        //Padding for some of the board elements (for easier modification)
        byte paddingSize = 5;
        indicatorPanel.setBorder(new EmptyBorder(paddingSize, 0, paddingSize, 0));
        indicatorPanel.add(getIndicatorLabel());
        indicatorPanel.setVisible(true);

        //centralGamePanel Properties (will contain grid buttons)
        JPanel centralGamePanel = new JPanel();
        centralGamePanel.setSize(400, 400);
        centralGamePanel.setLayout(new GridLayout(3, 3));
        centralGamePanel.setBorder(new EmptyBorder(0, paddingSize, 0, paddingSize));
        centralGamePanel.setVisible(true);

        //Populate the game grid with XO buttons
        for (int i = 0; i < 9; i++) {
            button[i] = new GridButton();
            centralGamePanel.add(button[i]);
        }


        //TurnIndicator label properties
        indicatorLabel.setFont(indicatorLabel.getFont().deriveFont(20.0f));

        //Add all the components to the frame (border layout)
        windowFrame.setLayout(new BorderLayout(paddingSize, paddingSize));
        windowFrame.add(indicatorPanel, BorderLayout.NORTH);
        windowFrame.add(centralGamePanel, BorderLayout.CENTER);


    }

    /**
     * Method which resets the entire game back to its original state. Clearing all icons and values from the game grid.
     * Next player is then generated randomly if the game is continued.
     **/

    private static void resetGame() {
        try {
            GameWindow.windowFrame.dispose();
            TicTacToe.currentPlayer = 1;
            TicTacToe.gameMode = null;
            TicTacToe.playerMode = null;
            TicTacToe.gameState = "STOPPED";
            new ModeSelector();
        } catch (NullPointerException ignore) {}
    }

    //Clear all saved records from the gameGrid (restore them back to original "." state)


    static void clearBoardIcons() {
        for (int i = 0; i < 9; i++) {
            button[i].setIcon(null);
        }
    }

/*
    private static void enableGridButtons() {
        for (int i = 0; i < 9; i++) {
            GameWindow.button[i].setEnabled(true);
        }
    }
*/

    static void updateTurnIndicatorLabel() {
        String firstPlayerName = "";
        String secondPlayerName = "";

        if(TicTacToe.gameMode == null) return;

        //Determine what to call the second player based on the game mode
        if (TicTacToe.gameMode.equals("Player vs Computer")) {
            firstPlayerName = "Your Turn";
            secondPlayerName = "Computer's Turn";
        } else {
            firstPlayerName = "Player 1's Turn";
            secondPlayerName = "Player 2's Turn";
        }

        if (TicTacToe.currentPlayer %2 == 1) {
            setIndicatorLabel(""+ firstPlayerName);
        } else if (TicTacToe.currentPlayer %2 == 0) {
            setIndicatorLabel("" + secondPlayerName);
        }
    }

    private static JLabel getIndicatorLabel() {
        return GameWindow.indicatorLabel;
    }

    static void setIndicatorLabel(String label) {
        GameWindow.indicatorLabel.setText(String.valueOf(label));
    }

    /**
     * Contains all the graphical elements outside of the main app window and game chooser (popups, messages etc)
     **/
    static void drawDetectedMessage() {
        int reply = JOptionPane.showConfirmDialog(null, "It's a draw! \n" +
                "\n Would you like to play again?", "Draw", JOptionPane.YES_NO_OPTION);

        if (reply == JOptionPane.YES_OPTION) {
            GameWindow.resetGame();
        } else {
            TicTacToe.shutdownApplication();
        }
    }

    static void winnerDetectedMessage(String winnerName) {
        int reply = JOptionPane.showConfirmDialog(null, "" + winnerName + " won the game! \n" +
                "\n Would you like to play again?", "Win", JOptionPane.YES_NO_OPTION);

        //If the user chooses Yes, reset the game
        if (reply == JOptionPane.YES_OPTION) {
            try {
                GameWindow.resetGame();
            } catch (NullPointerException ignored) {}
        } else {
            TicTacToe.shutdownApplication();
        }
    }

}