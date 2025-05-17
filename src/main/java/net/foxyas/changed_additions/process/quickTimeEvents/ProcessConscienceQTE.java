package net.foxyas.changed_additions.process.quickTimeEvents;

import net.foxyas.changed_additions.init.ChangedAdditionsGameRules;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ProcessConscienceQTE {

    @SubscribeEvent
    public static void StartQTEWhenTransfured(ProcessTransfur.KeepConsciousEvent event){
        Player player = event.player;
        Level world = player.getLevel();
        boolean keepConscious = event.keepConscious;
        if (!keepConscious && !world.isClientSide() && world.getGameRules().getBoolean(ChangedAdditionsGameRules.CHANGED_ADDITIONS_FIGHT_TO_KEEP_CONSCIENCE)) {
            event.shouldKeepConscious = true;
            ConscienceQTEManager.addQTE(player,new ConscienceQuickTimeEvent(player,player.getRandom(),player.getRandom().nextInt(100,120)));
        }
    }
}
