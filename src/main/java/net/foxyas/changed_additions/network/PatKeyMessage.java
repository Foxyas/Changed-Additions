package net.foxyas.changed_additions.network;

import net.foxyas.changed_additions.process.ProcessPatFeature;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class PatKeyMessage {
	int type, pressedms;

	public PatKeyMessage(int type, int pressedms) {
		this.type = type;
		this.pressedms = pressedms;
	}

	public static void buffer(PatKeyMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.type);
		buffer.writeInt(message.pressedms);
	}

	public static PatKeyMessage decode(FriendlyByteBuf buffer) {
		int type = buffer.readInt();
		int pressdms = buffer.readInt();

		return new PatKeyMessage(type,pressdms);
	}

	public static void handler(PatKeyMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> pressAction(context.getSender(), message.type, message.pressedms));
		context.setPacketHandled(true);
	}

	public static void pressAction(@Nullable Player entity, int type, int pressedms) {
		Level world = entity.level;
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(entity.blockPosition()))
			return;
		if (type == 0) {
			ProcessPatFeature.ProcessPat(world,x,y,z,entity);
		}
	}
}