package rpg.composite;

import rpg.model.Character;

public class CharacterLeaf implements GroupComponent {

    private final Character character;

    public CharacterLeaf(Character character) {
        this.character = character;
    }

    @Override
    public int getPower() {
        int str = character.getStrength();
        int agi = character.getAgility();
        int intel = character.getIntelligence();

        int main = Math.max(str, Math.max(agi, intel));
        return (int) (main * 1.5 + str + agi + intel);
    }

    @Override
    public void display() {
        System.out.println(character);
    }

    public Character getCharacter() {
        return character;
    }
}
