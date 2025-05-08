package net.foxyas.changed_additions.registers;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.network.packets.SyncTransfurVisionsPacket;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = ChangedAdditionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public class ChangedAddonPacketsRegistry {
		@SubscribeEvent
		public static void registerPackets(FMLConstructModEvent event) {
			ChangedAdditionsMod.addNetworkMessage(SyncTransfurVisionsPacket.class, SyncTransfurVisionsPacket::encode, SyncTransfurVisionsPacket::decode, SyncTransfurVisionsPacket::handle);
		}
	}