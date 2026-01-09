package rpg.ui;

import rpg.composite.*;
import rpg.dao.ArmyDao;

public class ArmyController {

    private final ConsoleView view;
    private final ArmyManager armyManager;
    private final ArmyDao armyDao;
    private final GroupManager groupManager;

    public ArmyController(ConsoleView view,
                          ArmyManager armyManager,
                          ArmyDao armyDao,
                          GroupManager groupManager) {
        this.view = view;
        this.armyManager = armyManager;
        this.armyDao = armyDao;
        this.groupManager = groupManager;
    }

    public void createArmy() {
        try {
            String name = view.ask("Nom de l'armée");
            armyManager.createArmy(name);
            armyDao.saveArmy(name);
            view.show("✅ Armée créée");
        } catch (Exception e) {
            view.show("❌ " + e.getMessage());
        }
    }

    public void addGroupToArmy() throws Exception {
        displayArmies();
        String armyName = view.ask("Choisir l'armée");
        Army army = armyManager.getArmy(armyName);
        if (army == null) return;

        groupManager.getAllGroups().values().forEach(g ->
                view.show("- " + g.getName())
        );

        String groupName = view.ask("Choisir le groupe");
        Group group = groupManager.getGroup(groupName);
        if (group == null) return;

        army.add(group);
        armyDao.addGroupToArmy(armyName, groupName);
        view.show("✅ Groupe ajouté à l’armée");
    }

    public void displayArmies() {
        armyManager.getAllArmies().values().forEach(Army::display);
    }
}
