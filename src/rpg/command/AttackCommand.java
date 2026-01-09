package rpg.command;

import rpg.combat.CombatEngine;
import rpg.model.Character;

public class AttackCommand implements Command {

    private final CombatEngine engine;
    private final Character attacker;
    private final Character defender;

    public AttackCommand(CombatEngine engine, Character attacker, Character defender) {
        this.engine = engine;
        this.attacker = attacker;
        this.defender = defender;
    }

    @Override
    public void execute() {
        engine.fight(attacker, defender);
    }
}
