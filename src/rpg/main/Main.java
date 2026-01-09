package rpg.main;

import rpg.dao.CharacterDao;
import rpg.ui.ConsoleController;
import rpg.ui.ConsoleView;

public class Main {

    public static void main(String[] args) throws Exception {

        ConsoleView view = new ConsoleView();
        CharacterDao dao = new CharacterDao();

        System.out.println("Personnages en base :");
        dao.findAll().forEach(System.out::println);

        new ConsoleController(view, dao).start();
    }
}
