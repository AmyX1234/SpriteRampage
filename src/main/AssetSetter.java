package main;

import entity.NPC_BlueMan;

public class AssetSetter {

    GamePanel gp; // Instance variable to hold a reference to the GamePanel

    // Constructor for AssetSetter class
    public AssetSetter(GamePanel gp){
        this.gp = gp; // Initialize GamePanel instance variable with the provided gp parameter
    }

    // Method to set NPC (non-player character) objects
    public void setNPC(){
        // Instantiate a new NPC_BlueMan object and assign it to the first element of the gp.npc array
        gp.npc[0] = new NPC_BlueMan(gp);

        // Set the world coordinates (position) of the NPC_BlueMan object
        gp.npc[0].worldX = gp.tileSize * 10; // Set worldX coordinate to 10 tiles (assuming tileSize is a constant or variable representing tile size)
        gp.npc[0].worldY = gp.tileSize * 44; // Set worldY coordinate to 44 tiles (assuming tileSize is a constant or variable representing tile size)
    }
}
