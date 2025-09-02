package main; // Declares the package name

import javax.swing.*; // Imports all classes from the javax.swing package
import java.awt.*; // Imports all classes from the java.awt package
import java.awt.event.MouseEvent; // Imports the MouseEvent class from java.awt.event package
import java.awt.event.MouseListener; // Imports the MouseListener interface from java.awt.event package

import collision.CollisionChecker; // Imports the CollisionChecker class from the collision package
import entity.NPC_BlueMan; // Imports the NPC_BlueMan class from the entity package
import tile.TileManager; // Imports the TileManager class from the tile package
import entity.Player; // Imports the Player class from the entity package

public class GamePanel extends JPanel implements Runnable, MouseListener { // Defines the GamePanel class which extends JPanel and implements Runnable and MouseListener interfaces
    // Screen Settings
    final int originalTileSize = 16; // Defines the size of the original tile
    final int scale = 3; // Defines the scaling factor for the image of the character

    // Calculating scaled tileSize
    public final int tileSize = originalTileSize * scale; // Calculates the scaled tile size (48x48)
    public final int maxScreenCol = 16; // Defines the maximum number of columns on the screen
    public final int maxScreenRow = 12; // Defines the maximum number of rows on the screen
    public final int screenWidth = tileSize * maxScreenCol; // Calculates the total screen width (768 pixels)
    public final int screenHeight = tileSize * maxScreenRow; // Calculates the total screen height (576 pixels)
    public final int maxWorldCol = 50; // Defines the maximum number of columns in the world
    public final int maxWorldRow = 50; // Defines the maximum number of rows in the world
    public final int maxWorldWidth = tileSize * maxWorldCol; // Calculates the total world width
    public final int maxWorldHeight = tileSize * maxWorldRow; // Calculates the total world height

    // Frames Per Second (FPS)
    int FPS = 60; // Sets the desired FPS to 60

    // System
    public TileManager tileM = new TileManager(this); // Creates an instance of TileManager
    KeyHandler keyH = new KeyHandler(this); // Creates an instance of KeyHandler
    Thread gameThread; // Declares a thread for the game loop
    public AssetSetter aSetter = new AssetSetter(this); // Creates an instance of AssetSetter
    public CollisionChecker cChecker = new CollisionChecker(this); // Creates an instance of CollisionChecker
    public UI ui = new UI(this); // Creates an instance of UI

    // Entity
    public Player player = new Player(this, keyH, "Jeff"); // Creates an instance of Player
    public NPC_BlueMan[] npc = new NPC_BlueMan[10]; // Creates an array to hold NPC_BlueMan instances

    // Game state
    public int gameState; // Declares a variable to hold the current game state
    public final int titleState = 0; // Sets a constant for the title state
    public final int playState = 1; // Sets a constant for the play state
    public final int pauseState = 2; // Sets a constant for the pause state
    public final int dialogueState = 3; // Sets a constant for the dialogue state
    public final int menuState = 4; // Sets a constant for the menu state

    // Constructor
    public GamePanel() { // Constructor for the GamePanel class
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Sets the preferred size of the panel
        this.setBackground(Color.BLACK); // Sets the background color to black
        this.setDoubleBuffered(true); // Enables double buffering for smoother graphics
        this.addKeyListener(keyH); // Adds a key listener for user input
        this.setFocusable(true); // Makes the panel focusable for key input
        this.addMouseListener(this); // Adds a mouse listener for user input
    }

