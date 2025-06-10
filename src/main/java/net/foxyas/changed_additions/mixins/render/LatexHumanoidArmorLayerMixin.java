package net.foxyas.changed_additions.mixins.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.foxyas.changed_additions.item.armor.ClothingItem;
import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.LatexHumanoidArmorLayer;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.client.renderer.model.armor.LatexHumanoidArmorModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LatexHumanoidArmorLayer.class, remap = false)
public abstract class LatexHumanoidArmorLayerMixin<T extends ChangedEntity, M extends AdvancedHumanoidModel<T>, A extends LatexHumanoidArmorModel<T, ?>> {

    @Shadow
    protected abstract void renderModel(ChangedEntity entity, ItemStack stack, EquipmentSlot slot, PoseStack pose, MultiBufferSource buffers, int packedLight, boolean foil, LatexHumanoidArmorModel<T, ?> model, float red, float green, float blue, ResourceLocation armorResource);

    @Shadow
    @Final
    AdvancedHumanoidRenderer<T, M, A> parent;

    @Inject(
            method = "renderModel(Lnet/ltxprogrammer/changed/entity/ChangedEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IZLnet/ltxprogrammer/changed/client/renderer/model/armor/LatexHumanoidArmorModel;FFFLnet/minecraft/resources/ResourceLocation;)V",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void Scale(T entity, ItemStack stack, EquipmentSlot slot, PoseStack pose, MultiBufferSource buffers, int packedLight, boolean foil, LatexHumanoidArmorModel<T, ?> model, float red, float green, float blue, ResourceLocation armorResource, CallbackInfo ci) {
        if (entity.getItemBySlot(slot).getItem() instanceof ClothingItem) {
            ci.cancel();
            if (slot == EquipmentSlot.CHEST) {
                pose.pushPose();
                model.prepareVisibility(slot, stack);
                pose.translate(0.1f, 0.0f, -0.01f);
                pose.scale(0.75f, 1.0f, 0.75f);
                model.getArm(HumanoidArm.LEFT).render(pose, ItemRenderer.getArmorFoilBuffer(buffers, RenderType.armorCutoutNoCull(armorResource), false, foil), packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1f);
                model.prepareVisibility(slot, stack);
                pose.popPose(); // ← use outro mixin com AFTER se necessário

                pose.pushPose();
                pose.translate(-0.1f, 0.0f, -0.01f);
                pose.scale(0.75f, 1.0f, 0.75f);
                model.prepareVisibility(slot, stack);
                model.getArm(HumanoidArm.RIGHT).render(pose, ItemRenderer.getArmorFoilBuffer(buffers, RenderType.armorCutoutNoCull(armorResource), false, foil), packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1f);
                model.prepareVisibility(slot, stack);
                pose.popPose(); // ← use outro mixin com AFTER se necessário

                pose.pushPose();
                pose.scale(0.825f, 0.825f, 0.825f);
                pose.translate(0.0, 0.025, 0.0);
                model.prepareVisibility(slot, stack);
                model.getTorso().render(pose, ItemRenderer.getArmorFoilBuffer(buffers, RenderType.armorCutoutNoCull(armorResource), false, foil), packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1f);
                model.prepareVisibility(slot, stack);
                pose.popPose(); // ← use outro mixin com AFTER se necessário

                //model.renderForSlot(entity, parent, stack, slot, pose, ItemRenderer.getArmorFoilBuffer(buffers, RenderType.armorCutoutNoCull(armorResource), false, foil), packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
                //model.prepareVisibility(slot, stack);
                //pose.popPose();
            } else {
                pose.pushPose();

                pose.scale(0.890f, 0.890f, 0.890f);

                model.prepareVisibility(slot, stack);
                model.renderForSlot(entity, parent, stack, slot, pose, ItemRenderer.getArmorFoilBuffer(buffers, RenderType.armorCutoutNoCull(armorResource), false, foil), packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
                model.prepareVisibility(slot, stack);

                // (deixe renderArmorPiece rodar depois)
                pose.popPose(); // ← use outro mixin com AFTER se necessário*/

            }

        }
    }
}
