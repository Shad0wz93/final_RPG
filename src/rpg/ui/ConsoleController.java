package rpg.ui;

public class ConsoleController {

    private final ConsoleView view;

    private final CharacterController characterController;
    private final GroupController groupController;
    private final ArmyController armyController;
    private final CombatController combatController;

    public ConsoleController(
            ConsoleView view,
            CharacterController characterController,
            GroupController groupController,
            ArmyController armyController,
            CombatController combatController
    ) {
        this.view = view;
        this.characterController = characterController;
        this.groupController = groupController;
        this.armyController = armyController;
        this.combatController = combatController;
    }

    public void start() throws Exception {

        while (true) {
            view.showMenu();
            String choice = view.ask("");

            switch (choice) {
                case "1" -> characterController.createCharacter();
                case "2" -> characterController.listCharacters();
                case "3" -> groupController.createGroup();
                case "4" -> groupController.addCharacterToGroup();
                case "5" -> groupController.displayGroups();
                case "6" -> armyController.createArmy();
                case "7" -> armyController.addGroupToArmy();
                case "8" -> armyController.displayArmies();
                case "9" -> combatController.launchCombat();
                case "10" -> combatController.replay();
                case "0" -> {
                    view.show("Au revoir !");
                    return;
                }
                default -> view.show("Choix invalide");
            }
        }
    }
}
