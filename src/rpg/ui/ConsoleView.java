package rpg.ui;

import java.util.Scanner;

public class ConsoleView {

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        System.out.println("\n=== RPG ===");
        System.out.println("1. Créer un personnage");
        System.out.println("2. Lister les personnages");
        System.out.println("3. Créer un groupe");
        System.out.println("4. Ajouter un personnage à un groupe");
        System.out.println("5. Afficher les groupes");
        System.out.println("6. Créer une armée");
        System.out.println("7. Ajouter un groupe à une armée");
        System.out.println("8. Afficher les armées");
        System.out.println("9. Quitter");
        System.out.print("> ");
    }

    public String ask(String message) {
        System.out.print(message + " : ");
        return scanner.nextLine();
    }

    public void show(String message) {
        System.out.println(message);
    }
}
