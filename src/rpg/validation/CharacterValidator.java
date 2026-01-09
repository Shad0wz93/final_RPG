package rpg.validation;

import rpg.model.Character;

public abstract class CharacterValidator {

    protected CharacterValidator next;

    public CharacterValidator linkWith(CharacterValidator next) {
        this.next = next;
        return next;
    }

    public abstract void validate(Character character);

    protected void validateNext(Character character) {
        if (next != null) next.validate(character);
    }
}
