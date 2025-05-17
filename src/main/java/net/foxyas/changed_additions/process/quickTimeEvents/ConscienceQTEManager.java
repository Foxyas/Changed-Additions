package net.foxyas.changed_additions.process.quickTimeEvents;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Iterator;

public class ConscienceQTEManager {
    private static final HashMap<Player, ConscienceQuickTimeEvent> activeQTEs = new HashMap<>();

    public static void addQTE(Player player, ConscienceQuickTimeEvent qte) {
        activeQTEs.put(player, qte);
    }

    public static void removeQTE(Player player, ConscienceQuickTimeEvent qte) {
        activeQTEs.remove(player, qte);
    }

    public static ConscienceQuickTimeEvent getActiveQTE(Player player) {
        return activeQTEs.get(player);
    }

    public static void TriggerKeyUpdate(InputKey inputKey, int action) {
        Iterator<ConscienceQuickTimeEvent> it = activeQTEs.values().iterator();
        while (it.hasNext()) {
            ConscienceQuickTimeEvent qte = it.next();
            qte.applyKeyInput(inputKey, action);
            if (qte.isFinished()) {
                it.remove(); // remove qte finalizado da lista
            }
        }
    }

    public static void tickAll() {
        Iterator<ConscienceQuickTimeEvent> it = activeQTEs.values().iterator();
        while (it.hasNext()) {
            ConscienceQuickTimeEvent qte = it.next();
            qte.tick();
            if (qte.isFinished()) {
                it.remove(); // remove qte finalizado da lista
            }
        }
    }
}
