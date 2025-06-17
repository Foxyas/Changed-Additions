package net.foxyas.changed_additions.network.packets;

import net.foxyas.changed_additions.process.variantsExtraStats.TransfurExtraStats;
import net.foxyas.changed_additions.process.variantsExtraStats.TransfurExtraStatsRegistry;
import net.foxyas.changed_additions.process.variantsExtraStats.visions.ClientTransfurVisionRegistry;
import net.foxyas.changed_additions.process.variantsExtraStats.visions.TransfurVariantVision;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SyncTransfursStatsPacket {
    private final List<TransfurExtraStats> stats;

    public SyncTransfursStatsPacket(List<TransfurExtraStats> visions) {
        this.stats = visions;
    }

    public static void encode(SyncTransfursStatsPacket msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.stats.size());
        for (TransfurExtraStats v : msg.stats) {
            v.WriteToBuffer(buf);
        }
    }

    public static SyncTransfursStatsPacket decode(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        List<TransfurExtraStats> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new TransfurExtraStats(buf));
        }
        return new SyncTransfursStatsPacket(list);
    }

    public static void handle(SyncTransfursStatsPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            TransfurExtraStatsRegistry.CLIENT.clear();
            for (TransfurExtraStats v : msg.stats) {
                TransfurExtraStatsRegistry.CLIENT.register(v); // igual ao servidor, mas do lado do cliente
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
