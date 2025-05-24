package net.foxyas.changed_additions.process.quickTimeEvents.serverSide;

import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QTEManager;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QuickTimeEvent;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Classe utilitária
public class QTEPendingManager {
    private static final Map<UUID, QuickTimeEvent> pendingQTEs = new HashMap<>();

    public static void queueQTE(ServerPlayer player, QuickTimeEvent qte) {
        pendingQTEs.put(player.getUUID(), qte);
    }

    public static void trySend(ServerPlayer player) {
        QuickTimeEvent qte = pendingQTEs.remove(player.getUUID());
        if (qte != null) {
            QTEManager.addQTE(player, qte);
        }
    }
}
