package net.foxyas.changed_additions.mixins;

import com.mojang.blaze3d.shaders.Uniform;
import net.foxyas.changed_additions.process.variantsExtraStats.visions.TransfurVariantVision;
import net.foxyas.changed_additions.process.variantsExtraStats.visions.TransfurVariantVisions;
import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.List;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Unique
    private TransfurVariantVision changed_Additions$transfurVariantVision;
    @Unique
    private PostChain changed_Additions$colorblindChain;
    @Unique
    private int changed_Additions$prevWidth = -1, changed_Additions$prevHeight = -1;

    @Shadow
    public abstract void render(float p_109094_, long p_109095_, boolean p_109096_);

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER))
    private void verdant$addColorblindFilter(float partialTicks, long nanoTime, boolean renderLevel, CallbackInfo ci) {
        Player player = this.minecraft.player;
        if (player != null) {
            if (changed_Additions$ShouldWork(player)) {
                if (this.changed_Additions$colorblindChain == null) {
                    changed_Additions$loadShaders();
                }
                int w = this.minecraft.getMainRenderTarget().width;
                int h = this.minecraft.getMainRenderTarget().height;
                if (w != changed_Additions$prevWidth || h != changed_Additions$prevHeight) {
                    this.changed_Additions$colorblindChain.resize(w, h);
                    changed_Additions$prevWidth = w;
                    changed_Additions$prevHeight = h;
                }

                this.changed_Additions$colorblindChain.process(partialTicks);

            }
        }
    }

    @Unique
    private boolean changed_Additions$ShouldWork(Player player) {
        TransfurVariantInstance<?> variantInstance = ProcessTransfur.getPlayerTransfurVariant(player);
        if (variantInstance != null) {
            this.changed_Additions$transfurVariantVision = TransfurVariantVisions.getTransfurVariantVision(player.level(), variantInstance.getFormId());
            return TransfurVariantVisions.getTransfurVariantVision(player.level(), variantInstance.getFormId()) != null;
        }


        return false;
    }

    @Unique
    private void ignore(Player player) {
        if (this.changed_Additions$colorblindChain instanceof PostChainMixin postChainMixin) {
            List<PostPass> postPassList = postChainMixin.getPasses();
            if (postPassList != null && !postPassList.isEmpty()) {
                for (PostPass postPass : postPassList) {
                    Uniform uniform = postPass.getEffect().getUniform("Saturation");
                    Uniform uniform2 = postPass.getEffect().getUniform("Contrast");
                    if (uniform != null) {
                        if (player.isShiftKeyDown()) {
                            uniform.set(1);
                        } else {
                            uniform.set(2);
                        }
                    }
                    if (uniform2 != null) {
                        if (player.isShiftKeyDown()) {
                            uniform2.set(1);
                        } else {
                            uniform2.set(2);
                        }
                    }
                }
            }

        }
    }

    @Unique
    private void changed_Additions$loadShaders() {
        try {
            this.changed_Additions$colorblindChain = new PostChain(
                    this.minecraft.getTextureManager(),
                    this.minecraft.getResourceManager(),
                    this.minecraft.getMainRenderTarget(),
                    ResourceLocation.parse(changed_Additions$transfurVariantVision.getVisionEffect().toString())
            );
            this.changed_Additions$colorblindChain.resize(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}