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
public class ConscienceQTEInputHandle {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (event.getAction() == GLFW.GLFW_PRESS || event.getAction() == GLFW.GLFW_REPEAT) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.screen == null) {
                if (ConscienceQTEManager.getActiveQTE(Minecraft.getInstance().player) != null && InputKey.fromKeyCode(event.getKey()).isPresent()) {
                    ChangedAdditionsMod.PACKET_HANDLER.sendToServer(new QTEKeyInputPacket(event.getKey(), event.getAction()));
                    // Cancela movimentação
                    mc.options.keyUp.setDown(false);
                    mc.options.keyDown.setDown(false);
                    mc.options.keyLeft.setDown(false);
                    mc.options.keyRight.setDown(false);
                    mc.options.keyJump.setDown(false);
                    mc.options.keySprint.setDown(false);
                    mc.options.keyShift.setDown(false);
                }
            }
        }
    }

}
