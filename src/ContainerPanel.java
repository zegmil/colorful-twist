import javax.swing.*;

public class ContainerPanel extends JPanel {
    private int windowWidth;
    private int windowHeight;

    public ContainerPanel(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        add(new MenuPanel(windowWidth,windowHeight,this));
    }

    public void switchTo(int targetPanel){
        removeAll();
        if(targetPanel == Game.menuPanel){
            add(new MenuPanel(windowWidth,windowHeight,this));
        }
        else if(targetPanel == Game.easyGamePanel){
            add(new GamePanel(windowWidth,windowHeight,Game.easyGamePanel,this));
        }
        else if(targetPanel == Game.hardGamePanel){
            add(new GamePanel(windowWidth,windowHeight,Game.hardGamePanel,this));
        }
        revalidate();
        repaint();
    }

    public void showSummaryPanel(GameState gameState){
        removeAll();
        add(new SummaryPanel(windowWidth,windowHeight,gameState,this));
        revalidate();
        repaint();
    }

}
