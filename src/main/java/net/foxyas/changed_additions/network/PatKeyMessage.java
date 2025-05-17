package net.foxyas.changed_additions.network;

import net.foxyas.changed_additions.advancements.critereon.PatFeatureHandle;
import net.foxyas.changed_additions.init.ChangedAdditionsKeyMappings;
import net.ltxprogrammer.changed.entity.TransfurMode;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class PatKeyMessage {
    int type, pressedms;

    public PatKeyMessage(int type, int pressedms) {
        this.type = type;
        this.pressedms = pressedms;
    }

    public PatKeyMessage(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
        this.pressedms = buffer.readInt();
    }

    public static void buffer(PatKeyMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
        buffer.writeInt(message.pressedms);
    }

    public static void handler(PatKeyMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
            run(entity, world);
        }
    }

    private static void run(Player player, Level world) {
        PatFeatureHandle.execute(world, player);
    }
}