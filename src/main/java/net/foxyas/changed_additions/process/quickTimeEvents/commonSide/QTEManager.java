package net.foxyas.changed_additions.process.quickTimeEvents.commonSide;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.network.packets.QTESyncPacket;
import net.foxyas.changed_additions.network.packets.utils.PacketsUtils;
import net.foxyas.changed_additions.process.quickTimeEvents.InputKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class QTEManager {

    private static final HashMap<Player, QuickTimeEvent> activeQTEs = new HashMap<>();

    public static void addQTE(Player player, QuickTimeEvent qte) {
        if (player instanceof ServerPlayer serverPlayer) {
            PacketsUtils.sendToPlayer(ChangedAdditionsMod.PACKET_HANDLER, new QTESyncPacket(qte), serverPlayer);
        }
        activeQTEs.put(player, qte);
    }

    public static HashMap<Player, QuickTimeEvent> getActiveQTEs() {
        return activeQTEs;
    }

    public static void removeQTE(Player player, QuickTimeEvent qte) {
        if (player instanceof ServerPlayer serverPlayer) {
            PacketsUtils.sendToPlayer(ChangedAdditionsMod.PACKET_HANDLER, new QTESyncPacket(qte), serverPlayer);
        }
        activeQTEs.remove(player, qte);
    }

    public static QuickTimeEvent getActiveQTE(Player player) {
        return activeQTEs.get(player);
    }

    public static QuickTimeEvent getAutoActiveQTE(Player player) {
        if (player instanceof ServerPlayer) {
            return activeQTEs.get(player);
        }

        if (player instanceof AbstractClientPlayer) {
            return Client.ClientActiveQTEs.get(player);
        }

        // Caso não se encaixe nos tipos conhecidos, tenta pegar do lado do servidor como fallback
        return activeQTEs.get(player);
    }

    public static void TriggerKeyUpdate(InputKey inputKey, int action) {
        for (QuickTimeEvent qte : activeQTEs.values()) {
            qte.applyKeyInput(inputKey, action);
            if (qte.isFinished()) {
                removeQTE(qte.getPlayer(), qte);
            }
        }
    }

    public static void tickAll() {
        if (activeQTEs.isEmpty()) return;

        Iterator<Map.Entry<Player, QuickTimeEvent>> iterator = activeQTEs.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Player, QuickTimeEvent> entry = iterator.next();
            QuickTimeEvent qte = entry.getValue();
            qte.tick();
            if (qte.isFinished()) {
                iterator.remove();
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    public static class Client {
        private static final HashMap<Player, QuickTimeEvent> ClientActiveQTEs = new HashMap<>();

        public static void addQTE(QuickTimeEvent qte) {
            qte.setPlayer(Minecraft.getInstance().player);
            ClientActiveQTEs.put(Minecraft.getInstance().player, qte);
        }

        public static void removeQTE(Player player, QuickTimeEvent qte) {
            ClientActiveQTEs.remove(player, qte);
        }

        public static QuickTimeEvent getActiveQTE(Player player) {
            return ClientActiveQTEs.get(player);
        }

        public static void WhenSync() {
            for (QuickTimeEvent qte : ClientActiveQTEs.values()) {
                if (qte.isFinished()) {
                    removeQTE(qte.getPlayer(), qte);
                }
            }
        }

    }
}
