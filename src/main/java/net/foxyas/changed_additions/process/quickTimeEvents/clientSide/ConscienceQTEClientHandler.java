package net.foxyas.changed_additions.process.quickTimeEvents.clientSide;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.foxyas.changed_additions.process.quickTimeEvents.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ConscienceQTEClientHandler {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        ConscienceQuickTimeEvent qte = ConscienceQTEManager.getAutoActiveQTE(player);
        if (qte == null || qte.isFinished()) return;

        PoseStack stack = event.getMatrixStack();
        int sw = event.getWindow().getGuiScaledWidth();
        int sh = event.getWindow().getGuiScaledHeight();

        var keys = qte.getSequence();

        for (InputKey key : keys) {
            { // Verifica se deve usar a imagem de "pressionada"
                boolean isPressed = InputKeyTracker.isHeld(key);
                ConscienceQuickTimeEventType qteType = ConscienceQuickTimeEventType.getFromSequence(qte.getSequence());
                //String type = qteType != null ? qteType.name() : "";

                assert qteType != null;
                int ImageSizeX,ImageSizeY,KeySizeX,KeySizeY;
                ImageSizeX = qteType.getImageDimensions().getFirst();
                ImageSizeY = qteType.getImageDimensions().getSecond();
                KeySizeX = qteType.getKeyTypeSize().getFirst();
                KeySizeY = qteType.getKeyTypeSize().getSecond();
                ResourceLocation texture = qteType.getKeyTexture();
                RenderSystem.setShaderTexture(0, texture);

                //int size = 16;
                int x = (sw / 2) + key.guiX;
                int y = sh - key.guiY;

                int u = key.u;
                int v = key.v;

                if (isPressed) {
                    GuiComponent.blit(stack, x, y, u, v + 16, KeySizeX, KeySizeY, ImageSizeX, ImageSizeY);
                } else {
                    GuiComponent.blit(stack, x, y, u, v , KeySizeX, KeySizeY, ImageSizeX, ImageSizeY);
                }

            }
        }
    }
}
