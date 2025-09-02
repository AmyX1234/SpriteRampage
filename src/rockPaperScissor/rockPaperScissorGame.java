package rockPaperScissor;

import entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Random;

// Main class for the Rock Paper Scissors game
public class rockPaperScissorGame extends JFrame implements ActionListener {
    // GUI components
    private JButton rockButton, paperButton, scissorsButton;
    private JLabel resultLabel, balanceLabel;

    // Constructor to set up the game window
    public rockPaperScissorGame() {
        setTitle("Rock Paper Scissors Game"); // Set window title
        setSize(650, 400); // Set window size
        setLayout(new FlowLayout()); // Set layout manager
        setLocationRelativeTo(null);
        Font font = new Font("Arial", Font.PLAIN, 24); //set a font and size of text


        // Create buttons for Rock, Paper, and Scissors
        rockButton = new JButton("Rock");
        paperButton = new JButton("Paper");
        scissorsButton = new JButton("Scissors");

        // Set the font for the buttons
        rockButton.setFont(font);
        paperButton.setFont(font);
        scissorsButton.setFont(font);

        // Add action listeners to the buttons
        rockButton.addActionListener(this);
        paperButton.addActionListener(this);
        scissorsButton.addActionListener(this);

        // Create labels to display the result and balance
        resultLabel = new JLabel("Choose Rock, Paper, or Scissors");
        balanceLabel = new JLabel("Balance: $" + new DecimalFormat("0.00").format(Player.getMoney()));

        resultLabel.setFont(font);
        balanceLabel.setFont(font);

        // Add buttons and labels to the frame
        add(rockButton);
        add(paperButton);
        add(scissorsButton);
        add(resultLabel);
        add(balanceLabel);
    }

    // Method called when a button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        String userChoice = e.getActionCommand(); // Get user's choice
        String computerChoice = getComputerChoice(); // Get computer's random choice
        String result = determineWinner(userChoice, computerChoice); // Determine the winner

        // Update balance if user wins
        if (result.equals("Win")) {
            Player.setMoney(Player.getMoney() + 2.20);
        }else if (result.equals("Lose")){
            Player.setMoney(Player.getMoney() - 1.00);
        }

        // Update the result and balance labels
        resultLabel.setText("You chose " + userChoice + ". Computer chose " + computerChoice + ". You " + result + "!");
        balanceLabel.setText("Balance: $" + new DecimalFormat("0.00").format(Player.getMoney()));
    }

    // Method to get the computer's choice
    private String getComputerChoice() {
        Random random = new Random();
        int choice = random.nextInt(3) + 1; // Generate random number between 1 and 3
        switch (choice) {
            case 1:
                return "Rock";
            case 2:
                return "Paper";
            case 3:
                return "Scissors";
        }
        return null; // Default return value (should not be reached)
    }

    // Method to determine the winner
    private String determineWinner(String userChoice, String computerChoice) {
        if (userChoice.equals(computerChoice)) {
            return "Draw"; // It's a tie
        } else if ((userChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                (userChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                (userChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            return "Win"; // User wins
        } else {
            return "Lose"; // Computer wins
        }
    }

    // Main method to start the game
    public static void startGame() {
        // Run the game in the Swing event dispatch thread
        SwingUtilities.invokeLater(() -> {
            new rockPaperScissorGame().setVisible(true); // Create and display the game window
        });
    }
}
