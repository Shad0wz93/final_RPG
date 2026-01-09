package rpg.validation;

import rpg.model.Character;
import rpg.settings.GameSettings;

public class StatsValidator extends CharacterValidator {

    @Override
    public void validate(Character character) {
        int total = character.totalStats();
        int max = GameSettings.getInstance().getMaxStatPoints();

        if (total > max) {
            throw new IllegalArgumentException(
                    "Trop de points : " + total + " / " + max
            );
        }
        validateNext(character);
    }
}
