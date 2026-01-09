package rpg.combat;

import rpg.model.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceUtil {

    static final Random random = new Random();

    public static int rollForCharacter(Character c) {

        int str = c.getStrength();
        int agi = c.getAgility();
        int intel = c.getIntelligence();

        int main = Math.max(str, Math.max(agi, intel));
        List<Integer> possible = new ArrayList<>();

        if (str != main) possible.add(str);
        if (agi != main) possible.add(agi);
        if (intel != main) possible.add(intel);

        int chosen = possible.get(random.nextInt(possible.size()));
        int dice = random.nextInt(6) + 1;

        return chosen + dice;
    }
}
