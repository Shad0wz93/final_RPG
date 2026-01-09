package rpg.decorator;

import rpg.model.Character;

public class DodgeDecorator extends CharacterDecorator {

    public DodgeDecorator(Character base) {
        super(base);
        addAbility("Esquive");
    }
}
