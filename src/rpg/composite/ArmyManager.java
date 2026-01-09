package rpg.composite;

import java.util.HashMap;
import java.util.Map;

public class ArmyManager {

    private final Map<String, Army> armies = new HashMap<>();

    public void createArmy(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nom d'armée invalide");
        if (armies.containsKey(name))
            throw new IllegalArgumentException("L'armée existe déjà");

        armies.put(name, new Army(name));
    }

    public Army getArmy(String name) {
        return armies.get(name);
    }

    public Map<String, Army> getAllArmies() {
        return armies;
    }
}