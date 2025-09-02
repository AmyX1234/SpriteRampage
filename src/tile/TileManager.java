package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamepanel;
    public Tile[] tile; // Array to store different tile types
    public int[][] mapTileNum; // 2D array to store tile numbers for the map

    // Constructor
    public TileManager(GamePanel gp) {
        gamepanel = gp;
        tile = new Tile[11]; // Initialize tile array with size 10
        mapTileNum = new int[gamepanel.maxWorldCol][gamepanel.maxWorldRow]; // Initialize mapTileNum array with dimensions from GamePanel
        getTileImage(); // Load tile images
        loadMap("/Map/map.02"); // Load map data
    }

    // Method to load map data from a file
    public void loadMap(String mapfile) {
        try (InputStream is = getClass().getResourceAsStream(mapfile);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            int col = 0;
            int row = 0;

            // Read map data line by line
            while (col < gamepanel.maxWorldCol && row < gamepanel.maxWorldRow) {
                String line = br.readLine();

                // Parse each line to get tile numbers
                while (col < gamepanel.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }

                // Move to the next row when finished with a line
                if (col == gamepanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
        } catch (Exception e) {
              e.printStackTrace();
        }
    }

    // Method to load tile images
    public void getTileImage() {

            String title = "/tile/";
            // Load each tile image and set properties

            setup(0, "water", true, false, false, false, false);
            setup(1, "grass", false, false, false, false, false);
            setup(2, "wall", true, false, false, true, false);
            setup(3, "bushGrass", false, false, false, false, true);
            setup(4, "tree", true, false, false, false, false);
            setup(5, "sand", false, false, false, false, false);
            setup(6, "sand2", false, false, false, false, false);
            setup(7, "sand3", true, false, false, false, false);
            setup(8, "wood", false, false, true, false, false);
            setup(9, "Dimondplate", false, true, false, false, false);
            setup(10, "earth", false, false, false,false, false);
    }

    public void setup(int index, String imagePath, boolean coll, boolean buy, boolean rpsg, boolean ma, boolean se){

        UtilityTool uTool = new UtilityTool();
        String title = "/tile/";

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream(title + imagePath + ".png"));
            tile[index].image = uTool.scaledImage(tile[index].image, gamepanel.tileSize, gamepanel.tileSize);
            tile[index].collision = coll;
            tile[index].buy = buy;
            tile[index].RPSG = rpsg;
            tile[index].make = ma;
            tile[index].sell = se;

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    // Method to draw tiles on the screen
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        // Loop through mapTileNum array
        while (worldCol < gamepanel.maxWorldCol && worldRow < gamepanel.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gamepanel.tileSize;
            int worldY = worldRow * gamepanel.tileSize;
            int screenX = worldX - gamepanel.player.worldX + gamepanel.player.screenX; // Calculate screen position
            int screenY = worldY - gamepanel.player.worldY + gamepanel.player.screenY;
            // Draw only visible tiles
            if ( (worldX + gamepanel.tileSize) > gamepanel.player.worldX - gamepanel.player.screenX &&
                    (worldX - gamepanel.tileSize) < gamepanel.player.worldX + gamepanel.player.screenX &&
                    (worldY + gamepanel.tileSize) > gamepanel.player.worldY - gamepanel.player.screenY &&
                    (worldY - gamepanel.tileSize) < gamepanel.player.worldY + gamepanel.player.screenY
            )
            {
                g2.drawImage(tile[tileNum].image, screenX, screenY,null);

            }
            worldCol++;
            if (worldCol == gamepanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
