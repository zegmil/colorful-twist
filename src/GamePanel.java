import drawables.*;
import drawables.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class GamePanel extends JPanel {
    private ContainerPanel parentPanel;
    private int level;
    private ColorName palette[];
    private ColorName currentColor;
    private ColorField colorFields[];
    private GameState gameState;
    private List<Button> buttons;
    private Gift gift;
    private Bag bag;

    public GamePanel(int panelWidth, int panelHeight, int level, ContainerPanel parentPanel){
        this.parentPanel = parentPanel;
        this.bag = new Bag(4*panelWidth/10,panelHeight - 200);
        this.gift = null;
        this.level = level;
        int fieldsNumber;
        if(level == Game.easyLevel)
            fieldsNumber = 6;
        else
            fieldsNumber = 8;

        currentColor = new ColorName("zielony", Color.BLUE);
        colorFields = new ColorField[fieldsNumber];
        setPreferredSize(new Dimension(panelWidth,panelHeight));
        initButtons();
        initPalette();
        initColorFields();
        updateColorFields();
        this.gameState = new GameState();

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                for (Button button : buttons){
                    if(button.isClicked(e.getX(),e.getY()))
                        button.click();
                }

                if(gift != null) {
                    if (gift.isClicked(e.getX(), e.getY()))
                        gift.isDragged = true;
                }

                for(ColorField colorField : colorFields){
                    if(colorField.isClicked(e.getX(),e.getY())) {
                        gameState.roundNumber++;
                        if(colorField.color.equals(getColorByName(currentColor.name))) {
                            gameState.points += Game.pointsPerWin;
                            if(gameState.points >= Game.pointsLimit) {
                                gameState.isFinished = true;
                                parentPanel.showSummaryPanel(gameState);
                                break;
                            }
                        }
                        gameState.lastClickTime = System.currentTimeMillis();
                        changeCurrentColor();
                        updateColorFields();
                        gift = null;
                        tryToAddGift();
                        repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(gift != null) {
                    if (bag.isGiftInBounds(gift)){
                        gameState.points += Game.pointsPerGift;
                        Bag.playGiftInsideBagSound();
                        if (gameState.points >= Game.pointsLimit) {
                            gameState.isFinished = true;
                            parentPanel.showSummaryPanel(gameState);
                        }
                    }
                    if(gift.isDragged)
                        gift = null;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseMoved(e);
                if (gift != null) {
                    if (gift.isDragged) {
                        gift.goToLocation(e.getX() - Gift.width / 2, e.getY() - Gift.height / 2);
                    }
                }
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);

        int timeDecreaseRatio;
        if(level == Game.easyLevel)
            timeDecreaseRatio = 2000;
        else
            timeDecreaseRatio = 3000;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int timeLimit = 5000 - (int)(timeDecreaseRatio * (double)gameState.points/(double)Game.pointsLimit);
                if(System.currentTimeMillis() - gameState.lastClickTime > timeLimit){
                    gameState.roundNumber++;
                    gameState.lastClickTime = System.currentTimeMillis();
                    changeCurrentColor();
                    updateColorFields();
                    gift = null;    // deleting gift if appeared in current round
                    tryToAddGift(); // attempt to add gift to the next round
                }

                if(gameState.isFinished){
                    timer.cancel();
                    timer.purge();
                }
                else
                    repaint();
            }

        },0,10);
    }

    public void initButtons(){
        double screenHeight = getPreferredSize().getHeight();
        double screenWidth = getPreferredSize().getWidth();
        buttons = new ArrayList<>();
        Button exit = new Button((int)(screenWidth - 250),(int)(screenHeight - 75),
                "assets/finish.png");
        exit.setOnClickAction( () -> {
            gameState.isFinished = true;
            parentPanel.switchTo(Game.menuPanel);
        });
        buttons.add(exit);

    }
    public void initPalette(){
        List <ColorName> palette =new ArrayList<>(Arrays.asList(
                new ColorName("czerwony", new Color(255,0,0)),
                new ColorName("zielony", new Color(0,255,0)),
                new ColorName("niebieski",new Color(0,0,255)),
                new ColorName("żółty", new Color(255,255,0)),
                new ColorName("czarny", new Color(0,0,0)),
                new ColorName("różowy", new Color(255, 102, 204)),
                new ColorName("pomarańczowy", new Color(255,102,0)),
                new ColorName("szary", new Color(153, 153, 153)),
                new ColorName("fioletowy", new Color(128, 0, 128)),
                new ColorName("brązowy", new Color(128, 64, 0))
        ));
        if(level == Game.hardLevel){
            List <ColorName> extendedPalette = Arrays.asList(
                    new ColorName("winogronowy", new Color(111,45,168)),
                    new ColorName("paprociowy", new Color(113,188,120)),
                    new ColorName("gruszkowy", new Color(209,226,49)),
                    new ColorName("zielony jodłowy", new Color(23,41,28)),
                    new ColorName("mosiądz", new Color(212,194,121)),
                    new ColorName("arbuzowy", new Color(255,109,102)),
                    new ColorName("szary błękitny", new Color(135,148,166)),
                    new ColorName("szmaragdowy", new Color(25,165,111)),
                    new ColorName("karmazynowy", new Color(220,20,60)),
                    new ColorName("kobaltowy", new Color(0,18,69)),
                    new ColorName("róż hebanowy", new Color(103,72,70)),
                    new ColorName("karmazynowy", new Color(220,20,60))
            );
            palette.addAll(extendedPalette);
        }
        this.palette = palette.toArray(new ColorName[palette.size()]);
    }

    public void initColorFields() {
        int fieldsInRow = colorFields.length/2;
        int startingX = ((int)getPreferredSize().getWidth() - (100 + (fieldsInRow-1)*200))/2;
        for(int i = 0; i < colorFields.length; i++){
            ColorField colorField = new ColorField(startingX + (i%fieldsInRow)*200,180 + (i/fieldsInRow)*200,
                    100,100);
            colorField.color = getColorByName("zielony");
            colorFields[i] = colorField;
        }
    }

    public void changeCurrentColor(){
        Random random = new Random();
        int randomNameIndex = random.nextInt(palette.length);
        int randomColorIndex = random.nextInt(palette.length);
        currentColor = new ColorName(palette[randomNameIndex].name, palette[randomColorIndex].color);
    }

    public void updateColorFields(){
        Random random = new Random();
        Set<Color> newColors = new HashSet<>();
        newColors.add(currentColor.color);
        newColors.add(getColorByName(currentColor.name));
        while(newColors.size() < colorFields.length){
            int index = random.nextInt(palette.length);
            newColors.add(palette[index].color);
        }
        List <Color> colorsList = new ArrayList<>(newColors);
        Collections.shuffle(colorsList);
        for(int i = 0; i < colorFields.length; i++){
            colorFields[i].color = colorsList.get(i);
        }
    }

    public Color getColorByName(String name){
        for (ColorName color : palette) {
            if (color.name.equals(name))
                return color.color;
        }
        return null;
    }

    public void tryToAddGift(){
        if(gift == null){
            Random random = new Random();
            int randomInt = random.nextInt();
            if(randomInt % Game.giftAppearanceRatio == 0) {
                int panelWidth = getPreferredSize().width;
                int panelHeight = getPreferredSize().height;
                gift = new Gift(colorFields, panelWidth - Gift.width, panelHeight - bag.height - Gift.height);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Renderer.drawColorName(g2d, currentColor);
        Renderer.drawColorFields(g2d, colorFields);
        Renderer.drawText(g2d,this,"assets/points.png",10,getHeight() - 110);
        Renderer.drawText(g2d,Integer.toString(gameState.points), Color.BLACK,36,180,getHeight() - 80);
        Renderer.drawText(g2d,this,"assets/rounds.png",10,getHeight() - 50);
        Renderer.drawText(g2d,Integer.toString(gameState.roundNumber), Color.BLACK,36,180,getHeight() - 20);
        Renderer.drawButton(g2d,this, buttons.get(0));
        Renderer.drawBag(g2d,this,bag);
        if(gift != null)
            Renderer.drawGift(g2d,this, gift);
    }

}
