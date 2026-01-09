package rpg.ui;

import rpg.builder.CharacterBuilder;
import rpg.dao.CharacterDao;
import rpg.decorator.*;
import rpg.model.*;
import rpg.observer.CharacterLog;
import rpg.settings.GameSettings;
import rpg.validation.*;

import java.util.List;

public class CharacterController {

    private final ConsoleView view;
    private final CharacterDao characterDao;

    public CharacterController(ConsoleView view, CharacterDao characterDao) {
        this.view = view;
        this.characterDao = characterDao;
    }

    public void createCharacter() {

        while (true) {
            try {
                view.show("Max points : " + GameSettings.getInstance().getMaxStatPoints());

                String name = view.ask("Nom");
                int str = Integer.parseInt(view.ask("Force"));
                int intel = Integer.parseInt(view.ask("Intelligence"));
                int agi = Integer.parseInt(view.ask("Agilité"));

                rpg.model.Character base = new CharacterBuilder()
                        .name(name)
                        .strength(str)
                        .intelligence(intel)
                        .agility(agi)
                        .build();

                ObservableCharacter character = new ObservableCharacter(base);
                character.addObserver(new CharacterLog());

                new NameValidator()
                        .linkWith(new StatsValidator())
                        .validate(character);

                String ability = view.ask("""
                Ajouter une capacité ?
                1 - Invisibilité
                2 - Esquive
                0 - Aucune
                """);

                if ("1".equals(ability)) character = new ObservableCharacter(new InvisibilityDecorator(character));
                else if ("2".equals(ability)) character = new ObservableCharacter(new DodgeDecorator(character));

                String remove = view.ask("Retirer une capacité ? (nom exact ou vide)");
                if (!remove.isBlank()) AbilityRemover.remove(character, remove);

                characterDao.save(character);
                view.show("✅ Personnage créé");
                break;

            } catch (Exception e) {
                view.show("❌ " + e.getMessage());
            }
        }
    }

    public void listCharacters() throws Exception {
        List<rpg.model.Character> characters = characterDao.findAll();
        if (characters.isEmpty()) {
            view.show("Aucun personnage");
            return;
        }
        characters.forEach(c -> view.show(c.toString()));
    }
}
