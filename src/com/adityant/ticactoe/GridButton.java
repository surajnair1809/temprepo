package com.adityant.ticactoe;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GridButton extends JButton implements ActionListener {
    static ImageIcon iconX, iconO;

    public GridButton() {
        Color myColor = new Color(52, 73, 94);

        this.setBackground(myColor);
        try {
            iconX = new ImageIcon(ImageIO.read(GameWindow.class.getResource("resources/X.png")));
            iconO = new ImageIcon(ImageIO.read(GameWindow.class.getResource("resources/O.png")));
        } catch (IOException e) {
            System.out.println("One or more of the icon images is missing from the resources folder.");
        }
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object click = e.getSource();

        if(TicTacToe.gameMode == null) return;

        for (int i = 0; i < GameWindow.button.length; i++) {
            if (click == GameWindow.button[i]) {
                GameWindow.button[i].setEnabled(false);
                Tile userMove = new Tile(i / 3, i % 3);
                TicTacToe.submitmove(userMove);
                break;
            }
        }
    }
}
