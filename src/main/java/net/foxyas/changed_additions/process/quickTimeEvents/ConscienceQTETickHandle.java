package net.foxyas.changed_additions.process.quickTimeEvents;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class ConscienceQTETickHandle {

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ConscienceQTEManager.tickAll();
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        CompoundTag tag = player.saveWithoutId(new CompoundTag());
        if (tag.contains("QTE")) {
            ConscienceQuickTimeEvent qte = ConscienceQuickTimeEvent.loadFromTag(player, tag.getCompound("QTE"));
            if (!qte.isFinished()) {
                ConscienceQTEManager.addQTE(player, qte);
            }
        }
    }

}
