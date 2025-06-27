package net.foxyas.changed_additions.process.variantsExtraStats;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.network.packets.SyncTransfurVisionsPacket;
import net.foxyas.changed_additions.network.packets.SyncTransfursStatsPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static net.foxyas.changed_additions.network.packets.utils.PacketsUtils.sendToPlayer;

@Mod.EventBusSubscriber
public class TransfurExtraStatsSync {
    public static void syncTo(ServerPlayer player) {
        List<TransfurExtraStats> all = new ArrayList<>(TransfurExtraStatsRegistry.getAll());
        sendToPlayer(ChangedAdditionsMod.PACKET_HANDLER, new SyncTransfursStatsPacket(all), player);
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            TransfurExtraStatsSync.syncTo(serverPlayer);
        }
    }
}
