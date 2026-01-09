package rpg.model;

import java.util.List;

public class Character {
    protected final String name;
    protected final int strength;
    protected final int intelligence;
    protected final int agility;

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

    public List<String> getAbilities() {
        return List.of();
    }

    public int totalStats() {
        return strength + intelligence + agility;
    }

    @Override
    public String toString() {
        return name + " [STR=" + strength +
                ", INT=" + intelligence +
                ", AGI=" + agility +
                ", abilities=" + getAbilities() + "]";
    }
}
