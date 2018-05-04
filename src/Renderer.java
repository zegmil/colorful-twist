import drawables.*;
import drawables.Button;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Renderer {


    public static void drawColorName(Graphics2D graphics, ColorName colorName){
        Font oldFont = graphics.getFont();
        Color oldColor = graphics.getColor();

        Font font = new Font("Dialog", Font.BOLD,42);
        graphics.setFont(font);
        graphics.setColor(colorName.color);

        FontMetrics fontMetrics = graphics.getFontMetrics();
        int stringX = Game.windowWidth/2 - fontMetrics.stringWidth(colorName.name)/2;
        int stringY = 100;

        graphics.drawString(colorName.name, stringX, stringY);

        graphics.setFont(oldFont);
        graphics.setColor(oldColor);
    }

    public static void drawText(Graphics2D graphics, String text, Color fontColor, int size, int positionX, int positionY){
        Font oldFont = graphics.getFont();
        Color oldColor = graphics.getColor();

        Font font = new Font("Dialog", Font.BOLD, size);
        graphics.setFont(font);
        graphics.setColor(fontColor);
        graphics.drawString(text, positionX, positionY);

        graphics.setFont(oldFont);
        graphics.setColor(oldColor);
    }

    public static void drawText(Graphics2D graphics,JPanel containingPanel, String imagePath, int positionX, int positionY){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics.drawImage(image,positionX,positionY,containingPanel);
    }

    public static void drawColorFields(Graphics2D graphics, ColorField colorFields[]){
        Color oldColor = graphics.getColor();

        for (ColorField colorField : colorFields){
            graphics.setColor(colorField.color);
            graphics.fillOval(colorField.positionX, colorField.positionY, colorField.width, colorField.height);
        }

        graphics.setColor(oldColor);
    }

    public static void drawGift(Graphics2D graphics, JPanel containingPanel, Gift gift){
        graphics.drawImage(gift.image,gift.positionX,gift.positionY,containingPanel);
    }

    public static void drawBag(Graphics2D graphics, JPanel containingPanel, Bag bag){
        graphics.drawImage(bag.image, bag.positionX,bag.positionY, containingPanel);
    }

    public static void drawButton(Graphics2D graphics, JPanel containingPanel, Button button){
        graphics.drawImage(button.image, button.positionX, button.positionY, containingPanel);
    }
}
