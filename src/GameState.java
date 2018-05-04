
public class GameState {
    public long lastClickTime;
    public int points;
    public int roundNumber;
    public boolean isFinished;


    public GameState() {
        lastClickTime = System.currentTimeMillis();
        points = 0;
        roundNumber = 1;
    }
}
