package com.adityant.ticactoe;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class ModeSelector {

	private JComboBox<String> gameModeComboBox;
	private JComboBox<String> playerModeComboBox;

	ModeSelector() {
		//GameModeChooser frame properties
		final JFrame gameModeChooserFrame = new JFrame();
		gameModeChooserFrame.setSize(300, 250);
		gameModeChooserFrame.setBackground(Color.white);
		gameModeChooserFrame.setTitle("Game Mode Selector");
		gameModeChooserFrame.setResizable(false);
		gameModeChooserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameModeChooserFrame.setLocationRelativeTo(null);

		//panel properties
		JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		masterPanel.setSize(300, 220);
		masterPanel.setBorder(new EmptyBorder(10,10,10,10));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		int paddingSize = 5;
		topPanel.setBorder(new EmptyBorder(paddingSize, 0, paddingSize, 0));

		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		middlePanel.setBorder(new EmptyBorder(paddingSize, 0, paddingSize, 0));

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(new EmptyBorder(paddingSize, 0, paddingSize, 0));

		//gameTitleLabel properties
		JLabel gameTitleLabel = new JLabel();
		gameTitleLabel.setText(".: Tic Tac Toe :.");
		gameTitleLabel.setFont(gameTitleLabel.getFont().deriveFont(26.0f));
		gameTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		//gameModeLabel properties
		JLabel gameModeLabel = new JLabel();
		gameModeLabel.setText("\nChoose your preferred game mode");
		gameModeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		//playerModeLabel properties
		JLabel playerModeLabel = new JLabel();
		playerModeLabel.setText("Choose who should go first");
		playerModeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		//gameModeComboBox properties
		//selected option is processed when user presses start
		String[] gameModeOptions = {"Player vs Player", "Player vs Computer"};
		gameModeComboBox = new JComboBox<>(gameModeOptions);

		//playerModeComboBox properties
		String[] playerModeOptions = {"Player 1", "Player 2 (or Computer)"};
		playerModeComboBox = new JComboBox<>(playerModeOptions);

		//Buttons
		JButton startButton = new JButton();
		startButton.setBackground(Color.white);
		startButton.setText("Start");

		startButton.addActionListener(e -> {
			//Assign combo-box values to main variables governing the game
			TicTacToe.gameMode = (String) gameModeComboBox.getSelectedItem();
			TicTacToe.playerMode = (String) playerModeComboBox.getSelectedItem();
			gameModeChooserFrame.dispose();
			TicTacToe.beginGame();
		});

		JButton helpButton = new JButton();
		helpButton.setBackground(Color.white);
		helpButton.setText("Help");
//		helpButton.addActionListener(e -> GameWindow.displayHelp());

		JButton quitButton = new JButton();
		quitButton.setBackground(Color.white);
		quitButton.setText("Quit");
		quitButton.addActionListener(e -> TicTacToe.shutdownApplication());

		topPanel.add(gameTitleLabel);
		topPanel.add(gameModeLabel);
		topPanel.add(Box.createVerticalStrut(10));
		topPanel.add(gameModeComboBox);
		middlePanel.add(playerModeLabel);
		middlePanel.add(Box.createVerticalStrut(10));
		middlePanel.add(playerModeComboBox);
		bottomPanel.add(startButton);
		bottomPanel.add(helpButton);
		bottomPanel.add(quitButton);

		masterPanel.add(topPanel);
		masterPanel.add(middlePanel);
		masterPanel.add(bottomPanel);

		masterPanel.setVisible(true);
		topPanel.setVisible(true);
		middlePanel.setVisible(true);
		bottomPanel.setVisible(true);
		gameModeChooserFrame.add(masterPanel);
		gameModeChooserFrame.setVisible(true);
	}
}