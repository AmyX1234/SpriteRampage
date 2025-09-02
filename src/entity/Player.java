package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity { // Player class extending Entity

    // Instance variables
    KeyHandler keyH;
    private String selectedPlayer = "Jeff";
    private int player;
    private static double money = 25.00;

    // Player screen position
    public final int screenX;
    public final int screenY;

    // Constructor
    public Player(GamePanel gp, KeyHandler keyH, String choice) {
        super(gp);
        this.keyH = keyH;

        // Calculate screen position
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Set solid area for collision
        solidArea = new Rectangle();
        solidArea.x = 8; // Hitbox in the middle of player's boundaries
        solidArea.y = 16; // Hitbox at the lower end of player's boundaries
        solidArea.width = 32; // Less than the tile size to make sure the player can move easily
        solidArea.height = 32;

        // Set default values
        setDefaultValues();
        // Get player image based on choice
        getPlayerImage(choice);
    }

    // Method to set default values
    public void setDefaultValues() {
        worldX = gp.tileSize * 14;
        worldY = gp.tileSize * 43;
        speed = 4;
        direction = "down"; // Default direction
    }

    // Method to get player image based on choice
    public void getPlayerImage(String choice) {
        String basePath = "/Player/";
        try {
            // Load player images for different directions
            up1 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to update player state
    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // Check collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Check NPC collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // Move player if no collision
            if (!collisionOn && direction != null) {
                switch (direction) {
                    case "up":
                        if (worldY - speed >= 0) {
                            worldY -= speed;
                        }
                        break;
                    case "down":
                        if (worldY + gp.tileSize + speed <= gp.maxWorldHeight * gp.tileSize) {
                            worldY += speed;
                        }
                        break;
                    case "left":
                        if (worldX - speed >= 0) {
                            worldX -= speed;
                        }
                        break;
                    case "right":
                        if (worldX + gp.tileSize + speed <= gp.maxWorldWidth * gp.tileSize) {
                            worldX += speed;
                        }
                        break;
                    default:
                        direction = "down";
                }
            }
            // Update sprite animation
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        // Check if switch character button is pressed
        if (keyH.switchCharacterPressed) {
            // Show dialog to switch character
            String[] options = {"Jeff", "Riz"};
            int choiceIndex = JOptionPane.showOptionDialog(
                    null,
                    "Who would you like to switch to?",
                    "Switch Character",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    JOptionPane.CLOSED_OPTION // Set CLOSE_OPTION as default
            );
            // Handle player's choice
            if (choiceIndex == -1) {
                choiceIndex = player;
            }
            selectedPlayer = options[choiceIndex];
            getPlayerImage(selectedPlayer);
            keyH.switchCharacterPressed = false;
            player = choiceIndex;
        }
    }

    // Method to interact with NPC
    public void interactNPC(int i) {
        if (i != 999) {
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
        }
    }

    // Method to draw player
    public void draw(Graphics2D g2) {
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

        // Draw player image
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    // Getter method for money
    public static double getMoney() {
        return money;
    }

    // Setter method for money
    public static void setMoney(double m) {
        money = m;
    }

}
