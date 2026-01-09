package rpg.builder;

import rpg.model.Character;

public class CharacterBuilder {

    private String name;
    private int strength;
    private int intelligence;
    private int agility;

    public CharacterBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CharacterBuilder strength(int value) {
        this.strength = value;
        return this;
    }

    public CharacterBuilder intelligence(int value) {
        this.intelligence = value;
        return this;
    }

    public CharacterBuilder agility(int value) {
        this.agility = value;
        return this;
    }

    public Character build() {
        return new Character(name, strength, intelligence, agility);
    }
}
