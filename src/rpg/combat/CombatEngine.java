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

    // === CAPACITÉS DÉFENSIVES ===
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

    // === 1 vs 1 ===
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

    // === GROUPE / ARMÉE ===
    public void fight(GroupComponent a, GroupComponent b) {

        notifyObservers("⚔️ Combat Groupe / Armée");

        int pa = computeGroupPower(a, "Groupe 1");
        int pb = computeGroupPower(b, "Groupe 2");

        notifyObservers("Groupe 1 puissance = " + pa);
        notifyObservers("Groupe 2 puissance = " + pb);

        if (pa > pb) notifyObservers("🏆 Groupe 1 gagne");
        else if (pb > pa) notifyObservers("🏆 Groupe 2 gagne");
        else notifyObservers("🤝 Match nul");
    }

    // === CALCUL RÉCURSIF ===
    private int computeGroupPower(GroupComponent component, String label) {

        int total = 0;

        try {
            var childrenMethod = component.getClass().getMethod("getChildren");
            @SuppressWarnings("unchecked")
            List<GroupComponent> children =
                    (List<GroupComponent>) childrenMethod.invoke(component);

            for (GroupComponent child : children) {
                total += computeGroupPower(child, label);
            }

        } catch (NoSuchMethodException e) {
            // feuille → personnage
            if (component instanceof rpg.composite.CharacterLeaf leaf) {
                Character c = leaf.getCharacter();
                int p = strategy.compute(c);
                notifyObservers(" - " + c.getName() + " puissance = " + p);
                total += p;
            }
        } catch (Exception ignored) {}

        return total;
    }
}
