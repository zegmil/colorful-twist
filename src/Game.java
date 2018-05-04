import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Game extends JFrame {

    public static int windowWidth;
    public static int windowHeight;
    public static int menuPanel = 0;
    public static int easyLevel = 1, easyGamePanel = 1;
    public static int hardLevel = 2, hardGamePanel = 2;

    public static int pointsPerWin = 20;
    public static int pointsPerGift = 30;
    public static int pointsLimit = 1000;
    public static int giftAppearanceRatio = 5; // the higher the ratio, the smaller the
                                                // probability of gift appearance in the next round

    public Game(int windowWidth, int windowHeight) {
        super("Game");
        Game.windowWidth = windowWidth;
        Game.windowHeight = windowHeight;

        setSize(windowWidth, windowHeight);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ContainerPanel containerPanel = new ContainerPanel(windowWidth,windowHeight);
        add(containerPanel);
        pack();
        setVisible(true);
    }
}
