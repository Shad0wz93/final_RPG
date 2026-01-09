package rpg.composite;

import rpg.combat.DiceUtil;
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
        int boostedMain = (int) (main * 1.5);

        int diceBonus = DiceUtil.rollForCharacter(character);

        return boostedMain + str + agi + intel + diceBonus;
    }

    @Override
    public void display() {
        System.out.println(character);
    }

    public Character getCharacter() {
        return character;
    }
}
