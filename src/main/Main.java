package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Create a window for the game
        JFrame window = new JFrame();

        // Set the behavior for closing the window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prevent resizing of the window
        window.setResizable(false);

        // Set the title of the window
        window.setTitle("Sprite Rampage");

        // Create a panel for the game
        GamePanel gamePanel = new GamePanel();

        // Add the game panel to the window
        window.add(gamePanel);

        // Size the window to fit the game panel
        window.pack();

        // Center the window on the screen
        window.setLocationRelativeTo(null);

        // Make the window visible
        window.setVisible(true);

        gamePanel.setUpGame();

        // Start the game thread
        gamePanel.startGameThread();
    }
}