package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_BlueMan extends Entity {

    // Constructor
    public NPC_BlueMan(GamePanel gp) {
        super(gp);

        // Initialize NPC properties
        direction = "down"; // Initial direction
        speed = 2; // Movement speed

        // Load NPC image and set initial dialogue
        getNPCImage();
        setDialogue();
    }

    // Method to draw the NPC on screen
    public void draw(Graphics2D g2) {
        // Calculate NPC position relative to player's position and screen
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Check if NPC is within the visible screen area
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            BufferedImage image = null;

            // Set image based on direction and sprite number
            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            // Draw the NPC image on screen
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    // Method to load NPC images from resources
    public void getNPCImage() {
        String basePath = "/NPC/";
        try {
            // Load NPC images for different directions
            up1 = ImageIO.read(getClass().getResourceAsStream(basePath + "BlueMan_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream(basePath + "BlueMan_up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream(basePath + "BlueMan_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream(basePath + "BlueMan_down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream(basePath + "BlueMan_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream(basePath + "BlueMan_left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream(basePath + "BlueMan_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream(basePath + "BlueMan_right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to set NPC dialogue
    public void setDialogue() {
        // Set initial dialogue for the NPC
        dialogues[0] = "Welcome to the town of Montana. Touching any diamond plate will open the shop, touching wood will open rock, paper, " +
                "scissors, touching wall will open a place to change the recipe of the sprite, and touching grass is where you will sell the sprite to customers. Make as much money as you can. If you go bankrupt, just close the game. Good luck. :)";
    }

    // Method to set NPC action
    public void setAction() {
        // Increment action counter
        actionLookCounter++;

        // Perform action based on counter
        if (actionLookCounter == 90) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // Generate a random number from 1 to 100

            // Set NPC direction based on random number
            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else {
                direction = "right";
            }
            actionLookCounter = 0; // Reset action counter
        }
    }

    // Method for NPC to speak
    public void speak() {
        // Set current dialogue in UI to NPC's dialogue
        gp.ui.currentDialogue = dialogues[0];
    }
}
