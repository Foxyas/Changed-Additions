package net.foxyas.changed_additions.network.packets.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketsUtils {
    public static void sendToPlayer(SimpleChannel simpleChannel, Object message, ServerPlayer player) {
        simpleChannel.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
