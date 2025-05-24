package net.foxyas.changed_additions.process.quickTimeEvents.clientSide;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.foxyas.changed_additions.process.quickTimeEvents.InputKey;
import net.foxyas.changed_additions.process.quickTimeEvents.InputKeyTracker;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QTEManager;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QuickTimeEvent;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QuickTimeEventSequenceType;
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

        QuickTimeEvent qte = QTEManager.getAutoActiveQTE(player);
        if (qte == null || qte.isFinished()) return;

        PoseStack stack = event.getMatrixStack();
        int sw = event.getWindow().getGuiScaledWidth();
        int sh = event.getWindow().getGuiScaledHeight();

        var keys = qte.getSequence();

        for (InputKey key : keys) {
            { // Verifica se deve usar a imagem de "pressionada"
                boolean isPressed = InputKeyTracker.isHeld(key);
                QuickTimeEventSequenceType qteType = QuickTimeEventSequenceType.getFromSequence(qte.getSequence());
                //String type = qteType != null ? qteType.name() : "";

                assert qteType != null;
                int ImageSizeX, ImageSizeY, KeySizeX, KeySizeY;
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

                // Verifica se é a próxima tecla esperada
                boolean isNextExpectedKey = key == qte.getCurrentExpectedKey();

                // Se for a esperada, colore de vermelho (1, 0, 0, 1)
                if (isNextExpectedKey && key != InputKey.SPACE) {
                    RenderSystem.setShaderColor(1f, 0f, 0f, 1f); // Vermelho
                } else {
                    RenderSystem.setShaderColor(1f, 1f, 1f, 1f); // Branco padrão
                }

                // Se for a esperada, colore de vermelho (1, 0, 0, 1)
                if (isNextExpectedKey && key != InputKey.SPACE && isPressed) {
                    RenderSystem.setShaderColor(0f, 1f, 0f, 1f); // Verde
                }

                if (isPressed) {
                    GuiComponent.blit(stack, x, y, u, v + KeySizeY, KeySizeX, KeySizeY, ImageSizeX, ImageSizeY);
                } else {
                    GuiComponent.blit(stack, x, y, u, v, KeySizeX, KeySizeY, ImageSizeX, ImageSizeY);
                }

            }
        }
    }
}
