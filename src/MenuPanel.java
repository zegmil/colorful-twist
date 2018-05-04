import drawables.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuPanel extends JPanel {
    private ContainerPanel parentPanel;
    private List<Button> buttons;

    public MenuPanel(int width, int height, ContainerPanel parentPanel){
        setPreferredSize(new Dimension(width,height));
        this.parentPanel = parentPanel;
        buttons = new ArrayList<>();

        Button startEasyGameButton = new Button(width/3,100,"assets/easyLevel.png");
        startEasyGameButton.setOnClickAction( () -> {
            parentPanel.switchTo(Game.easyGamePanel);
        });
        buttons.add(startEasyGameButton);

        Button startHardGameButton = new Button(width/3,200,"assets/hardLevel.png");
        startHardGameButton.setOnClickAction( () -> {
            parentPanel.switchTo(Game.hardGamePanel);
        });
        buttons.add(startHardGameButton);

        Button exitGameButton = new Button((int)(width*0.45),300,"assets/exit.png");
        exitGameButton.setOnClickAction( () -> System.exit(0) );
        buttons.add(exitGameButton);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                for (Button button : buttons){
                    if(button.isClicked(e.getX(),e.getY()))
                        button.click();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(0,0,0));
        Graphics2D g2d = (Graphics2D) g;
        for(Button button : buttons)
            Renderer.drawButton(g2d,this,button);
        Renderer.drawText(g2d,"Poziom podstawowy jest łatwy",Color.BLACK,20,50,(int)getPreferredSize().getHeight()-100);
        Renderer.drawText(g2d,"Poziom rozszerzony jest trudny",Color.BLACK,20,50,(int)getPreferredSize().getHeight()-50);
    }
}
