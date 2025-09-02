package SellWindow;

import entity.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Random;

public class SellWindow {

    // Array to store ingredient quantities, initialized to a default size or initial values
    private static int[] ingredientQuantitiesPit = new int[4];

    // GUI components
    private JPanel sellPanel;
    private JTextArea weatherTextArea;
    private JTextArea salesTextArea;

    // Constructor for SellWindow
    public SellWindow() {
        // Initialize GUI components
        sellPanel = new JPanel();
        weatherTextArea = new JTextArea(5, 20);
        salesTextArea = new JTextArea(10, 20);

        // Adding components to sellPanel
        sellPanel.add(new JLabel("Weather:"));
        sellPanel.add(weatherTextArea);
        sellPanel.add(new JLabel("Sprite Sales:"));
        sellPanel.add(salesTextArea);

        // Button to simulate sales
        JButton simulateButton = new JButton("Simulate Sales");
        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulateSales(); // Action when button is clicked
            }
        });
        sellPanel.add(simulateButton);

        // Setting up the JFrame
        JFrame frame = new JFrame("Sprite Sales Window");
        frame.getContentPane().add(sellPanel); // Adding panel to frame
        frame.pack(); // Packing components
        frame.setVisible(true); // Making frame visible
        frame.setLocationRelativeTo(null); // Centering frame on screen
    }

    // Method to simulate sales
    private void simulateSales() {
        // Simulate sales based on current weather
        String currentWeather = getCurrentWeather();
        int sales = calculateSales(currentWeather); // Calculate sales based on weather

        // Update UI with current weather and sales
        weatherTextArea.setText(currentWeather); // Display current weather
        salesTextArea.setText(String.valueOf(sales)); // Display sales

        // Calculate money earned from sales and update Player's money
        double mon = sales * 0.22; // Assuming each sale earns 22 cents
        String formattedMon = new DecimalFormat("0.00").format(mon); // Format money as string
        double formattedMonDouble = Double.parseDouble(formattedMon); // Parse formatted string to double

        String forMonPl = new DecimalFormat("0.00").format(Player.getMoney()); // Current money as string
        double doublePl = Double.parseDouble(forMonPl); // Parse current money to double

        Player.setMoney(formattedMonDouble + doublePl); // Update Player's money
    }

    // Method to get current weather randomly
    private String getCurrentWeather() {
        String[] weatherOptions = {"Sunny", "Rainy", "Cloudy", "Thunderstorm"};
        Random random = new Random();
        return weatherOptions[random.nextInt(weatherOptions.length)];
    }

    // Method to calculate sales based on weather
    private int calculateSales(String weather) {
        Random random = new Random();
        int numCustomers = 0;
        int i;

        int[] pitcherQuantities = getIngredientQuantities(); // Get quantities of ingredients

        if (pitcherQuantities == null) {
            i = 0; // Handle null case
        }

        int totalPitchers = 0;
        for (int pitchers : pitcherQuantities) {
            totalPitchers += pitchers;
        }
        i = totalPitchers * 12; // Each pitcher makes 12 drinks

        // Determine number of customers based on weather
        numCustomers = switch (weather) {
            case "Sunny" -> random.nextInt(16) + 95;
            case "Cloudy" -> random.nextInt(20) + 66;
            case "Rainy" -> random.nextInt(21) + 44;
            case "Thunderstorm" -> random.nextInt(21);
            default -> numCustomers;
        };

        // Calculate actual sales based on available pitchers and customers
        if (i > numCustomers) {
            return numCustomers;
        } else {
            return i - numCustomers;
        }
    }

    // Setter for ingredient quantities
    public static void setIngredientQuantities(int[] quantities) {
        ingredientQuantitiesPit = quantities;
    }

    // Getter for ingredient quantities
    public static int[] getIngredientQuantities() {
        return ingredientQuantitiesPit;
    }

}
