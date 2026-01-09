package rpg.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Character {

    private final String name;
    private final int strength;
    private final int intelligence;
    private final int agility;

    private final List<String> abilities = new ArrayList<>();

    public Character(String name, int strength, int intelligence, int agility) {
        this.name = name;
        this.strength = strength;
        this.intelligence = intelligence;
        this.agility = agility;
    }

    public String getName() { return name; }
    public int getStrength() { return strength; }
    public int getIntelligence() { return intelligence; }
    public int getAgility() { return agility; }

    public void addAbility(String ability) {
        if (!abilities.contains(ability)) {
            abilities.add(ability);
        }
    }

    public List<String> getAbilities() {
        return Collections.unmodifiableList(abilities);
    }

    public boolean hasAbility(String ability) {
        return abilities.contains(ability);
    }

    public int totalStats() {
        return strength + intelligence + agility;
    }

    @Override
    public String toString() {
        return name +
                " [STR=" + strength +
                ", INT=" + intelligence +
                ", AGI=" + agility +
                ", abilities=" + abilities + "]";
    }
}
