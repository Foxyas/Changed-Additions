package net.foxyas.changed_additions.network;

import net.ltxprogrammer.changed.entity.TransfurMode;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class TurnOffTransfurMessage {
	int type, pressedms;

	public TurnOffTransfurMessage(int type, int pressedms) {
		this.type = type;
		this.pressedms = pressedms;
	}

	public static void buffer(TurnOffTransfurMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.type);
		buffer.writeInt(message.pressedms);
	}

	public static TurnOffTransfurMessage decode(FriendlyByteBuf buffer) {
		int type, pressdms;
		type = buffer.readInt();
		pressdms = buffer.readInt();

		return new TurnOffTransfurMessage(type,pressdms);
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
        Level world = entity.level();
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