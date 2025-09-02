package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener { // Implements the listener interface for receiving keyboard events

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, switchCharacterPressed, enterPressed;
    boolean checkDrawTime = false; // Debug flag to check drawing time

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {
        // Not used in this implementation
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Get the key code associated with the pressed key

        // Handle movement keys
        if (code == KeyEvent.VK_W) { // If the key is 'W' then set upPressed to true
            upPressed = true;
        } if (code == KeyEvent.VK_S) { // If the key is 'S' then set downPressed to true
            downPressed = true;
        } if (code == KeyEvent.VK_A) { // If the key is 'A' then set leftPressed to true
            leftPressed = true;
        } if (code == KeyEvent.VK_D) { // If the key is 'D' then set rightPressed to true
            rightPressed = true;
        } if (code == KeyEvent.VK_V){ // If the key is 'V' then set switchCharacterPressed to true
            switchCharacterPressed = true;
        } if (code == KeyEvent.VK_P){ // If the key is 'P' then toggle game state between play and pause
            if(gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            }
            else{
                gp.gameState = gp.playState;
            }
        } if (code == KeyEvent.VK_ENTER){ // If the key is 'ENTER' then set enterPressed to true
            enterPressed = true;
        }

        // Menu state toggling
        if (code == KeyEvent.VK_M) {
            if (gp.gameState == gp.playState){
                gp.gameState = gp.menuState;
            } else if (gp.gameState == gp.menuState) {
                gp.gameState = gp.playState;
            }
        }

        // Handle navigation within menu state
        int maxCommandNum = 0;
        switch (gp.ui.subState){
            case 0: maxCommandNum = 3; break;
            case 3: maxCommandNum = 1; break;
        }

        if(gp.gameState == gp.menuState){
            if(code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0){
                    gp.ui.commandNum = maxCommandNum;
                }
            }
            if (code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if (gp.ui.commandNum > maxCommandNum){
                    gp.ui.commandNum = 0;
                }
            }
        }

        // Debug: Toggle draw time checking
        if (code == KeyEvent.VK_T){
            checkDrawTime = !checkDrawTime;
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // Reset movement flags on key release
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        } if (code == KeyEvent.VK_S) {
            downPressed = false;
        } if (code == KeyEvent.VK_A) {
            leftPressed = false;
        } if (code == KeyEvent.VK_D) {
            rightPressed = false;
        } if (code == KeyEvent.VK_V){
            switchCharacterPressed = false;
        }
    }
}
