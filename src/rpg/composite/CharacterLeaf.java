package rpg.composite;

import rpg.model.Character;

public class CharacterLeaf implements GroupComponent {
    private final Character character;

    public CharacterLeaf(Character character) {
        this.character = character;
    }

    @Override
    public void display() {
        System.out.println(character);
    }
}
