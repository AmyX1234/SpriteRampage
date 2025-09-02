package BuyWindow;

import entity.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class BuyWindow {

    // GUI components
    private JPanel productsAndPrice;
    private JButton[] buyButtons;
    private JButton returnButton;
    private JLabel[] ingredientLabels;
    private JFrame buyFrame;
    private JLabel moneyLabel;

    // Main frame
    private JFrame frame;

    // Ingredient names and quantities
    private String[] ingredients = {"White Sugar", "Lemon Juice", "Lime Water", "Cups", "Ice"};
    private static int[] quantities = {IngredientList.getNumSugar(), IngredientList.getNumJuice(), IngredientList.getNumWater(), IngredientList.getNumCups(), IngredientList.getNumIce()}; // quantities the user has

    private int currentIngredientIndex = -1; // Track current ingredient being viewed

    public BuyWindow() {
        // Initialize UI components
        this.productsAndPrice = new JPanel();
        this.buyButtons = new JButton[5];
        this.ingredientLabels = new JLabel[5];
        this.returnButton = new JButton("Return to Ingredients");
        this.moneyLabel = new JLabel("Money: $" + new DecimalFormat("0.00").format(Player.getMoney()));
        setupUI(); // Set up the initial UI
    }

    private void setupUI() {
        productsAndPrice.removeAll(); // Clear previous components

        frame = new JFrame("Buy Products");
        frame.setSize(1000, 600); // Set main frame size
        productsAndPrice.setLayout(null); // Use null layout for manual component positioning

        moneyLabel.setBounds(50, 20, 200, 30); // Position money label
        productsAndPrice.add(moneyLabel);

        // Display ingredients and current quantities
        for (int i = 0; i < 5; i++) {
            ingredientLabels[i] = new JLabel(ingredients[i] + ": " + quantities[i]);
            ingredientLabels[i].setBounds(50, 50 + i * 30, 200, 30); // Position ingredient labels
            productsAndPrice.add(ingredientLabels[i]);

            buyButtons[i] = new JButton("Buy " + ingredients[i]);
            buyButtons[i].setBounds(300, 50 + i * 30, 150, 30); // Position buy buttons
            int index = i; // Needed for ActionListener
            buyButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showBuyUI(index); // Show detailed buy UI for selected ingredient
                }
            });
            productsAndPrice.add(buyButtons[i]);
        }

        returnButton.setBounds(50, 300, 200, 30); // Position return button
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIngredientIndex != -1) {
                    setupUI(); // Return to main UI
                    currentIngredientIndex = -1;
                    returnButton.setVisible(false); // Hide return button again
                }
            }
        });
        returnButton.setVisible(false); // Initially hidden
        productsAndPrice.add(returnButton);

        frame.add(productsAndPrice); // Add main panel to frame
        frame.setVisible(true); // Make the frame visible
        frame.setLocationRelativeTo(null); // Center frame on screen
    }

    private void showBuyUI(int index) {
        // Close previous buy window if exists
        if (buyFrame != null) {
            buyFrame.dispose(); // Dispose previous buy window
        }

        // Create new buy window for selected ingredient
        buyFrame = new JFrame("Buy " + ingredients[index]);
        buyFrame.setSize(400, 400); // Set buy window size
        buyFrame.setLocationRelativeTo(frame); // Position relative to main frame

        JPanel buyPanel = new JPanel();
        buyPanel.setLayout(null); // Use null layout for detailed component control

        JLabel label = new JLabel("Buying " + ingredients[index]);
        label.setBounds(50, 50, 200, 30); // Position label
        buyPanel.add(label);

        // Display options and prices based on ingredient index
        JComboBox<String> optionsComboBox;
        switch (index) {
            case 0: // White Sugar
                optionsComboBox = new JComboBox<>(IngredientPricing.sugarOptions);
                break;
            case 1: // Lemon Juice
                optionsComboBox = new JComboBox<>(IngredientPricing.lemonOptions);
                break;
            case 2: // Lime Water
                optionsComboBox = new JComboBox<>(IngredientPricing.limeWaterOptions);
                break;
            case 3: // Cups
                optionsComboBox = new JComboBox<>(IngredientPricing.cupsOptions);
                break;
            case 4: // Ice
                optionsComboBox = new JComboBox<>(IngredientPricing.iceOptions);
                break;
            default:
                optionsComboBox = new JComboBox<>();
                break;
        }
        optionsComboBox.setBounds(50, 100, 150, 30); // Position combo box
        buyPanel.add(optionsComboBox);

        JLabel priceLabel = new JLabel("Price: ");
        priceLabel.setBounds(50, 140, 150, 30); // Position price label
        buyPanel.add(priceLabel);

        // Check if money label is null
        if (moneyLabel == null) {
            moneyLabel = new JLabel("Money: $" + new DecimalFormat("0.00").format(Player.getMoney()));
            moneyLabel.setBounds(50, 250, 150, 30); // Position money label
            buyPanel.add(moneyLabel);
        } else {
            moneyLabel.setText("Money: $" + new DecimalFormat("0.00").format(Player.getMoney()));
        }

        JButton buyButton = new JButton("Buy");
        buyButton.setBounds(50, 180, 100, 30); // Position buy button
        buyPanel.add(buyButton);

        // ActionListener for buy button
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = optionsComboBox.getSelectedIndex();
                double totalPrice;

                // Calculate total price based on selected option
                switch (index) {
                    case 0: // White Sugar
                        totalPrice = IngredientPricing.sugarPrices[selectedIndex];
                        String sugarOption = IngredientPricing.sugarOptions[selectedIndex];
                        int quantitySugar = Integer.parseInt(sugarOption.split(" ")[0]);
                        quantities[index] += quantitySugar;
                        IngredientList.setNumSugar(quantitySugar);
                        break;
                    case 1: // Lemon Juice
                        totalPrice = IngredientPricing.lemonPrices[selectedIndex];
                        String lemonOption = IngredientPricing.lemonOptions[selectedIndex];
                        int quantityLemon = Integer.parseInt(lemonOption.split(" ")[0]);
                        int tablespoons = quantityLemon * 3; // Convert lemons to tablespoons
                        quantities[index] += tablespoons;
                        IngredientList.setNumJuice(tablespoons);
                        break;
                    case 2: // Lime Water
                        totalPrice = IngredientPricing.limeWaterPrices[selectedIndex];
                        String limeWaterOption = IngredientPricing.limeWaterOptions[selectedIndex];
                        int quantityLimeWater = Integer.parseInt(limeWaterOption.split(" ")[0]);
                        quantities[index] += quantityLimeWater;
                        IngredientList.setNumWater(quantityLimeWater);
                        break;
                    case 3: // Cups
                        totalPrice = IngredientPricing.cupsPrices[selectedIndex];
                        String cupsOption = IngredientPricing.cupsOptions[selectedIndex];
                        int quantityCups = Integer.parseInt(cupsOption.split(" ")[0]);
                        quantities[index] += quantityCups;
                        IngredientList.setNumCups(quantityCups);
                        break;
                    case 4: // Ice
                        totalPrice = IngredientPricing.icePrices[selectedIndex];
                        String iceOption = IngredientPricing.iceOptions[selectedIndex];
                        int quantityIce = Integer.parseInt(iceOption.split(" ")[0]);
                        quantities[index] += quantityIce;
                        IngredientList.setNumIce(quantityIce);
                        break;
                    default:
                        totalPrice = 0.0;
                        break;
                }

                // Check if player has enough money
                if (Player.getMoney() >= totalPrice) {
                    Player.setMoney(Player.getMoney() - totalPrice); // Deduct money
                    moneyLabel.setText("Money: $" + new DecimalFormat("0.00").format(Player.getMoney())); // Update money label
                    ingredientLabels[index].setText(ingredients[index] + ": " + quantities[index]); // Update ingredient label
                    priceLabel.setText("Price: $" + new DecimalFormat("0.00").format(totalPrice)); // Update price label
                } else {
                    JOptionPane.showMessageDialog(buyFrame, "Not enough money to buy " + ingredients[index], "Insufficient Funds", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton returnButton = new JButton("Return to Ingredients");
        returnButton.setBounds(50, 220, 200, 30); // Position return button
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyFrame.dispose(); // Close buy window
            }
        });
        buyPanel.add(returnButton);

        buyFrame.add(buyPanel); // Add buy panel to buy frame
        buyFrame.setVisible(true); // Make buy frame visible
    }
}
