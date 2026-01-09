package rpg.composite;

import java.util.HashMap;
import java.util.Map;

public class GroupManager {

    private final Map<String, Group> groups = new HashMap<>();

    public void createGroup(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nom de groupe invalide");
        if (groups.containsKey(name))
            throw new IllegalArgumentException("Le groupe existe déjà");

        groups.put(name, new Group(name));
    }

    public Group getGroup(String name) {
        return groups.get(name);
    }

    public Map<String, Group> getAllGroups() {
        return groups;
    }
}
