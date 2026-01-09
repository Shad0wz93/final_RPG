package rpg.composite;

import java.util.ArrayList;
import java.util.List;

public class Army implements GroupComponent {

    private final String name;
    private final List<Group> groups = new ArrayList<>();

    public Army(String name) {
        this.name = name;
    }

    public void add(Group group) {
        groups.add(group);
    }

    @Override
    public int getPower() {
        return groups.stream()
                .mapToInt(Group::getPower)
                .sum();
    }

    @Override
    public void display() {
        System.out.println("Armée : " + name);
        groups.forEach(Group::display);
    }

    public List<GroupComponent> getChildren() {
        return new ArrayList<>(groups);
    }

    public String getName() {
        return name;
    }
}
