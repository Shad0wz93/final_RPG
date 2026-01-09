package rpg.ui;

import rpg.builder.CharacterBuilder;
import rpg.composite.CharacterLeaf;
import rpg.composite.Group;
import rpg.composite.GroupManager;
import rpg.dao.CharacterDao;
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

    public ConsoleController(ConsoleView view, CharacterDao dao) {
        this.view = view;
        this.dao = dao;
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
                case "6" -> {
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

                String invis = view.ask("Ajouter invisibilité ? (oui/non)");
                if (invis.equalsIgnoreCase("oui")) {
                    new InvisibilityDecorator(character);
                }

                dao.save(character);
                view.show("Personnage sauvegardé !");
                break;

            } catch (IllegalArgumentException e) {
                view.show("Erreur : " + e.getMessage());
                view.show("Merci de recommencer la saisie.\n");

            } catch (Exception e) {
                view.show("❌ Erreur technique : " + e.getMessage());
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

        groupManager.getAllGroups()
                .values()
                .forEach(Group::display);
    }
}
