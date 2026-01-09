package rpg.command;

import rpg.combat.CombatEngine;
import rpg.composite.GroupComponent;

public class GroupFightCommand implements Command {

    private final CombatEngine engine;
    private final GroupComponent g1;
    private final GroupComponent g2;

    public GroupFightCommand(CombatEngine engine, GroupComponent g1, GroupComponent g2) {
        this.engine = engine;
        this.g1 = g1;
        this.g2 = g2;
    }

    @Override
    public void execute() {
        engine.fight(g1, g2);
    }
}
