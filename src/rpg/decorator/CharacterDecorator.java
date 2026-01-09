package rpg.decorator;

import rpg.model.Character;

public abstract class CharacterDecorator extends Character {

    protected final Character base;

    protected CharacterDecorator(Character base) {
        super(
                base.getName(),
                base.getStrength(),
                base.getIntelligence(),
                base.getAgility()
        );
        this.base = base;
        base.getAbilities().forEach(this::addAbility);
    }
}
