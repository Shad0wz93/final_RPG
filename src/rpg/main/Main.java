package rpg.main;

import rpg.dao.*;
import rpg.ui.*;
import rpg.composite.*;

public class Main {

    public static void main(String[] args) throws Exception {

        ConsoleView view = new ConsoleView();
        CharacterDao characterDao = new CharacterDao();
        GroupDao groupDao = new GroupDao();
        ArmyDao armyDao = new ArmyDao();
        GroupManager groupManager = new GroupManager();
        ArmyManager armyManager = new ArmyManager();
        CharacterController characterController =
                new CharacterController(view, characterDao);

        GroupController groupController =
                new GroupController(view, groupManager, groupDao, characterDao);

        ArmyController armyController =
                new ArmyController(view, armyManager, armyDao, groupManager);

        CombatController combatController =
                new CombatController(view, characterDao, groupManager, armyManager);

        ConsoleController consoleController =
                new ConsoleController(
                        view,
                        characterController,
                        groupController,
                        armyController,
                        combatController
                );
        consoleController.start();
    }
}
