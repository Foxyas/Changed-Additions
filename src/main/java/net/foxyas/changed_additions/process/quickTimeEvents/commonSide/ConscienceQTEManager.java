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

public class ConscienceQTEManager {

    @OnlyIn(Dist.CLIENT)
    public static class Client {
        private static final HashMap<Player, ConscienceQuickTimeEvent> ClientActiveQTEs = new HashMap<>();

        public static void addQTE(ConscienceQuickTimeEvent qte) {
            qte.setPlayer(Minecraft.getInstance().player);
            ClientActiveQTEs.put(Minecraft.getInstance().player, qte);
        }

        public static void removeQTE(Player player, ConscienceQuickTimeEvent qte) {
            ClientActiveQTEs.remove(player, qte);
        }

        public static ConscienceQuickTimeEvent getActiveQTE(Player player) {
            return ClientActiveQTEs.get(player);
        }

        public static void WhenSync() {
            for (ConscienceQuickTimeEvent qte: ClientActiveQTEs.values()) {
                if (qte.isFinished()) {
                    removeQTE(qte.getPlayer(), qte);
                }
            }
        }

    }
    private static final HashMap<Player, ConscienceQuickTimeEvent> activeQTEs = new HashMap<>();


    public static void addQTE(Player player, ConscienceQuickTimeEvent qte) {
        if (player instanceof ServerPlayer serverPlayer){
            PacketsUtils.sendToPlayer(ChangedAdditionsMod.PACKET_HANDLER,new QTESyncPacket(qte), serverPlayer);
        }
        activeQTEs.put(player, qte);
    }

    public static void removeQTE(Player player, ConscienceQuickTimeEvent qte) {
        if (player instanceof ServerPlayer serverPlayer){
            PacketsUtils.sendToPlayer(ChangedAdditionsMod.PACKET_HANDLER,new QTESyncPacket(qte), serverPlayer);
        }
        activeQTEs.remove(player, qte);
    }

    public static ConscienceQuickTimeEvent getActiveQTE(Player player) {
        return activeQTEs.get(player);
    }

    public static ConscienceQuickTimeEvent getAutoActiveQTE(Player player) {
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
        for (ConscienceQuickTimeEvent qte : activeQTEs.values()) {
            qte.applyKeyInput(inputKey, action);
            if (qte.isFinished()) {
                removeQTE(qte.getPlayer(), qte);
            }
        }
    }

    public static void tickAll() {
        for (ConscienceQuickTimeEvent qte : activeQTEs.values()) {
            qte.tick();
            if (qte.isFinished()) {
                removeQTE(qte.getPlayer(), qte); // remove qte finalizado da lista
            }
        }
    }
}
