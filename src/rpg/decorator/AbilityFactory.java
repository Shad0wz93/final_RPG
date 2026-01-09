package rpg.decorator;

import rpg.model.Character;

public class AbilityFactory {

    public static Character apply(Character base, String ability) {
        return switch (ability) {
            case "Invisibilité" -> new InvisibilityDecorator(base);
            case "Esquive" -> new DodgeDecorator(base);
            default -> base;
        };
    }
}
