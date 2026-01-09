package rpg.combat;

import rpg.model.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DefaultPowerStrategy implements PowerStrategy {

    private final Random random = new Random();

    @Override
    public int compute(Character c) {

        int str = c.getStrength();
        int agi = c.getAgility();
        int intel = c.getIntelligence();

        int max = Math.max(str, Math.max(agi, intel));

        List<Integer> others = new ArrayList<>();
        if (str != max) others.add(str);
        if (agi != max) others.add(agi);
        if (intel != max) others.add(intel);

        int dice = others.isEmpty()
                ? random.nextInt(6) + 1
                : others.get(random.nextInt(others.size())) + random.nextInt(6) + 1;

        return (int) (max * 1.5) + dice;
    }
}
