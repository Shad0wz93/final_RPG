package rpg.validation;

import rpg.model.Character;

public class NameValidator extends CharacterValidator {

    @Override
    public void validate(Character character) {
        if (character.getName() == null || character.getName().isBlank())
            throw new IllegalArgumentException("Nom invalide");

        validateNext(character);
    }
}
