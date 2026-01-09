package rpg.decorator;

import rpg.model.Character;

public class AbilityRemover {

    private AbilityRemover() {}

    public static Character remove(Character character, String ability) {
        if (character != null && ability != null) {
            character.removeAbility(ability);
        }
        return character;
    }
}
