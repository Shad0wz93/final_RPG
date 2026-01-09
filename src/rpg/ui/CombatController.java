package rpg.ui;

import rpg.combat.CombatEngine;
import rpg.command.*;
import rpg.composite.*;
import rpg.dao.CharacterDao;
import rpg.observer.CombatLog;

public class CombatController {

    private final ConsoleView view;
    private final CharacterDao characterDao;
    private final GroupManager groupManager;
    private final ArmyManager armyManager;

    private final CombatEngine combatEngine = new CombatEngine();
    private final GameEngine gameEngine = new GameEngine();

    public CombatController(ConsoleView view,
                            CharacterDao characterDao,
                            GroupManager groupManager,
                            ArmyManager armyManager) {

        this.view = view;
        this.characterDao = characterDao;
        this.groupManager = groupManager;
        this.armyManager = armyManager;

        combatEngine.addObserver(new CombatLog());
    }

    public void launchCombat() throws Exception {

        String type = view.ask("""
        Type de combat :
        1 - Personnage vs Personnage
        2 - Groupe vs Groupe
        3 - Armée vs Armée
        """);

        switch (type) {
            case "1" -> {
                var a = characterDao.findByName(view.ask("Attaquant"));
                var b = characterDao.findByName(view.ask("Défenseur"));
                if (a != null && b != null)
                    gameEngine.executeCommand(new AttackCommand(combatEngine, a, b));
            }
            case "2" -> {
                Group g1 = groupManager.getGroup(view.ask("Groupe 1"));
                Group g2 = groupManager.getGroup(view.ask("Groupe 2"));
                if (g1 != null && g2 != null)
                    gameEngine.executeCommand(new GroupFightCommand(combatEngine, g1, g2));
            }
            case "3" -> {
                Army a1 = armyManager.getArmy(view.ask("Armée 1"));
                Army a2 = armyManager.getArmy(view.ask("Armée 2"));
                if (a1 != null && a2 != null)
                    gameEngine.executeCommand(new GroupFightCommand(combatEngine, a1, a2));
            }
        }
    }

    public void replay() {
        gameEngine.replay();
    }
}
