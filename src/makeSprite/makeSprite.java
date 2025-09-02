package makeSprite;

import BuyWindow.IngredientList;
import SellWindow.SellWindow;
import collision.CollisionChecker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class makeSprite {

    // Initial quantities of ingredients in the pits
    private static int numSugarPit = 4;
    private static int numJuicePit = 4;
    private static int numWaterPit = 4;
    private static int numIcePit = 4;

    // GUI components
    private JPanel spritePanel;
    private JFrame frame;
    private JLabel[] ingredientLabels;
    private JButton[] plusButtons;
    private JButton[] minusButtons;
    private JTextField[] quantityFields;
    private JButton saveButton;
    private JButton returnButton;
    private JLabel numPitchersLabel;
    private JList<String> quantityList;
    private DefaultListModel<String> listModel;

    // Ingredients and their current quantities
    private String[] ingredients = {"White Sugar", "Lemon Juice", "Lime Water", "Ice"};
    private int[] ingredientQuantities = {
            IngredientList.getNumSugar(),
            IngredientList.getNumJuice(),
            IngredientList.getNumWater(),
            IngredientList.getNumIce()
    };

    // Amounts to be used in each pitcher
    private int[] pitcherQuantities = {numSugarPit, numJuicePit, numWaterPit, numIcePit};

    // Constructor to initialize the UI
    public makeSprite() {
        this.spritePanel = new JPanel();
        this.ingredientLabels = new JLabel[4];
        this.plusButtons = new JButton[4];
        this.minusButtons = new JButton[4];
        this.quantityFields = new JTextField[4];
        this.saveButton = new JButton("Save");
        this.returnButton = new JButton("Return to Main");
        this.numPitchersLabel = new JLabel();
        this.listModel = new DefaultListModel<>();
        this.quantityList = new JList<>(listModel);
        setupUI();
    }

    // Method to set up the graphical user interface
    private void setupUI() {
        spritePanel.removeAll(); // Clear any existing components from the panel

        frame = new JFrame("Make Sprite");
        frame.setSize(1000, 600);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CollisionChecker.startOpenTimer(); // Start a timer on window close
            }
        });
        spritePanel.setLayout(null); // Using absolute positioning

        // Display ingredients and quantities with plus and minus buttons
        for (int i = 0; i < 4; i++) {
            // Labels for each ingredient
            ingredientLabels[i] = new JLabel(ingredients[i] + ":");
            ingredientLabels[i].setBounds(50, 50 + i * 50, 100, 30);
            spritePanel.add(ingredientLabels[i]);

            // Minus button for reducing quantity
            minusButtons[i] = new JButton("-");
            minusButtons[i].setBounds(150, 50 + i * 50, 50, 30);
            final int index = i; // Index of the current ingredient
            minusButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (pitcherQuantities[index] > 0) {
                        pitcherQuantities[index]--;
                        quantityFields[index].setText(String.valueOf(pitcherQuantities[index]));
                        updateListModel(); // Update the list model after modification
                    }
                }
            });
            spritePanel.add(minusButtons[i]);

            // Text field to display and edit quantity
            quantityFields[i] = new JTextField(String.valueOf(pitcherQuantities[i]));
            quantityFields[i].setBounds(200, 50 + i * 50, 50, 30);
            quantityFields[i].setEditable(false); // Quantity field is not editable
            spritePanel.add(quantityFields[i]);

            // Plus button for increasing quantity
            plusButtons[i] = new JButton("+");
            plusButtons[i].setBounds(250, 50 + i * 50, 50, 30);
            plusButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ingredientQuantities[index] = getUpdatedIngredientQuantity(index); // Refresh the ingredient quantity
                    if (pitcherQuantities[index] < ingredientQuantities[index]) {
                        pitcherQuantities[index]++;
                        quantityFields[index].setText(String.valueOf(pitcherQuantities[index]));
                        updateListModel(); // Update the list model after modification
                    } else {
                        JOptionPane.showMessageDialog(frame, "Not enough " + ingredients[index] + " in stock.");
                    }
                }
            });
            spritePanel.add(plusButtons[i]);
        }

        // Save button to save ingredient quantities
        saveButton.setBounds(50, 300, 100, 30);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveIngredientQuantities(); // Save ingredient quantities
                saveIngredientNext(); // Save ingredient quantities for next use
                JOptionPane.showMessageDialog(frame, "Ingredient quantities saved successfully!");
            }
        });
        spritePanel.add(saveButton);

        // Return button to return to main window
        returnButton.setBounds(200, 300, 150, 30);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the MakeSprite window
                CollisionChecker.startOpenTimer(); // Start a timer
            }
        });
        spritePanel.add(returnButton);

        // Label displaying the number of pitchers that can be made
        numPitchersLabel.setBounds(50, 350, 300, 30);
        spritePanel.add(numPitchersLabel);

        // List displaying the current quantities of each ingredient
        updateListModel(); // Update the list model initially
        quantityList.setBounds(400, 50, 300, 150);
        spritePanel.add(quantityList);

        frame.add(spritePanel); // Add panel to frame
        frame.setVisible(true); // Make the frame visible
        frame.setLocationRelativeTo(null); // Center the frame on the screen
    }

    // Update the list model with current ingredient quantities
    private void updateListModel() {
        listModel.clear(); // Clear existing items in the list model
        for (int i = 0; i < 4; i++) {
            ingredientQuantities[i] = getUpdatedIngredientQuantity(i); // Refresh the ingredient quantity
            listModel.addElement(ingredients[i] + ": " + ingredientQuantities[i] + " (Available), " + pitcherQuantities[i] + " (In Pitcher)");
        }
        updateNumPitchersLabel(); // Update the label showing the number of pitchers that can be made
    }

    // Get the updated quantity of a specific ingredient from IngredientList
    private int getUpdatedIngredientQuantity(int index) {
        return switch (index) {
            case 0 -> IngredientList.getNumSugar();
            case 1 -> IngredientList.getNumJuice();
            case 2 -> IngredientList.getNumWater();
            case 3 -> IngredientList.getNumIce();
            default -> 0;
        };
    }

    // Save the current pitcher quantities for use in another window
    private void saveIngredientQuantities() {
        SellWindow.setIngredientQuantities(pitcherQuantities);
    }

    // Save current pitcher quantities to static variables for next use
    private void saveIngredientNext() {
        numSugarPit = pitcherQuantities[0];
        numJuicePit = pitcherQuantities[1];
        numWaterPit = pitcherQuantities[2];
        numIcePit = pitcherQuantities[3];
    }

    // Update the label showing the number of full pitchers that can be made
    private void updateNumPitchersLabel() {
        int numPitchers = calculateNumPitchers(); // Calculate the number of full pitchers that can be made
        numPitchersLabel.setText("Number of full pitchers that can be made: " + numPitchers);
    }

    // Calculate the number of full pitchers that can be made based on current ingredient quantities
    private int calculateNumPitchers() {
        int numPitchers = Integer.MAX_VALUE; // Start with a very large number
        for (int i = 0; i < 4; i++) {
            int available = getUpdatedIngredientQuantity(i); // Get current available quantity of the ingredient
            if (pitcherQuantities[i] > 0) {
                numPitchers = Math.min(numPitchers, available / pitcherQuantities[i]); // Calculate possible full pitchers
            }
        }
        if (numPitchers == Integer.MAX_VALUE) {
            numPitchers = 0; // Set to 0 if no full pitchers can be made
        }
        return numPitchers;
    }
}
