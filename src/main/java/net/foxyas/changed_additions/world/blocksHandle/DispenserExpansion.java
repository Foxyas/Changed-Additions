package net.foxyas.changed_additions.world.blocksHandle;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = ChangedAdditionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DispenserExpansion {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(BoneMealExpansion.BoneMealDispenserHandler::registerDispenserBehavior);
        event.enqueueWork(BoneMealExpansion.GooApplyDispenserHandler::registerDispenserBehavior);
    }
}