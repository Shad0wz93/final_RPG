package rpg.settings;

public class GameSettings {

    private static final GameSettings INSTANCE = new GameSettings();
    private final int maxStatPoints = 15;

    private GameSettings() {}

    public static GameSettings getInstance() {
        return INSTANCE;
    }

    public int getMaxStatPoints() {
        return maxStatPoints;
    }
}