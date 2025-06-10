package net.foxyas.changed_additions.client.models.accessories.render_layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.foxyas.changed_additions.item.armor.ClothingItem;
import net.ltxprogrammer.changed.client.renderer.layers.CustomEyesLayer;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

public class ClothesLayer<E extends ChangedEntity, M extends AdvancedHumanoidModel<E>> extends RenderLayer<E, M> {

    public ClothesLayer(RenderLayerParent<E, M> p_117346_) {
        super(p_117346_);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight,
                       @NotNull E entity, float limbSwing, float limbSwingAmount, float partialTicks,
                       float ageInTicks, float netHeadYaw, float headPitch) {
        M parentModel = this.getParentModel();
        for (EquipmentSlot slot : EquipmentSlot.values()) {

            parentModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            parentModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            ResourceLocation accessoryTexture = getClothTexture(entity, slot, entity.getItemBySlot(slot));

            if (accessoryTexture == null)
                continue;

            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(accessoryTexture));
            Color3 color = getClothColor(entity, slot, entity.getItemBySlot(slot));
            float alpha = getClothAlpha(entity, slot, entity.getItemBySlot(slot));
            float zFightOffset = CustomEyesLayer.getZFightingOffset(entity);

            if (slot == EquipmentSlot.CHEST) {
                poseStack.pushPose();
                parentModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
                parentModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                poseStack.scale(1.0025f + zFightOffset, 1.0025f + zFightOffset, 1.0025f + zFightOffset);

                poseStack.pushPose();
                poseStack.translate(-0.0015f, 0.0f, 0.0f);
                poseStack.scale(1.0001f, 1.0f, 1f);
                if (color != null) {
                    parentModel.getArm(HumanoidArm.LEFT).render(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), color.red(), color.green(), color.blue(), alpha);
                } else {
                    parentModel.getArm(HumanoidArm.LEFT).render(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), 1.0F, 1.0F, 1.0F, 1.0F);
                }
                poseStack.popPose();

                poseStack.pushPose();
                poseStack.translate(0.0015f, 0.0f, 0.0f);
                poseStack.scale(1.0001f, 1.0f, 1f);
                if (color != null) {
                    parentModel.getArm(HumanoidArm.RIGHT).render(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), color.red(), color.green(), color.blue(), alpha);
                } else {
                    parentModel.getArm(HumanoidArm.RIGHT).render(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), 1.0F, 1.0F, 1.0F, 1.0F);
                }
                poseStack.popPose();


                List<ModelPart> modelPartStream = List.of(parentModel.getLeg(HumanoidArm.LEFT), parentModel.getLeg(HumanoidArm.RIGHT), parentModel.getTorso(), parentModel.getHead());
                for (ModelPart part : modelPartStream) {
                    if (color != null) {
                        part.render(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), color.red(), color.green(), color.blue(), alpha);
                    } else {
                        part.render(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), 1.0F, 1.0F, 1.0F, 1.0F);
                    }
                }
                poseStack.popPose();
            } else {
                poseStack.pushPose();
                parentModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
                parentModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                poseStack.scale(1.001f, 1.001f, 1.001f);
                if (color != null) {
                    parentModel.renderToBuffer(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), color.red(), color.green(), color.blue(), alpha);
                } else {
                    parentModel.renderToBuffer(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), 1.0F, 1.0F, 1.0F, 1.0F);
                }
                poseStack.popPose();
            }
        }
    }

    private float getClothAlpha(@NotNull E entity, EquipmentSlot slot, ItemStack itemBySlot) {
        if (entity.getItemBySlot(slot).getItem() instanceof ClothingItem accessoryItem) {
            return accessoryItem.getClothAlpha(entity, slot, itemBySlot);
        }
        return 1;
    }

    private Color3 getClothColor(E entity, EquipmentSlot slot, ItemStack itemBySlot) {
        if (entity.getItemBySlot(slot).getItem() instanceof ClothingItem accessoryItem) {
            return accessoryItem.getClothColor(entity, slot, itemBySlot);
        }
        return null;
    }

    private ResourceLocation getClothTexture(E entity, EquipmentSlot slot, ItemStack itemBySlot) {
        if (entity.getItemBySlot(slot).getItem() instanceof ClothingItem accessoryItem) {
            return accessoryItem.getClothTexture(entity, slot, itemBySlot);
        }
        return null;
    }
}
