package rpg.combat;

import rpg.model.Character;

import java.util.Random;

public class DefaultPowerStrategy implements PowerStrategy {

    private final Random random = new Random();

    @Override
    public int compute(Character c) {
        int main = Math.max(
                c.getStrength(),
                Math.max(c.getAgility(), c.getIntelligence())
        );
        return (int) (main * 1.5 + c.totalStats() + random.nextInt(6) + 1);
    }
}
