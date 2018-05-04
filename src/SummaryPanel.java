import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SummaryPanel extends JPanel {
    private ContainerPanel parentPanel;
    private GameState gameState;

    public SummaryPanel(int windowWidth, int windowHeight, GameState gameState, ContainerPanel parentPanel){
        this.gameState = gameState;
        this.parentPanel = parentPanel;
        setPreferredSize(new Dimension(windowWidth,windowHeight));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                parentPanel.switchTo(Game.menuPanel);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Renderer.drawText(g2d,"G R A T U L A C J E !",Color.RED,50,getPreferredSize().width/3,100);
        Renderer.drawText(g2d,"Próg " + Game.pointsLimit + " punktów udało ci się",Color.RED,50,getPreferredSize().width/10,300);
        Renderer.drawText(g2d,"osiągnąć w ciągu " + gameState.roundNumber + " rund",Color.RED,50,getPreferredSize().width/10,400);

    }
}
