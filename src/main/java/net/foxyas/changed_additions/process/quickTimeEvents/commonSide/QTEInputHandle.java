package net.foxyas.changed_additions.process.quickTimeEvents.commonSide;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.configuration.ChangedAdditionsClientConfigs;
import net.foxyas.changed_additions.network.packets.QTEKeyInputPacket;
import net.foxyas.changed_additions.process.quickTimeEvents.InputKey;
import net.ltxprogrammer.changed.init.ChangedSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.screen == null) {
                if (QTEManager.Client.getActiveQTE(mc.player) != null && InputKey.fromKeyCode(event.getKey()).isPresent()) {
                    ChangedAdditionsMod.PACKET_HANDLER.sendToServer(new QTEKeyInputPacket(event.getKey(), event.getAction()));

                    InputKey.fromKeyCode(event.getKey()).ifPresent(inputKey -> {
                        if (QTEManager.Client.getActiveQTE(mc.player) != null) {
                            mc.player.getLevel().playSound(mc.player, mc.player, ChangedAdditionsClientConfigs.USE_BLOW1_SOUND_INSTEAD_OF_CLICK.get() ? ChangedSounds.BLOW1 : SoundEvents.UI_BUTTON_CLICK, SoundSource.PLAYERS, 1f, QTEManager.Client.getActiveQTE(mc.player).getProgress());
                        }
                    });

                    // Cancela movimentação
                    QTEManager.Client.WhenSync();
                    if (QTEManager.Client.getActiveQTE(mc.player) != null) {
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

}
