package drawables;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Gift {
    static public int width = 50;
    static public int height = 50;
    public int positionX;
    public int positionY;
    public BufferedImage image;
    public boolean isDragged;

    public Gift(ColorField [] colorFields, int xBoundary, int yBoundary){

        try {
            image = ImageIO.read(new File("assets/gift.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Random random = new Random();
        boolean isPositionValid;
        int positionX,positionY;
        do {
            isPositionValid = true;
            positionX = random.nextInt(xBoundary);
            positionY = random.nextInt(yBoundary);
            for (ColorField colorField : colorFields) {
                if (positionX < colorField.positionX + colorField.width && positionX + width > colorField.positionX &&
                        positionY < colorField.positionY + colorField.height && positionY + height > colorField.positionY)
                    isPositionValid = false;
            }
        }while(!isPositionValid);
        this.positionX = positionX;
        this.positionY = positionY;
        isDragged = false;
    }


    public boolean isClicked(int mouseX, int mouseY){
        if(mouseX > positionX && mouseX < positionX + width && mouseY > positionY && mouseY < positionY + height){
            return true;
        }
        else
            return false;
    }

    public void goToLocation(int x, int y){
        positionX = x;
        positionY = y;
    }

}
