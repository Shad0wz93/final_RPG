package rpg.composite;

import java.util.ArrayList;
import java.util.List;

public class Army implements GroupComponent {

    private final String name;
    private final List<GroupComponent> groups = new ArrayList<>();

    public Army(String name) {
        this.name = name;
    }

    public void add(GroupComponent component) {
        groups.add(component);
    }

    public String getName() {
        return name;
    }

    @Override
    public void display() {
        System.out.println("Armée : " + name);
        groups.forEach(GroupComponent::display);
    }
}
