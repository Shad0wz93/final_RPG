package rpg.combat;

import rpg.model.Character;

public interface PowerStrategy {
    int compute(Character character);
}
