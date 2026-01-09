package rpg.observer;

public class CharacterLog implements CharacterObserver {

    @Override
    public void onCharacterChanged(String message) {
        System.out.println("👁️ " + message);
    }
}
