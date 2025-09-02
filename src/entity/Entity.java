package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    GamePanel gp;

    // Entity's position in the world
    public int worldX, worldY;

    // Entity's speed and direction of movement
    public int speed;
    public String direction;

    // Flags for collision, buy, and sell window
    public boolean collisionOn = false;

    // Images for entity's movement sprites
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    public int solidAreaDefaultX, solidAreaDefaultY;

    // Sprite animation variables
    public int spriteCounter = 0; // Counter for sprite animation
    public int spriteNum = 1; // Number of sprites for animation
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Area representing entity's collision bounds
    public int actionLookCounter = 0;
    String dialogues[] = new String[2];

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){}

    public void update(){

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkPlayer(this);

        //if collision is false, npc can move

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
    public void speak(){}
}
