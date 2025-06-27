package net.foxyas.changed_additions.process.quickTimeEvents.commonSide;

import net.foxyas.changed_additions.process.quickTimeEvents.serverSide.QTEPendingManager;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class QTETickHandle {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;

        QuickTimeEvent qte = QTEManager.getActiveQTE(event.player);
        if (qte != null) {
            qte.tick();
            if (qte.isFinished()) {
                QTEManager.removeQTE(event.player, qte);
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        /*Player player = event.getPlayer();
        if (!player.level().isClientSide()) {
            CompoundTag tag = player.saveWithoutId(new CompoundTag());
            if (tag.contains("QTE")) {
                QuickTimeEvent qte = QuickTimeEvent.loadFromTag(player, tag.getCompound("QTE"));
                if (!qte.isFinished()) {
                    QTEManager.addQTE(player, qte);
                }
            }
        }*/
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof ServerPlayer serverPlayer) {
            QTEPendingManager.trySend(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onTransfurAttempt(ProcessTransfur.TransfurAttackEvent event){
        if (!event.target.level().isClientSide() && event.target instanceof Player player) {
            if (QTEManager.getActiveQTE(player) != null && QTEManager.getActiveQTE(player).getType() == QuickTimeEventType.STRUGGLE) {
                player.invulnerableTime = 2;
                player.hurtDuration = 2;
                player.hurtTime = player.hurtDuration;
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onHitAttempt(LivingAttackEvent event){
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof Player player) {
            if (QTEManager.getActiveQTE(player) != null && QTEManager.getActiveQTE(player).getType() == QuickTimeEventType.FIGHT_TO_KEEP_CONSCIENCE && !QTEManager.getActiveQTE(player).isFinished()) {
                event.setCanceled(true);
            }
        }
    }

    /*@SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getPlayer();
        if (!player.level().isClientSide()) {
            QuickTimeEvent qte = QTEManager.getActiveQTE(player);
            if (!qte.isFinished()) {
                QTEManager.removeQTE(player, qte);
            }
        }
    }*/

}
