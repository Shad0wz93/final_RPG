package rpg.composite;

import java.util.ArrayList;
import java.util.List;

public class Group implements GroupComponent {
    private final String name;
    private final List<GroupComponent> children = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }

    public void add(GroupComponent component) {
        children.add(component);
    }

    @Override
    public void display() {
        System.out.println("Groupe: " + name);
        children.forEach(GroupComponent::display);
    }
}
