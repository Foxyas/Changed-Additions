package net.foxyas.changed_additions.process.quickTimeEvents.clientSide;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.foxyas.changed_additions.configuration.ChangedAdditionsClientConfigs;
import net.foxyas.changed_additions.process.quickTimeEvents.InputKey;
import net.foxyas.changed_additions.process.quickTimeEvents.InputKeyTracker;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QTEManager;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QuickTimeEvent;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QuickTimeEventSequenceType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
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

                if (ChangedAdditionsClientConfigs.QTE_SHOW_INFO.get() && key == keys.get(0)) {
                    float progress = qte.getProgress();
                    int ticksLeft = qte.getTicksRemaining();
                    int textX = x + 10;
                    int baseY = y - 30;

                    int lineSpacing = 10;
                    int line = 1;

                    if (ChangedAdditionsClientConfigs.QTE_SHOW_TICKS_LEFT.get()) {
                        String ticksText = "Ticks Left: " + ticksLeft;
                        GuiComponent.drawCenteredString(stack, mc.font, ticksText, textX, baseY + (lineSpacing * line++), getTickColor(ticksLeft));
                    }

                    if (ChangedAdditionsClientConfigs.QTE_SHOW_PROGRESS.get()) {
                        String progressText = String.format("Progress: %.0f%%", progress * 100f);
                        GuiComponent.drawCenteredString(stack, mc.font, progressText, textX, baseY + (lineSpacing * line), getProgressColor(progress));
                    }
                    //mc.font.draw(stack, progressText, textX, textY, 0xFFFFFF);
                }

            }
        }
    }

    private static int getProgressColor(float progress) {
        if (progress <= 1.0f) {
            // Interpola de vermelho (0xFF0000) → verde (0x00FF00)
            return interpolateColor(0xFF0000, 0x00FF00, progress);
        } else {
            float overProgress = Math.min((progress - 1.0f) / 0.5f, 1.0f); // 1.0 → 1.5
            // Interpola de verde (0x00FF00) → dourado (0xFFD700)
            return interpolateColor(0x00FF00, 0xEFBF04, overProgress);
        }
    }

    private static int interpolateColor(int fromColor, int toColor, float factor) {
        factor = Mth.clamp(factor, 0.0f, 1.0f);

        int r1 = (fromColor >> 16) & 0xFF;
        int g1 = (fromColor >> 8) & 0xFF;
        int b1 = fromColor & 0xFF;

        int r2 = (toColor >> 16) & 0xFF;
        int g2 = (toColor >> 8) & 0xFF;
        int b2 = toColor & 0xFF;

        int r = (int)(r1 + (r2 - r1) * factor);
        int g = (int)(g1 + (g2 - g1) * factor);
        int b = (int)(b1 + (b2 - b1) * factor);

        return (r << 16) | (g << 8) | b;
    }

    private static int getTickColor(int ticksRemaining) {
        float factor = 1.0f - Mth.clamp(ticksRemaining / 160.0f, 0.0f, 1.0f);
        return interpolateColor(0xFFFFFF, 0xFF0000, factor); // branco → vermelho
    }

}
