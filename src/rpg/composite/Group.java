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
    public int getPower() {
        return children.stream()
                .mapToInt(GroupComponent::getPower)
                .sum();
    }

    @Override
    public void display() {
        System.out.println("Groupe : " + name);
        children.forEach(GroupComponent::display);
    }

    public String getName() {
        return name;
    }

    public List<GroupComponent> getChildren() {
        return children;
    }
}
