package drawables;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bag {
    public int positionX;
    public int positionY;
    public int width;
    public int height;
    public BufferedImage image;

    public Bag(int positionX,int positionY){
        try {
            image = ImageIO.read(new File("assets/bag.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public boolean isGiftInBounds(Gift gift)
    {
        if(gift.positionY > positionY && gift.positionY + Gift.height < positionY+height
                && gift.positionX > positionX && gift.positionX + Gift.width < positionX + width)
            return true;
        else
            return false;
    }

    static public void playGiftInsideBagSound(){
        new Thread(() -> {
            String soundPath = "assets/giftInsideBag.wav";
            AudioInputStream audioInputStream = null;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(new File(soundPath));
            } catch (UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            try {
                if (clip != null) {
                    clip.open(audioInputStream);
                }
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
            if (clip != null) {
                clip.start();
            }
        }).start();

    }
}
