package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.configuration.ChangedAdditionsClientConfigs;
import net.foxyas.changed_additions.configuration.ChangedAdditionsCommonConfigs;
import net.foxyas.changed_additions.configuration.ChangedAdditionsServerConfigs;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = ChangedAdditionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsConfigs {
	@SubscribeEvent
	@SuppressWarnings("removal")
	public static void register(FMLConstructModEvent event) {
		event.enqueueWork(() -> {
			ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ChangedAdditionsServerConfigs.SPEC, "changed_additions-server.toml");
			ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ChangedAdditionsClientConfigs.SPEC, "changed_additions-client.toml");
			ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ChangedAdditionsCommonConfigs.SPEC, "changed_additions-common.toml");
		});
	}
}
