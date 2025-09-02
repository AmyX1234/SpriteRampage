package collision;
import BuyWindow.BuyWindow;
import SellWindow.SellWindow;
import entity.Entity;
import main.GamePanel;
import rockPaperScissor.rockPaperScissorGame;

import java.util.Timer;
import java.util.TimerTask;

import makeSprite.makeSprite;

public class CollisionChecker {

    private GamePanel gamepanel;
    private static boolean buyOpen = false;
    private static boolean makeOpen = false;
    private static Timer OpenTimer;
    private static boolean RPSGOpen = false;
    private static boolean sellOpen = false;

    // Constructor
    public CollisionChecker(GamePanel gp) {
        gamepanel = gp;
    }

    // Method to check collision with tiles for the given entity
    public void checkTile(Entity entity) {
        // Calculate entity's boundaries
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Calculate the tile positions of the entity's boundaries
        int entityLeftCol = entityLeftWorldX / gamepanel.tileSize; // Column of the map the entity's left side is in
        int entityRightCol = entityRightWorldX / gamepanel.tileSize;
        int entityTopRow = entityTopWorldY / gamepanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamepanel.tileSize;

        int tileNum1, tileNum2;
        // Check collision based on entity's direction
        if (entity.direction.equals("up")) {
            // Adjust top row position based on entity's speed
            entityTopRow = (entityTopWorldY - entity.speed) / gamepanel.tileSize;
            // Get tile numbers at the adjusted positions
            tileNum1 = gamepanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gamepanel.tileM.mapTileNum[entityRightCol][entityTopRow];
            // Check for collision and trigger battle event if necessary
            if (gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            if (gamepanel.tileM.tile[tileNum1].buy || gamepanel.tileM.tile[tileNum2].buy) {
                handleBuyWindow(); // Open BuyWindow if buy tile is detected
            }
            if (gamepanel.tileM.tile[tileNum1].RPSG || gamepanel.tileM.tile[tileNum2].RPSG) {
                startRPSG(); // Start Rock Paper Scissors game if RPSG tile is detected
            }
            if (gamepanel.tileM.tile[tileNum1].make || gamepanel.tileM.tile[tileNum2].make) {
                startSprite(); // Start makeSprite if make tile is detected
            }
            if (gamepanel.tileM.tile[tileNum1].sell || gamepanel.tileM.tile[tileNum2].sell) {
                startSell(); // Open SellWindow if sell tile is detected
            }
        } else if (entity.direction.equals("down")) {
            // Adjust bottom row position based on entity's speed
            entityBottomRow = (entityBottomWorldY + entity.speed) / gamepanel.tileSize;
            // Get tile numbers at the adjusted positions
            tileNum1 = gamepanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gamepanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
            // Check for collision and trigger battle event if necessary
            if (gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            if (gamepanel.tileM.tile[tileNum1].buy || gamepanel.tileM.tile[tileNum2].buy) {
                handleBuyWindow(); // Open BuyWindow if buy tile is detected
            }
            if (gamepanel.tileM.tile[tileNum1].RPSG || gamepanel.tileM.tile[tileNum2].RPSG) {
                startRPSG(); // Start Rock Paper Scissors game if RPSG tile is detected
            }
            if (gamepanel.tileM.tile[tileNum1].make || gamepanel.tileM.tile[tileNum2].make) {
                startSprite(); // Start makeSprite if make tile is detected
            }
            if (gamepanel.tileM.tile[tileNum1].sell || gamepanel.tileM.tile[tileNum2].sell) {
                startSell(); // Open SellWindow if sell tile is detected
            }
        } else if (entity.direction.equals("left")) {
            // Adjust left column position based on entity's speed
            entityLeftCol = (entityLeftWorldX - entity.speed) / gamepanel.tileSize;
            // Get tile numbers at the adjusted positions
            tileNum1 = gamepanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gamepanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            // Check for collision and trigger battle event if necessary
            if (gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            if (gamepanel.tileM.tile[tileNum1].buy || gamepanel.tileM.tile[tileNum2].buy) {
                handleBuyWindow(); // Open BuyWindow if buy tile is detected
            }
            if (gamepanel.tileM.tile[tileNum1].RPSG || gamepanel.tileM.tile[tileNum2].RPSG) {
                startRPSG(); // Start Rock Paper Scissors game if RPSG tile is detected
            }
            if (gamepanel.tileM.tile[tileNum1].make || gamepanel.tileM.tile[tileNum2].make) {
                startSprite(); // Start makeSprite if make tile is detected
            }
            if (gamepanel.tileM.tile[tileNum1].sell || gamepanel.tileM.tile[tileNum2].sell) {
                startSell(); // Open SellWindow if sell tile is detected
            }
        } else if (entity.direction.equals("right")) {
            // Adjust right column position based on entity's speed
            entityRightCol = (entityRightWorldX + entity.speed) / gamepanel.tileSize;
            // Get tile numbers at the adjusted positions
            tileNum1 = gamepanel.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gamepanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
            // Check for collision and trigger battle event if necessary
            if (gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            if (gamepanel.tileM.tile[tileNum1].buy || gamepanel.tileM.tile[tileNum2].buy) {
                handleBuyWindow(); // Open BuyWindow if buy tile is detected
            }
            if (gamepanel.tileM.tile[tileNum1].RPSG || gamepanel.tileM.tile[tileNum2].RPSG) {
                startRPSG(); // Start Rock Paper Scissors game if RPSG tile is detected
            }
            if (gamepanel.tileM.tile[tileNum1].make || gamepanel.tileM.tile[tileNum2].make) {
                startSprite(); // Start makeSprite if make tile is detected
            }
            if (gamepanel.tileM.tile[tileNum1].sell || gamepanel.tileM.tile[tileNum2].sell) {
                startSell(); // Open SellWindow if sell tile is detected
            }
        }
    }

    // Method to handle showing the BuyWindow based on entity direction
    private void handleBuyWindow() {
        if (!buyOpen) {
            buyOpen = true;
            new BuyWindow();
            startOpenTimer(); // Start timer to close windows after some time
        }
    }

    // Method to start the timer that resets open windows after 10 seconds
    public static void startOpenTimer() {
        if (OpenTimer != null) {
            OpenTimer.cancel();
        }
        OpenTimer = new Timer();
        OpenTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                buyOpen = false;
                RPSGOpen = false;
                makeOpen = false;
                sellOpen = false;
                OpenTimer.cancel();
            }
        }, 4000); // 4 seconds delay
    }

    // Method to start the Rock Paper Scissors game
    private void startRPSG() {
        if (!RPSGOpen) {
            RPSGOpen = true;
            rockPaperScissorGame.startGame();
            startOpenTimer(); // Start timer to close windows after some time
        }
    }

    // Method to start the makeSprite window
    private void startSprite() {
        synchronized (this) {
            if (!makeOpen) {
                makeOpen = true;
                new makeSprite();
            }
        }
    }

    // Method to start the SellWindow
    private void startSell() {
        if (!sellOpen) {
            sellOpen = true;
            new SellWindow();
            startOpenTimer(); // Start timer to close windows after some time
        }
    }

    // Method to check collision with NPC or monster entities
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // Get the target's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                }
                // Reset solid area positions
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    // Method to check collision with the player
    public void checkPlayer(Entity entity) {
        // Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        // Get the player's solid area position
        gamepanel.player.solidArea.x = gamepanel.player.worldX + gamepanel.player.solidArea.x;
        gamepanel.player.solidArea.y = gamepanel.player.worldY + gamepanel.player.solidArea.y;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                if (entity.solidArea.intersects(gamepanel.player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                if (entity.solidArea.intersects(gamepanel.player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                if (entity.solidArea.intersects(gamepanel.player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gamepanel.player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
        }
        // Reset solid area positions
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gamepanel.player.solidArea.x = gamepanel.player.solidAreaDefaultX;
        gamepanel.player.solidArea.y = gamepanel.player.solidAreaDefaultY;
    }
}
