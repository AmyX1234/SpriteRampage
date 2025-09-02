package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    public String currentDialogue = "";
    public int commandNum = 0;
    private BufferedImage backgroundImage;
    int subState = 0;

    // Constructor to initialize the UI with the GamePanel object
    public UI(GamePanel gp) {
        this.gp = gp;

        // Setting up fonts
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    // Method to draw different UI components based on the game state
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        // Draw title screen if game is in title state
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // Draw pause screen if game is in pause state
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        // Draw dialogue screen if game is in dialogue state
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
        // Draw menu screen if game is in menu state
        if (gp.gameState == gp.menuState) {
            drawMenuScreen();
        }
    }

    // Method to draw the title screen
    private void drawTitleScreen() {
        try {
            // Load background image
            backgroundImage = ImageIO.read(new File("Source Folder/Menu/Sprite Rampage Menu image_1scale_32_32.png")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (backgroundImage != null) {
            // Calculate scaling ratios
            double scaleX = (double) gp.screenWidth / backgroundImage.getWidth();
            double scaleY = (double) gp.screenHeight / backgroundImage.getHeight();

            // Use the smaller scale to maintain aspect ratio and fit the screen
            double scale = Math.min(scaleX, scaleY);

            // Calculate new width and height based on the scale
            int scaledWidth = (int) (backgroundImage.getWidth() * scale);
            int scaledHeight = (int) (backgroundImage.getHeight() * scale);

            // Calculate position to center the image on the screen
            int x = (gp.screenWidth - scaledWidth) / 2;
            int y = (gp.screenHeight - scaledHeight) / 2;

            // Draw the scaled image
            g2.drawImage(backgroundImage, x, y, scaledWidth, scaledHeight, null);
        } else {
            // Handle image loading failure
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            g2.setColor(Color.WHITE);
            g2.setFont(arial_40);
            String errorMessage = "Failed to load background image";
            int x = getXforCenteredText(errorMessage);
            int y = gp.screenHeight / 2;
            g2.drawString(errorMessage, x, y);
        }
    }

    // Method to draw the pause screen
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";

        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    // Method to draw the dialogue screen
    public void drawDialogueScreen() {
        // Calculate the size of the subwindow based on the text
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);

        // Use a temporary Graphics2D object to calculate text dimensions
        BufferedImage tempImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2Temp = tempImg.createGraphics();
        g2Temp.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        ArrayList<String> lines = getWrappedText(currentDialogue, width - gp.tileSize * 2, g2Temp);
        g2Temp.dispose();

        int lineHeight = 40;
        int height = (lines.size() * lineHeight) + gp.tileSize;

        drawSubWindow(x, y, width, height);

        // Draw the text
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        int textX = x + gp.tileSize;
        int textY = y + gp.tileSize;

        for (String line : lines) {
            g2.drawString(line, textX, textY);
            textY += lineHeight;
        }
    }

    // Method to draw a subwindow with rounded corners and border
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    // Method to draw the menu screen
    public void drawMenuScreen() {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        // Subwindow for the menu
        int frameX = gp.tileSize * 4;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Switch between different menu states
        switch (subState) {
            case 0:
                menu_top(frameX, frameY);
                break;
            case 1:
                menu_control(frameX, frameY);
                break;
            case 2:
                menu_Credits(frameX, frameY);
                break;
            case 3:
                menu_endGame(frameX, frameY);
                break;
        }
        gp.keyH.enterPressed = false;
    }

    // Method to draw the top menu
    public void menu_top(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // Control option
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Control", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 1;
                commandNum = 0;
            }
        }

        // Credits option
        textY += gp.tileSize;
        g2.drawString("Credits", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }

        // End game option
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 3;
                commandNum = 0;
            }
        }

        // Back option
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }
    }

    // Method to calculate the x-coordinate for centered text
    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    // Method to wrap text based on a specified maximum width
    public ArrayList<String> getWrappedText(String text, int maxWidth, Graphics2D g2) {
        ArrayList<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            String testLine = line + word + " ";
            // Calculate the width of the current line
            int lineWidth = g2.getFontMetrics().stringWidth(testLine);

            // Check if adding this word exceeds the maximum width
            if (lineWidth > maxWidth) {
                lines.add(line.toString());  // Add current line to list
                line = new StringBuilder(word + " "); // Start a new line with the current word
            } else {
                line.append(word).append(" ");  // Continue building the current line
            }
        }

        // Add the last line
        lines.add(line.toString());

        return lines;  // Return the list of wrapped lines
    }

    // Method to draw the control menu
    public void menu_control(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // Control options
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Swi Char", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Pause", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Menu", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Test Speed", textX, textY);
        textY += gp.tileSize;

        // Corresponding controls
        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX, textY);
        textY += gp.tileSize;
        g2.drawString("V", textX, textY);
        textY += gp.tileSize;
        g2.drawString("P", textX, textY);
        textY += gp.tileSize;
        g2.drawString("M", textX, textY);
        textY += gp.tileSize;
        g2.drawString("T", textX, textY);
        textY += gp.tileSize;

        // Back option
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
            }
        }
    }

    // Method to draw the credits menu
    public void menu_Credits(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "Credits";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // Credits information
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Developer", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Producer", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Graphics", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Drawings", textX, textY);
        textY += gp.tileSize;

        // Corresponding names (placeholder "Amy X" used here)
        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("Amy X", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Amy X", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Amy X", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Amy X", textX, textY);
        textY += gp.tileSize;

        // Back option
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
            }
        }
    }

    // Method to draw the end game confirmation menu
    public void menu_endGame(int frameX, int frameY) {
        int textX = frameX + gp.tileSize / 2;
        int textY = frameY + gp.tileSize * 3;

        // Confirmation message
        currentDialogue = "Quit the game and \nreturn to the title screen?";

        // Draw confirmation message (with line breaks)
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Yes option
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                gp.gameState = gp.titleState;  // Return to title screen
            }
        }

        // No option
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 4;  // Placeholder action for "No"
            }
        }
    }
}
