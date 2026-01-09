package rpg.observer;

public class CombatLog implements CombatObserver {

    @Override
    public void onCombatEvent(String message) {
        System.out.println("📜 " + message);
    }
}
