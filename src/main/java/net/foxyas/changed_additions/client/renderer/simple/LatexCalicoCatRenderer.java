package net.foxyas.changed_additions.client.renderer.simple;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.client.models.simple.LatexCalicoCatModel;
import net.foxyas.changed_additions.entities.simple.LatexCalicoCatEntity;
import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.CustomEyesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.GasMaskLayer;
import net.ltxprogrammer.changed.client.renderer.layers.LatexParticlesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.TransfurCapeLayer;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLatexMaleCatModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LatexCalicoCatRenderer extends AdvancedHumanoidRenderer<LatexCalicoCatEntity, LatexCalicoCatModel, ArmorLatexMaleCatModel<LatexCalicoCatEntity>> {
    public LatexCalicoCatRenderer(EntityRendererProvider.Context context) {
        super(context, new LatexCalicoCatModel(context.bakeLayer(LatexCalicoCatModel.LAYER_LOCATION)), ArmorLatexMaleCatModel.MODEL_SET, 0.5F);
        this.addLayer(new LatexParticlesLayer<>(this, this.getModel()));
        this.addLayer(TransfurCapeLayer.normalCape(this, context.getModelSet()));
        this.addLayer(new CustomEyesLayer<>(this, context.getModelSet(), CustomEyesLayer::scleraColor, CustomEyesLayer::glowingIrisColorLeft, CustomEyesLayer::glowingIrisColorRight, CustomEyesLayer::noRender, CustomEyesLayer::noRender));
        this.addLayer(GasMaskLayer.forSnouted(this, context.getModelSet()));
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull LatexCalicoCatEntity p_114482_) {
        return ChangedAdditionsMod.modResource("textures/entities/latex_calico_cat/latex_calico_cat.png");
    }
}
