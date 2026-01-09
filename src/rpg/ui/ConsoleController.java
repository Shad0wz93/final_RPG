package rpg.ui;

import rpg.builder.CharacterBuilder;
import rpg.composite.*;
import rpg.dao.*;
import rpg.decorator.DodgeDecorator;
import rpg.decorator.InvisibilityDecorator;
import rpg.model.Character;
import rpg.settings.GameSettings;
import rpg.validation.NameValidator;
import rpg.validation.StatsValidator;

import java.util.List;

public class ConsoleController {

    private final ConsoleView view;
    private final CharacterDao dao;

    private final GroupManager groupManager = new GroupManager();
    private final ArmyManager armyManager = new ArmyManager();

    private final GroupDao groupDao = new GroupDao();
    private final ArmyDao armyDao = new ArmyDao();

    public ConsoleController(ConsoleView view, CharacterDao dao) throws Exception {
        this.view = view;
        this.dao = dao;
        loadAll();
    }

    private void loadAll() throws Exception {

        groupDao.loadGroupCharacters().forEach((groupName, characters) -> {
            Group group = new Group(groupName);
            groupManager.getAllGroups().put(groupName, group);

            characters.forEach(charName -> {
                try {
                    Character c = dao.findByName(charName);
                    if (c != null) {
                        group.add(new CharacterLeaf(c));
                    }
                } catch (Exception ignored) {}
            });
        });

        armyDao.loadArmyGroups().forEach((armyName, groups) -> {
            Army army = new Army(armyName);
            armyManager.getAllArmies().put(armyName, army);

            groups.forEach(groupName -> {
                Group g = groupManager.getGroup(groupName);
                if (g != null) {
                    army.add(g);
                }
            });
        });
    }
    public void start() throws Exception {
        while (true) {
            view.showMenu();
            String choice = view.ask("");

            switch (choice) {
                case "1" -> createCharacter();
                case "2" -> listCharacters();
                case "3" -> createGroup();
                case "4" -> addCharacterToGroup();
                case "5" -> displayGroups();
                case "6" -> createArmy();
                case "7" -> addGroupToArmy();
                case "8" -> displayArmies();
                case "9" -> {
                    view.show("Au revoir !");
                    return;
                }
                default -> view.show("Choix invalide");
            }
        }
    }

    private void createCharacter() {
        while (true) {
            try {
                view.show("Maximum de points autorisés : "
                        + GameSettings.getInstance().getMaxStatPoints());

                String name = view.ask("Nom du personnage");
                int str = Integer.parseInt(view.ask("Force"));
                int intel = Integer.parseInt(view.ask("Intelligence"));
                int agi = Integer.parseInt(view.ask("Agilité"));

                Character character = new CharacterBuilder()
                        .name(name)
                        .strength(str)
                        .intelligence(intel)
                        .agility(agi)
                        .build();

                new NameValidator()
                        .linkWith(new StatsValidator())
                        .validate(character);

                String abilityChoice = view.ask("""
                Ajouter une capacité ?
                1 - Invisibilité
                2 - Esquive
                0 - Aucune
                """);
                switch (abilityChoice) {
                    case "1" -> character = new InvisibilityDecorator(character);
                    case "2" -> character = new DodgeDecorator(character);
                }


                dao.save(character);
                view.show("Personnage sauvegardé !");
                break;

            } catch (IllegalArgumentException e) {
                view.show("Erreur : " + e.getMessage());
                view.show("Merci de recommencer.\n");
            } catch (Exception e) {
                view.show("Erreur technique : " + e.getMessage());
                return;
            }
        }
    }

    private void listCharacters() throws Exception {
        List<Character> characters = dao.findAll();
        if (characters.isEmpty()) {
            view.show("Aucun personnage enregistré");
        } else {
            characters.forEach(c -> view.show(c.toString()));
        }
    }

    private void createGroup() {
        try {
            String name = view.ask("Nom du groupe");
            groupManager.createGroup(name);
            groupDao.saveGroup(name);
            view.show("Groupe créé");
        } catch (Exception e) {
            view.show("❌ " + e.getMessage());
        }
    }

    private void addCharacterToGroup() {
        try {
            String groupName = view.ask("Nom du groupe");
            Group group = groupManager.getGroup(groupName);

            if (group == null) {
                view.show("Groupe introuvable");
                return;
            }

            String charName = view.ask("Nom du personnage");
            Character character = dao.findByName(charName);

            if (character == null) {
                view.show("Personnage introuvable");
                return;
            }

            group.add(new CharacterLeaf(character));
            groupDao.addCharacterToGroup(groupName, charName);
            view.show("Personnage ajouté au groupe");

        } catch (Exception e) {
            view.show("❌ " + e.getMessage());
        }
    }

    private void displayGroups() {
        if (groupManager.getAllGroups().isEmpty()) {
            view.show("Aucun groupe créé");
            return;
        }
        groupManager.getAllGroups().values().forEach(Group::display);
    }

    private void createArmy() {
        try {
            String name = view.ask("Nom de l'armée");
            armyManager.createArmy(name);
            armyDao.saveArmy(name);
            view.show("Armée créée");
        } catch (Exception e) {
            view.show("❌ " + e.getMessage());
        }
    }

    private void addGroupToArmy() {
        try {
            String armyName = view.ask("Nom de l'armée");
            Army army = armyManager.getArmy(armyName);

            if (army == null) {
                view.show("Armée introuvable");
                return;
            }

            String groupName = view.ask("Nom du groupe");
            Group group = groupManager.getGroup(groupName);

            if (group == null) {
                view.show("Groupe introuvable");
                return;
            }

            army.add(group);
            armyDao.addGroupToArmy(armyName, groupName);
            view.show("Groupe ajouté à l'armée");

        } catch (Exception e) {
            view.show("❌ " + e.getMessage());
        }
    }

    private void displayArmies() {
        if (armyManager.getAllArmies().isEmpty()) {
            view.show("Aucune armée créée");
            return;
        }
        armyManager.getAllArmies().values().forEach(Army::display);
    }
}
