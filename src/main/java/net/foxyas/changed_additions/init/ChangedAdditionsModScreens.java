
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.foxyas.changed_additions.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

import net.foxyas.changed_additions.client.gui.NeofuserGuiScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ChangedAdditionsModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(ChangedAdditionsModMenus.NEOFUSER_GUI, NeofuserGuiScreen::new);
		});
	}
}
