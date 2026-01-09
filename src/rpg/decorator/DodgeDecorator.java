package rpg.decorator;

import rpg.model.Character;

import java.util.ArrayList;
import java.util.List;

public class DodgeDecorator extends CharacterDecorator {

    public DodgeDecorator(Character base) {
        super(base);
    }

    @Override
    public List<String> getAbilities() {
        List<String> abilities = new ArrayList<>(base.getAbilities());
        abilities.add("Esquive");
        return abilities;
    }
}
