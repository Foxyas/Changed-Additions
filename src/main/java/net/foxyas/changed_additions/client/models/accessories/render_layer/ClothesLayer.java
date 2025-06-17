package net.foxyas.changed_additions.client.models.accessories.render_layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.foxyas.changed_additions.item.armor.ClothingItem;
import net.ltxprogrammer.changed.client.FormRenderHandler;
import net.ltxprogrammer.changed.client.renderer.layers.CustomEyesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.FirstPersonLayer;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

public class ClothesLayer<E extends ChangedEntity, M extends AdvancedHumanoidModel<E>> extends RenderLayer<E, M> implements FirstPersonLayer<E> {
    private final M model;

    public ClothesLayer(RenderLayerParent<E, M> p_117346_) {
        super(p_117346_);
        this.model = this.getParentModel();
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, @NotNull E entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            ResourceLocation accessoryTexture = getClothTexture(entity, slot, entity.getItemBySlot(slot));
            if (accessoryTexture == null) continue;
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(accessoryTexture));
            Color3 color = getClothColor(entity, slot, entity.getItemBySlot(slot));
            float alpha = getClothAlpha(entity, slot, entity.getItemBySlot(slot));
            float zFightOffset = CustomEyesLayer.getZFightingOffset(entity);
            if (slot == EquipmentSlot.CHEST) {
                poseStack.pushPose();
                model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
                model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                poseStack.scale(1.0025f + zFightOffset, 1.0025f + zFightOffset, 1.0025f + zFightOffset);
                poseStack.pushPose();
                poseStack.translate(-0.003f, 0, 0);
                poseStack.scale(1.005f, 1.005f, 1.005f);
                if (color != null) {
                    model.getArm(HumanoidArm.LEFT).render(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), color.red(), color.green(), color.blue(), alpha);
                } else {
                    model.getArm(HumanoidArm.LEFT).render(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), 1.0F, 1.0F, 1.0F, 1.0F);
                }
                poseStack.popPose();
                poseStack.pushPose();
                poseStack.translate(0.003f, 0, 0);
                poseStack.scale(1.005f, 1.005f, 1.005f);
                if (color != null) {
                    model.getArm(HumanoidArm.RIGHT).render(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), color.red(), color.green(), color.blue(), alpha);
                } else {
                    model.getArm(HumanoidArm.RIGHT).render(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), 1.0F, 1.0F, 1.0F, 1.0F);
                }
                poseStack.popPose();
                List<ModelPart> modelPartStream = List.of(model.getLeg(HumanoidArm.LEFT), model.getLeg(HumanoidArm.RIGHT), model.getTorso(), model.getHead());
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
                model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
                model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                poseStack.scale(1.0025f + zFightOffset, 1.0025f + zFightOffset, 1.0025f + zFightOffset);
                if (color != null) {
                    model.renderToBuffer(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), color.red(), color.green(), color.blue(), alpha);
                } else {
                    model.renderToBuffer(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), 1.0F, 1.0F, 1.0F, 1.0F);
                }
                poseStack.popPose();
            }
        }
    }

    @Override
    public void renderFirstPersonOnArms(PoseStack stack, MultiBufferSource bufferSource, int packedLight, E entity, HumanoidArm arm, PoseStack stackCorrector, float partialTick) {
        stack.pushPose();
        stack.scale(1.0002F, 1.0002F, 1.0002F);
        EquipmentSlot slot = EquipmentSlot.CHEST;
        ResourceLocation accessoryTexture = getClothTexture(entity, slot, entity.getItemBySlot(slot));
        if (accessoryTexture == null) {
            return;
        }
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(accessoryTexture));
        Color3 color = getClothColor(entity, slot, entity.getItemBySlot(slot));
        if (color == null) {
            color = Color3.WHITE;
        }
        float alpha = getClothAlpha(entity, slot, entity.getItemBySlot(slot));
        FormRenderHandler.renderModelPartWithTexture(this.model.getArm(arm), stackCorrector, stack, vertexConsumer, packedLight, color.red(), color.green(), color.blue(), alpha);
        stack.popPose();
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