package rpg.decorator;

import rpg.model.Character;

public class InvisibilityDecorator extends CharacterDecorator {

    public InvisibilityDecorator(Character base) {
        super(base);
        addAbility("Invisibilité");
    }
}
