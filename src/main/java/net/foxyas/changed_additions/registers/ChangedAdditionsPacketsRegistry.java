package net.foxyas.changed_additions.registers;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.network.PatKeyMessage;
import net.foxyas.changed_additions.network.TurnOffTransfurMessage;
import net.foxyas.changed_additions.network.packets.QTEKeyInputPacket;
import net.foxyas.changed_additions.network.packets.SyncTransfurVisionsPacket;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = ChangedAdditionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsPacketsRegistry {
    @SubscribeEvent
    public static void registerPackets(FMLConstructModEvent event) {

        //Packets
        ChangedAdditionsMod.addNetworkMessage(SyncTransfurVisionsPacket.class, SyncTransfurVisionsPacket::encode, SyncTransfurVisionsPacket::decode, SyncTransfurVisionsPacket::handle);
        ChangedAdditionsMod.addNetworkMessage(QTEKeyInputPacket.class, QTEKeyInputPacket::encode, QTEKeyInputPacket::decode, QTEKeyInputPacket::handle);
        //ChangedAdditionsMod.addNetworkMessage(QTESyncToClientPacket.class, QTESyncToClientPacket::encode, QTESyncToClientPacket::decode, QTESyncToClientPacket::handle);


        // Mensages
        ChangedAdditionsMod.addNetworkMessage(TurnOffTransfurMessage.class, TurnOffTransfurMessage::buffer, TurnOffTransfurMessage::new, TurnOffTransfurMessage::handler);
        ChangedAdditionsMod.addNetworkMessage(PatKeyMessage.class, PatKeyMessage::buffer, PatKeyMessage::new, PatKeyMessage::handler);
    }
}