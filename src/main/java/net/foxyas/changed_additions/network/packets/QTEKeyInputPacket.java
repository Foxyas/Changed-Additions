package net.foxyas.changed_additions.network.packets;

import net.foxyas.changed_additions.process.quickTimeEvents.InputKey;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.ConscienceQTEManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class QTEKeyInputPacket {

    private final int keyValue;
    private final int Action;

    public QTEKeyInputPacket(int keyValue, int Action) {
        this.keyValue = keyValue;
        this.Action = Action;
    }

    public static void encode(QTEKeyInputPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.keyValue);
        buf.writeInt(msg.Action);
    }

    public static QTEKeyInputPacket decode(FriendlyByteBuf buf) {
        return new QTEKeyInputPacket(buf.readInt(), buf.readInt());
    }

    public int getKeyCode() {
        return keyValue;
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            InputKey.fromKeyCode(keyValue).ifPresent(inputKey -> {
                if (ConscienceQTEManager.getActiveQTE(player) != null) {
                    ConscienceQTEManager.TriggerKeyUpdate(inputKey, Action);
                }
            });
        });
        context.setPacketHandled(true);
    }
}
