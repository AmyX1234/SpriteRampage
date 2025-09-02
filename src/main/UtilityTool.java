package main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * UtilityTool class provides various utility methods for image processing.
 */
public class UtilityTool {

    /**
     * Scales the given BufferedImage to the specified width and height.
     *
     * @param original The original BufferedImage to be scaled.
     * @param width The width to which the image should be scaled.
     * @param height The height to which the image should be scaled.
     * @return A new BufferedImage that is the scaled version of the original image.
     */
    public BufferedImage scaledImage(BufferedImage original, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        return scaledImage;
    }
}
