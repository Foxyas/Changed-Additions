package net.foxyas.changed_additions.client.renderer;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.client.models.LatexKitsuneFemaleModel;
import net.foxyas.changed_additions.entities.LatexKitsuneFemaleEntity;
import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.*;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLatexFemaleWolfModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LatexKitsuneFemaleRenderer extends AdvancedHumanoidRenderer<LatexKitsuneFemaleEntity, LatexKitsuneFemaleModel, ArmorLatexFemaleWolfModel<LatexKitsuneFemaleEntity>> {
    public LatexKitsuneFemaleRenderer(EntityRendererProvider.Context context) {
        super(context, new LatexKitsuneFemaleModel(context.bakeLayer(LatexKitsuneFemaleModel.LAYER_LOCATION)), ArmorLatexFemaleWolfModel.MODEL_SET, 0.5f);
        this.addLayer(new LatexParticlesLayer<>(this, getModel()));
        this.addLayer(new CustomEyesLayer<>(this, context.getModelSet(), CustomEyesLayer::scleraColor, CustomEyesLayer::glowingIrisColorLeft, CustomEyesLayer::glowingIrisColorRight, CustomEyesLayer::noRender, CustomEyesLayer::noRender));
        this.addLayer(TransfurCapeLayer.normalCape(this, context.getModelSet()));
        this.addLayer(new GasMaskLayer<>(this, context.getModelSet()));
        this.addLayer(new EmissiveBodyLayer<>(this, ChangedAdditionsMod.modResource("textures/entities/latex_kitsune_female/latex_kitsune_female_stripes.png")){
            @Override
            public @NotNull RenderType renderType() {
                return RenderType.energySwirl(getEmissiveTexture(), 0, 0);
            }
        });
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LatexKitsuneFemaleEntity entity) {
        return ChangedAdditionsMod.modResource("textures/entities/latex_kitsune_female/latex_kitsune_female.png");
    }
}