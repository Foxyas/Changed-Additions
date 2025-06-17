package net.foxyas.changed_additions.process;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.process.variantsExtraStats.TransfurExtraStats;
import net.foxyas.changed_additions.process.variantsExtraStats.TransfurExtraStatsRegistry;
import net.foxyas.changed_additions.variants.ExtraVariantStats;
import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChangedAdditionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FormsStats {

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        TransfurVariantInstance<?> transfurVariantInstance = ProcessTransfur.getPlayerTransfurVariant(player);
        if (transfurVariantInstance == null) {
            return;
        }
        TransfurExtraStats transfurExtraStats;
        if (player.getLevel().isClientSide()) {
            transfurExtraStats = TransfurExtraStatsRegistry.CLIENT.get(transfurVariantInstance.getFormId());
            // Verifica se o jogador está segurando um item específico, ou se tem alguma condição
            if (transfurVariantInstance.getChangedEntity() instanceof ExtraVariantStats extraVariantStats) {
                event.setNewSpeed(event.getNewSpeed() * extraVariantStats.getBlockBreakSpeedMultiplier()); // More Fast Break
            } else if (TransfurExtraStatsRegistry.hasExtraStats(transfurVariantInstance.getFormId()) && transfurExtraStats != null) {
                event.setNewSpeed(event.getNewSpeed() * transfurExtraStats.getMiningSpeedMultiplier());
            }
        } else {
            transfurExtraStats = TransfurExtraStatsRegistry.get(transfurVariantInstance.getFormId());
            // Verifica se o jogador está segurando um item específico, ou se tem alguma condição
            if (transfurVariantInstance.getChangedEntity() instanceof ExtraVariantStats extraVariantStats) {
                event.setNewSpeed(event.getNewSpeed() * extraVariantStats.getBlockBreakSpeedMultiplier()); // More Fast Break
            } else if (TransfurExtraStatsRegistry.hasExtraStats(transfurVariantInstance.getFormId()) && transfurExtraStats != null) {
                event.setNewSpeed(event.getNewSpeed() * transfurExtraStats.getMiningSpeedMultiplier());
            }
        }


    }

    @SubscribeEvent
    public static void onPlayerTick(LivingEvent.LivingUpdateEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.getLevel().isClientSide()) return;
        TransfurVariantInstance<?> transfurVariantInstance = ProcessTransfur.getPlayerTransfurVariant(player);
        if (transfurVariantInstance == null) {
            return;
        }
        TransfurExtraStats transfurExtraStats = TransfurExtraStatsRegistry.get(transfurVariantInstance.getFormId());
        if (transfurExtraStats == null) {
            return;
        }
        float multiplier = transfurExtraStats.getRegenSpeedMultiplier();
        if (multiplier <= 0) {
            return;
        }

        if (shouldRegenerate(player)) {
            int regenInterval = (int) (20 / multiplier);
            if (regenInterval <= 0 || player.tickCount % regenInterval == 0) {
                player.heal(1.0F);
                player.causeFoodExhaustion(0.125f * multiplier);
            }
        }
    }

    private static boolean shouldRegenerate(Player player) {
        return !player.isCreative() && player.getFoodData().getFoodLevel() >= 18 && player.getHealth() < player.getMaxHealth();
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player.getLevel().isClientSide()) return;
        if (player.isSpectator()) return;
        TransfurVariantInstance<?> transfurVariantInstance = ProcessTransfur.getPlayerTransfurVariant(player);
        if (transfurVariantInstance == null) {
            return;
        }
        TransfurExtraStats transfurExtraStats = TransfurExtraStatsRegistry.get(transfurVariantInstance.getFormId());
        if (transfurExtraStats == null) {
            return;
        }
        float multiplier = transfurExtraStats.getFlySpeedMultiplier();
        if (player.getAbilities().flying && !player.isCreative()) {
            player.getAbilities().setFlyingSpeed(0.05f * multiplier);
            player.onUpdateAbilities();
        } else if (player.getAbilities().flying && player.isCreative() && multiplier > 1) {
            player.getAbilities().setFlyingSpeed(0.05f * multiplier);
            player.onUpdateAbilities();
        }
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.getLevel().isClientSide()) return;
        TransfurVariantInstance<?> transfurVariantInstance = ProcessTransfur.getPlayerTransfurVariant(player);
        if (transfurVariantInstance == null) {
            return;
        }
        TransfurExtraStats transfurExtraStats = TransfurExtraStatsRegistry.get(transfurVariantInstance.getFormId());
        if (transfurExtraStats == null) {
            return;
        }
        float multiplier = transfurExtraStats.getFallDmgMultiplier();
        // Exemplo: reduz dano pela metade
        float distance = event.getDistance();
        float finalResult = distance * multiplier;
        if (finalResult <= 0) {
            event.setCanceled(true);
        }
        event.setDistance(finalResult);
    }

}
