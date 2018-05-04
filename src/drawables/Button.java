package drawables;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Button {
    public int positionX;
    public int positionY;
    public int width;
    public int height;
    public BufferedImage image;
    public ButtonClickAction onClickAction;

    public Button(int positionX, int positionY, String imagePath){
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public boolean isClicked(int mouseX, int mouseY){
        if(mouseX > positionX && mouseX < positionX + width && mouseY > positionY && mouseY < positionY + height)
            return true;
        else
            return false;
    }

    public void setOnClickAction(ButtonClickAction onClickAction){
        this.onClickAction = onClickAction;
    }

    public void click(){
        onClickAction.actionPerformed();
    }
}
