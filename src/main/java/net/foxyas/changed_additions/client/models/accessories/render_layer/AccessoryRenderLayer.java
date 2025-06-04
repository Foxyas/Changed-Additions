package net.foxyas.changed_additions.client.models.accessories.render_layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.foxyas.changed_additions.client.models.accessories.IAccessoryItem;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.Gender;
import net.ltxprogrammer.changed.entity.GenderedEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

public class AccessoryRenderLayer<E extends ChangedEntity, M extends AdvancedHumanoidModel<E>> extends RenderLayer<E, M> {

    private final EntityRendererProvider.Context context;

    public AccessoryRenderLayer(RenderLayerParent<E, M> p_117346_, AdvancedHumanoidModel<E> AccessoryModel) {
        super(p_117346_);
        accessoryModel = AccessoryModel;
        context = null;
    }

    public AccessoryRenderLayer(RenderLayerParent<E, M> p_117346_, EntityRendererProvider.Context context) {
        super(p_117346_);
        this.context = context;
    }

    private AdvancedHumanoidModel<E> accessoryModel = null;

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       E entity, float limbSwing, float limbSwingAmount, float partialTicks,
                       float ageInTicks, float netHeadYaw, float headPitch) {

        if (entity.isInvisible()) return;

        Gender gender = Gender.MALE;
        if (entity instanceof GenderedEntity genderedEntity) {
            gender = genderedEntity.getGender();
        }

        M parentModel = this.getParentModel();
        parentModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (accessoryModel == null && context != null) {
                accessoryModel = getAccessoryModel(entity, slot, gender, context);
            }

            ResourceLocation accessoryTexture = getAccessoryTexture(entity, slot, gender, context);

            if (accessoryModel == null || accessoryTexture == null)
                continue;

            poseStack.pushPose();

            parentModel.copyPropertiesTo(accessoryModel);

            accessoryModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            accessoryModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(accessoryTexture));
            Color color = getAccessoryColor(entity, slot);
            if (color != null) {
                accessoryModel.renderToBuffer(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            } else {
                accessoryModel.renderToBuffer(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity, 0.0f), 1.0F, 1.0F, 1.0F, 1.0F);
            }

            poseStack.popPose();
        }
    }

    private Color getAccessoryColor(E entity, EquipmentSlot slot) {
        if (entity.getItemBySlot(slot).getItem() instanceof IAccessoryItem accessoryItem) {
            return accessoryItem.getRenderColor(entity, slot);
        }
        return null;
    }

    private ResourceLocation getAccessoryTexture(E entity, EquipmentSlot slot, @Nullable Gender gender, EntityRendererProvider.Context context) {
        if (entity.getItemBySlot(slot).getItem() instanceof IAccessoryItem accessoryItem) {
            return accessoryItem.getAccessoryTexture(entity, slot, gender, context);
        }
        return null;
    }

    public AdvancedHumanoidModel<E> getAccessoryModel(E entity, EquipmentSlot slot, Gender gender, EntityRendererProvider.Context context) {
        if (entity.getItemBySlot(slot).getItem() instanceof IAccessoryItem accessoryItem) {
            return accessoryItem.getAccessoryModel(entity, slot, gender, context);
        }
        return null;
    }
}
