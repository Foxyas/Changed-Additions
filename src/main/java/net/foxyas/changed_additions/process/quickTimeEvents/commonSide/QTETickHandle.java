package net.foxyas.changed_additions.process.quickTimeEvents.commonSide;

import net.foxyas.changed_additions.process.quickTimeEvents.serverSide.QTEPendingManager;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class QTETickHandle {

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!QTEManager.getActiveQTEs().isEmpty()) {
                QTEManager.tickAll();
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        /*Player player = event.getPlayer();
        if (!player.getLevel().isClientSide()) {
            CompoundTag tag = player.saveWithoutId(new CompoundTag());
            if (tag.contains("QTE")) {
                QuickTimeEvent qte = QuickTimeEvent.loadFromTag(player, tag.getCompound("QTE"));
                if (!qte.isFinished()) {
                    QTEManager.addQTE(player, qte);
                }
            }
        }*/
        if (!event.getEntity().getLevel().isClientSide && event.getEntity() instanceof ServerPlayer serverPlayer) {
            QTEPendingManager.trySend(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onTransfurAttempt(ProcessTransfur.TransfurAttackEvent event){
        if (!event.target.getLevel().isClientSide() && event.target instanceof Player player) {
            if (QTEManager.getActiveQTE(player) != null) {
                player.invulnerableTime = 2;
                player.hurtDuration = 2;
                player.hurtTime = player.hurtDuration;
                event.setCanceled(true);
            }
        }
    }

    /*@SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getPlayer();
        if (!player.getLevel().isClientSide()) {
            QuickTimeEvent qte = QTEManager.getActiveQTE(player);
            if (!qte.isFinished()) {
                QTEManager.removeQTE(player, qte);
            }
        }
    }*/

}
