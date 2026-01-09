package rpg.decorator;

import rpg.model.Character;

import java.util.List;

public abstract class CharacterDecorator extends Character {

    protected final Character base;

    protected CharacterDecorator(Character base) {
        super(base.getName(),
                base.getStrength(),
                base.getIntelligence(),
                base.getAgility());
        this.base = base;
    }

    @Override
    public List<String> getAbilities() {
        return base.getAbilities();
    }
}
