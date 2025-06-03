package net.foxyas.changed_additions.mixins.render;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import net.foxyas.changed_additions.client.models.accessories.models.AccessoriesMaleWolf;
import net.foxyas.changed_additions.client.models.accessories.render_layer.AccessoryRenderLayer;
import net.foxyas.changed_additions.variants.ChangedAdditionsTransfurVariants;
import net.foxyas.changed_additions.variants.TransfurVariantTags;
import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.client.renderer.model.armor.LatexHumanoidArmorModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.init.ChangedTags;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.BiFunction;

@Mixin(value = AdvancedHumanoidRenderer.class, remap = false)
public abstract class MixinAdvancedHumanoidRenderer<T extends ChangedEntity, M extends AdvancedHumanoidModel<T>, A extends LatexHumanoidArmorModel<T, ?>> extends MobRenderer<T, M> {

    @Unique
    private EntityRendererProvider.Context changed_Additions$MixinContext;
    @Unique
    private ChangedEntity changed_Additions$MixinEntity;

    public MixinAdvancedHumanoidRenderer(EntityRendererProvider.Context p_174304_, M p_174305_, float p_174306_) {
        super(p_174304_, p_174305_, p_174306_);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Lnet/ltxprogrammer/changed/client/renderer/model/AdvancedHumanoidModel;Ljava/util/function/BiFunction;Lnet/minecraft/client/model/geom/ModelLayerLocation;Lnet/minecraft/client/model/geom/ModelLayerLocation;F)V", at = @At("TAIL"))
    private void injectLayer(EntityRendererProvider.Context context, AdvancedHumanoidModel main, BiFunction ctorA, ModelLayerLocation armorInner, ModelLayerLocation armorOuter, float shadowSize, CallbackInfo ci) {
        AdvancedHumanoidRenderer thisFixed = ((AdvancedHumanoidRenderer) (Object) this);
        thisFixed.addLayer(new AccessoryRenderLayer<T, M>(thisFixed, context));
        //this.changed_Additions$MixinContext = context;
        //thisFixed.addLayer(new AccessoryRenderLayer<T, M>(thisFixed, new AccessoriesMaleWolf<>(changed_Additions$MixinContext.bakeLayer(AccessoriesMaleWolf.LAYER_LOCATION))));
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "render(Lnet/ltxprogrammer/changed/entity/ChangedEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("TAIL"))
    private void injectLayer(ChangedEntity entity, float yRot, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, CallbackInfo ci) {
        //changed_Additions$MixinEntity = entity;
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "addLayers", at = @At("TAIL"))
    private void injectLayer(EntityRendererProvider.Context context, M main, CallbackInfo ci) {
        //AdvancedHumanoidRenderer thisFixed = ((AdvancedHumanoidRenderer) (Object) this);
        //thisFixed.addLayer(new AccessoryRenderLayer<T, M>(thisFixed, context));
        //if (changed_Additions$MixinEntity.getSelfVariant() != null && changed_Additions$MixinEntity.getSelfVariant().is(TransfurVariantTags.WOLF_LIKE)) {
        //    AdvancedHumanoidRenderer thisFixed = ((AdvancedHumanoidRenderer) (Object) this);
        //    thisFixed.addLayer(new AccessoryRenderLayer<T, M>(thisFixed, new AccessoriesMaleWolf<>(context.bakeLayer(AccessoriesMaleWolf.LAYER_LOCATION))));
        //}
    }
}
