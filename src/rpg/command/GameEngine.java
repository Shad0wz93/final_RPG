package rpg.command;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    private final List<Command> history = new ArrayList<>();

    public void executeCommand(Command command) {
        history.add(command);
        command.execute();
    }

    public void replay() {
        System.out.println("\n🔁 REPLAY");
        history.forEach(Command::execute);
    }
}
