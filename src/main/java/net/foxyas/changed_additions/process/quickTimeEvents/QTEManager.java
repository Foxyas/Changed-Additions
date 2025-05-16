package net.foxyas.changed_additions.process.quickTimeEvents;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

public class QTEManager {
    private static final HashMap<Player, QuickTimeEvent> activeQTEs = new HashMap<>();

    public static void addQTE(Player player, QuickTimeEvent qte) {
        activeQTEs.put(player, qte);
    }

    public static void removeQTE(Player player, QuickTimeEvent qte) {
        activeQTEs.remove(player, qte);
    }

    public static QuickTimeEvent getActiveQTE(Player player) {
        return activeQTEs.get(player);
    }

    public static void TriggerKeyUpdate(InputKey inputKey, int action) {
        Iterator<QuickTimeEvent> it = activeQTEs.values().iterator();
        while (it.hasNext()) {
            QuickTimeEvent qte = it.next();
            qte.applyKeyInput(inputKey, action);
            if (qte.isFinished()) {
                it.remove(); // remove qte finalizado da lista
            }
        }
    }

    public static void tickAll() {
        Iterator<QuickTimeEvent> it = activeQTEs.values().iterator();
        while (it.hasNext()) {
            QuickTimeEvent qte = it.next();
            qte.tick();
            if (qte.isFinished()) {
                it.remove(); // remove qte finalizado da lista
            }
        }
    }
}
