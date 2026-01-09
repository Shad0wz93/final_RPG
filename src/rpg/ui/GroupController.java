package rpg.ui;

import rpg.composite.*;
import rpg.dao.CharacterDao;
import rpg.dao.GroupDao;

public class GroupController {

    private final ConsoleView view;
    private final GroupManager groupManager;
    private final GroupDao groupDao;
    private final CharacterDao characterDao;

    public GroupController(ConsoleView view,
                           GroupManager groupManager,
                           GroupDao groupDao,
                           CharacterDao characterDao) {
        this.view = view;
        this.groupManager = groupManager;
        this.groupDao = groupDao;
        this.characterDao = characterDao;
    }

    public void createGroup() {
        try {
            String name = view.ask("Nom du groupe");
            groupManager.createGroup(name);
            groupDao.saveGroup(name);
            view.show("✅ Groupe créé");
        } catch (Exception e) {
            view.show("❌ " + e.getMessage());
        }
    }

    public void addCharacterToGroup() throws Exception {
        displayGroups();
        String groupName = view.ask("Choisir le groupe");
        Group group = groupManager.getGroup(groupName);
        if (group == null) return;

        characterDao.findAll().forEach(c ->
                view.show("- " + c.getName() + " " + c.getAbilities())
        );

        String charName = view.ask("Choisir le personnage");
        var c = characterDao.findByName(charName);
        if (c == null) return;

        group.add(new CharacterLeaf(c));
        groupDao.addCharacterToGroup(groupName, charName);
        view.show("✅ Ajout effectué");
    }

    public void displayGroups() {
        groupManager.getAllGroups().values().forEach(Group::display);
    }
}
