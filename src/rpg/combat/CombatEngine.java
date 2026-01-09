package rpg.combat;

import rpg.composite.GroupComponent;
import rpg.model.Character;
import rpg.observer.CombatObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatEngine {

    private final List<CombatObserver> observers = new ArrayList<>();
    private final Random random = new Random();

    private PowerStrategy strategy = new DefaultPowerStrategy();

    public void addObserver(CombatObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String message) {
        observers.forEach(o -> o.onCombatEvent(message));
    }

    private boolean tryAbility(Character defender) {

        if (defender.hasAbility("Invisibilité") && random.nextDouble() < 0.30) {
            notifyObservers("👻 " + defender.getName() + " devient invisible !");
            return true;
        }

        if (defender.hasAbility("Esquive") && random.nextDouble() < 0.20) {
            notifyObservers("🤸 " + defender.getName() + " esquive !");
            return true;
        }

        return false;
    }

    public void fight(Character a, Character b) {

        notifyObservers("⚔️ Combat : " + a.getName() + " VS " + b.getName());

        if (tryAbility(b)) {
            notifyObservers("🔄 Le tour recommence");
            fight(a, b);
            return;
        }

        int pa = strategy.compute(a);
        int pb = strategy.compute(b);

        notifyObservers(a.getName() + " puissance = " + pa);
        notifyObservers(b.getName() + " puissance = " + pb);

        if (pa > pb) notifyObservers("🏆 Vainqueur : " + a.getName());
        else if (pb > pa) notifyObservers("🏆 Vainqueur : " + b.getName());
        else notifyObservers("🤝 Match nul");
    }

    public void fight(GroupComponent a, GroupComponent b) {

        notifyObservers("⚔️ Combat Groupe / Armée");

        int pa = a.getPower();
        int pb = b.getPower();

        if (pa > pb) notifyObservers("🏆 Groupe 1 gagne");
        else if (pb > pa) notifyObservers("🏆 Groupe 2 gagne");
        else notifyObservers("🤝 Match nul");
    }
}
