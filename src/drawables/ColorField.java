package drawables;

import java.awt.*;

public class ColorField {
    public final int positionX;
    public final int positionY;
    public final int width;
    public final int height;
    public Color color;

    public ColorField(int positionX, int positionY, int width, int height){
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
    }

    public boolean isClicked(int mouseX, int mouseY){
        int centerX = positionX + width/2;
        int centerY = positionY + height/2;
        int radius = width/2;
        int distance = (int) Math.sqrt(Math.pow(mouseX - centerX,2) + Math.pow(mouseY - centerY,2));
        if(distance <= radius)
            return true;
        else
            return false;
    }
}
