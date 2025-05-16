package net.foxyas.changed_additions.process.quickTimeEvents;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.network.packets.QTEKeyInputPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class QTEInputHandle {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (event.getAction() == GLFW.GLFW_PRESS || event.getAction() == GLFW.GLFW_REPEAT) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().screen == null) {
                ChangedAdditionsMod.PACKET_HANDLER.sendToServer(new QTEKeyInputPacket(event.getKey(),event.getAction()));
            }
        }
    }

}
