package net.foxyas.changed_additions.process.quickTimeEvents;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class QTEClientHandler {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        QuickTimeEvent qte = QTEManager.getActiveQTE(player);
        if (qte == null || qte.isFinished()) return;

        PoseStack stack = event.getMatrixStack();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        var keys = qte.getSequence();

        for (InputKey key : keys) {
            { // Verifica se deve usar a imagem de "pressionada"
                boolean isPressed = InputKeyTracker.isHeld(key);
                QuickTimeEventType qteType = QuickTimeEventType.getFromSequence(qte.getSequence());
                String type = qteType != null ? qteType.name() : "";


                assert qteType != null;
                ResourceLocation texture = qteType.getKeyTexture();
                RenderSystem.setShaderTexture(0, texture);

                int size = 16;
                int x = key.guiX;
                int y = key.guiY;

                int u = key.u;
                int v = key.v;

                if (isPressed) {
                    v += 16; // suponha que linha de baixo da spritesheet seja versão "pressed"
                }

                GuiComponent.blit(stack, x, y, u, v, size, size, 64, 64);
            }
        }
    }
}
