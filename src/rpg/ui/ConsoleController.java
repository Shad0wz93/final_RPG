package rpg.ui;

import rpg.builder.CharacterBuilder;
import rpg.combat.CombatEngine;
import rpg.composite.*;
import rpg.dao.*;
import rpg.decorator.DodgeDecorator;
import rpg.decorator.InvisibilityDecorator;
import rpg.model.Character;
import rpg.observer.CombatLog;
import rpg.settings.GameSettings;
import rpg.validation.NameValidator;
import rpg.validation.StatsValidator;
import rpg.command.GameEngine;

import java.util.List;

public class ConsoleController {

    private final ConsoleView view;
    private final CharacterDao characterDao;

    private final GroupManager groupManager = new GroupManager();
    private final ArmyManager armyManager = new ArmyManager();

    private final GroupDao groupDao = new GroupDao();
    private final ArmyDao armyDao = new ArmyDao();

    private final CombatEngine combatEngine = new CombatEngine();
    private final GameEngine gameEngine = new GameEngine();

    public ConsoleController(ConsoleView view, CharacterDao characterDao) throws Exception {
        this.view = view;
        this.characterDao = characterDao;

        combatEngine.addObserver(new CombatLog());
        loadAll();
    }

    private void loadAll() throws Exception {

        groupDao.loadGroupCharacters().forEach((groupName, characters) -> {
            Group group = new Group(groupName);
            groupManager.getAllGroups().put(groupName, group);

            characters.forEach(charName -> {
                try {
                    Character c = characterDao.findByName(charName);
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
                if (g != null) army.add(g);
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
                case "9" -> launchCombat();
                case "10" -> gameEngine.replay();
                case "0" -> {
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
                view.show("Max points : " + GameSettings.getInstance().getMaxStatPoints());

                String name = view.ask("Nom");
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

                String ability = view.ask("""
                Ajouter une capacité ?
                1 - Invisibilité (30%)
                2 - Esquive (20%)
                0 - Aucune
                """);

                if ("1".equals(ability)) {
                    character = new InvisibilityDecorator(character);
                } else if ("2".equals(ability)) {
                    character = new DodgeDecorator(character);
                }

                characterDao.save(character);
                view.show("✅ Personnage créé");
                break;

            } catch (Exception e) {
                view.show("❌ " + e.getMessage());
            }
        }
    }

    private void listCharacters() throws Exception {

        List<Character> characters = characterDao.findAll();

        if (characters.isEmpty()) {
            view.show("Aucun personnage");
            return;
        }

        view.show("📜 Personnages :");
        characters.forEach(c ->
                view.show("- " + c.getName()
                        + " [STR=" + c.getStrength()
                        + ", INT=" + c.getIntelligence()
                        + ", AGI=" + c.getAgility()
                        + ", abilities=" + c.getAbilities() + "]")
        );
    }

    private void createGroup() {
        try {
            String name = view.ask("Nom du groupe");
            groupManager.createGroup(name);
            groupDao.saveGroup(name);
            view.show("✅ Groupe créé");
        } catch (Exception e) {
            view.show("❌ " + e.getMessage());
        }
    }

    private void addCharacterToGroup() throws Exception {

        showGroupsRecap();
        String groupName = view.ask("Choisir le groupe");
        Group group = groupManager.getGroup(groupName);
        if (group == null) return;

        showCharactersRecap();
        String charName = view.ask("Choisir le personnage");
        Character c = characterDao.findByName(charName);
        if (c == null) return;

        group.add(new CharacterLeaf(c));
        groupDao.addCharacterToGroup(groupName, charName);

        view.show("✅ Ajout effectué");
    }

    private void displayGroups() {
        showGroupsRecap();
    }

    private void createArmy() {
        try {
            String name = view.ask("Nom de l'armée");
            armyManager.createArmy(name);
            armyDao.saveArmy(name);
            view.show("✅ Armée créée");
        } catch (Exception e) {
            view.show("❌ " + e.getMessage());
        }
    }

    private void addGroupToArmy() throws Exception {

        showArmiesRecap();
        String armyName = view.ask("Choisir l'armée");
        Army army = armyManager.getArmy(armyName);
        if (army == null) return;

        showGroupsRecap();
        String groupName = view.ask("Choisir le groupe");
        Group group = groupManager.getGroup(groupName);
        if (group == null) return;

        army.add(group);
        armyDao.addGroupToArmy(armyName, groupName);

        view.show("✅ Groupe ajouté à l’armée");
    }

    private void displayArmies() {
        showArmiesRecap();
    }

    private void launchCombat() throws Exception {

        String type = view.ask("""
        Type de combat :
        1 - Personnage vs Personnage
        2 - Groupe vs Groupe
        3 - Armée vs Armée
        """);

        switch (type) {

            case "1" -> {
                showCharactersRecap();
                Character a = characterDao.findByName(view.ask("Attaquant"));
                Character b = characterDao.findByName(view.ask("Défenseur"));
                if (a != null && b != null)
                    gameEngine.executeCommand(() -> combatEngine.fight(a, b));
            }

            case "2" -> {
                showGroupsRecap();
                Group g1 = groupManager.getGroup(view.ask("Groupe 1"));
                Group g2 = groupManager.getGroup(view.ask("Groupe 2"));
                if (g1 != null && g2 != null)
                    gameEngine.executeCommand(() -> combatEngine.fight(g1, g2));
            }

            case "3" -> {
                showArmiesRecap();
                Army a1 = armyManager.getArmy(view.ask("Armée 1"));
                Army a2 = armyManager.getArmy(view.ask("Armée 2"));
                if (a1 != null && a2 != null)
                    gameEngine.executeCommand(() -> combatEngine.fight(a1, a2));
            }
        }
    }

    private void showCharactersRecap() throws Exception {

        view.show("👤 Personnages disponibles :");
        characterDao.findAll().forEach(c ->
                view.show("- " + c.getName() + " " + c.getAbilities())
        );
    }

    private void showGroupsRecap() {

        view.show("👥 Groupes disponibles :");
        groupManager.getAllGroups().values().forEach(group -> {
            view.show("- " + group.getName());
            group.getChildren().forEach(c -> {
                if (c instanceof CharacterLeaf leaf) {
                    view.show("   • " + leaf.getCharacter().getName()
                            + " " + leaf.getCharacter().getAbilities());
                }
            });
        });
    }

    private void showArmiesRecap() {

        view.show("🛡️ Armées disponibles :");
        armyManager.getAllArmies().values()
                .forEach(a -> view.show("- " + a.getName()));
    }
}
