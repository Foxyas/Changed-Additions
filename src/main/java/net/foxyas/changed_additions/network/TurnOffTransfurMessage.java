package net.foxyas.changed_additions.network;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.ltxprogrammer.changed.entity.TransfurMode;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class TurnOffTransfurMessage {
	int type, pressedms;

	public TurnOffTransfurMessage(int type, int pressedms) {
		this.type = type;
		this.pressedms = pressedms;
	}

	public TurnOffTransfurMessage(FriendlyByteBuf buffer) {
		this.type = buffer.readInt();
		this.pressedms = buffer.readInt();
	}

	public static void buffer(TurnOffTransfurMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.type);
		buffer.writeInt(message.pressedms);
	}

	public static void handler(TurnOffTransfurMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			pressAction(context.getSender(), message.type, message.pressedms);
		});
		context.setPacketHandled(true);
	}

	public static void pressAction(@Nullable Player entity, int type, int pressedms) {
        assert entity != null;
        Level world = entity.level;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(entity.blockPosition()))
			return;
		if (type == 0) {
			run(entity);
		}
	}

	private static void run(Player player){
		var variant = ProcessTransfur.getPlayerTransfurVariant(player);
		if (variant != null && variant.getParent().transfurMode != TransfurMode.NONE) {
			variant.transfurMode = variant.transfurMode == TransfurMode.NONE ? TransfurMode.REPLICATION : TransfurMode.NONE;
		}
	}
}