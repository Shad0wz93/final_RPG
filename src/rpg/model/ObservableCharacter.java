package rpg.model;

import rpg.observer.CharacterObserver;
import java.util.ArrayList;
import java.util.List;

public class ObservableCharacter extends Character {

    private final List<CharacterObserver> observers = new ArrayList<>();

    public ObservableCharacter(Character base) {
        super(
                base.getName(),
                base.getStrength(),
                base.getIntelligence(),
                base.getAgility()
        );
        base.getAbilities().forEach(this::addAbility);
    }

    public ObservableCharacter(String name, int strength, int intelligence, int agility) {
        super(name, strength, intelligence, agility);
    }

    public void addObserver(CharacterObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String message) {
        observers.forEach(o -> o.onCharacterChanged(message));
    }

    @Override
    public void addAbility(String ability) {
        super.addAbility(ability);
        notifyObservers("Capacité ajoutée à " + getName() + " : " + ability);
    }

    @Override
    public void removeAbility(String ability) {
        super.removeAbility(ability);
        notifyObservers("Capacité retirée de " + getName() + " : " + ability);
    }
}