    @Override
    public void mouseClicked(MouseEvent e) { // Handles mouse click events
        int mouseX = e.getX(); // Gets the X coordinate of the mouse click
        int mouseY = e.getY(); // Gets the Y coordinate of the mouse click

        // Check if click happened within the window boundaries
        if (mouseX >= 0 && mouseX < screenWidth && mouseY >= 0 && mouseY < screenHeight) {
            // Change game state to playState
            gameState = playState; // Sets the game state to play state
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { } // Handles mouse press events (not used)

    @Override
    public void mouseReleased(MouseEvent e) { } // Handles mouse release events (not used)

    @Override
    public void mouseEntered(MouseEvent e) { } // Handles mouse enter events (not used)

    @Override
    public void mouseExited(MouseEvent e) { } // Handles mouse exit events (not used)

    public void setUpGame() { // Sets up the game
        aSetter.setNPC(); // Sets up NPCs
        gameState = titleState; // Sets the initial game state to title state
    }

    // Method to start the game thread
    public void startGameThread() { // Starts the game thread
        gameThread = new Thread(this); // Creates a new thread
        gameThread.start(); // Starts the thread
    }

    // Game loop
    public void run() { // Runs the game loop
        double drawInterval = (double) 1000000000 / FPS; // Calculates the interval between each frame for the desired FPS
        double delta = 0; // Delta time accumulator
        long lastTime = System.nanoTime(); // Gets the current time in nanoseconds
        long currentTime; // Variable to hold the current time
        long timer = 0; // Timer accumulator
        int drawCount = 0; // Frame count

        while (gameThread != null) { // Main game loop
            currentTime = System.nanoTime(); // Gets the current time in nanoseconds

            delta += (currentTime - lastTime) / drawInterval; // Updates delta time
            timer += (currentTime - lastTime); // Updates timer
            lastTime = currentTime; // Updates lastTime to currentTime

            if (delta >= 1) { // Checks if it's time to update the game state and redraw the screen
                update(); // Updates the game state
                repaint(); // Repaints the screen
                delta--; // Decrements delta
                drawCount++; // Increments frame count
            }

            if (timer >= 1000000000) { // Checks if a second has passed
                System.out.println("FPS: " + drawCount); // Outputs the current FPS
                drawCount = 0; // Resets the frame count
                timer = 0; // Resets the timer
            }
        }
    }

    // Update method to update game state
    public void update() { // Updates the game state
        if (gameState == playState) { // Checks if the game is in play state
            player.update(); // Updates the player state

            for (NPC_BlueMan npcBlueMan : npc) { // Iterates through all NPCs
                if (npcBlueMan != null) { // Checks if the NPC is not null
                    npcBlueMan.update(); // Updates the NPC state
                }
            }
        }
    }

    // Method to paint graphics on the panel
    public void paintComponent(Graphics g) { // Paints graphics on the panel
        super.paintComponent(g); // Calls the superclasses paintComponent method
        Graphics2D g2 = (Graphics2D) g; // Casts the Graphics object to Graphics2D

        // Debug
        long drawStart = 0; // Variable to hold the start time of the drawing
        if (keyH.checkDrawTime) { // Checks if draw time debugging is enabled
            drawStart = System.nanoTime(); // Gets the current time in nanoseconds
        }

        // Title screen
        if (gameState == titleState) { // Checks if the game is in title state
            ui.draw(g2); // Draws the UI for the title screen
        } else { // For other game states
            tileM.draw(g2); // Draws the tiles

            // NPC
            for (NPC_BlueMan npcBlueMan : npc) { // Iterates through all NPCs
                if (npcBlueMan != null) { // Checks if the NPC is not null
                    npcBlueMan.draw(g2); // Draws the NPC
                }
            }
            player.draw(g2); // Draws the player

            // UI
            ui.draw(g2); // Draws the UI
        }

        // Debug
        if (keyH.checkDrawTime) { // Checks if draw time debugging is enabled
            long drawEnd = System.nanoTime(); // Gets the current time in nanoseconds
            long passed = drawEnd - drawStart; // Calculates the time taken to draw
            g2.setColor(Color.WHITE); // Sets the color to white
            g2.drawString("Draw Time: " + passed, 10, 400); // Draws the draw time on the screen
            System.out.println("Draw Time: " + passed); // Outputs the draw time to the console
        }
        g2.dispose(); // Disposes the graphics object
    }
}
