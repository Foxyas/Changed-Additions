package net.foxyas.changed_additions.client.renderer;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.client.models.LatexKitsuneMaleModel;
import net.foxyas.changed_additions.entities.LatexKitsuneMaleEntity;
import net.foxyas.changed_additions.init.ChangedAdditionsEntities;
import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.*;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLatexMaleWolfModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LatexKitsuneMaleRenderer extends AdvancedHumanoidRenderer<LatexKitsuneMaleEntity, LatexKitsuneMaleModel, ArmorLatexMaleWolfModel<LatexKitsuneMaleEntity>> {
    public LatexKitsuneMaleRenderer(EntityRendererProvider.Context context) {
        super(context, new LatexKitsuneMaleModel(context.bakeLayer(LatexKitsuneMaleModel.LAYER_LOCATION)), ArmorLatexMaleWolfModel::new, ArmorLatexMaleWolfModel.INNER_ARMOR, ArmorLatexMaleWolfModel.OUTER_ARMOR, 0.5f);
        this.addLayer(new LatexParticlesLayer<>(this, getModel()));
        this.addLayer(new CustomEyesLayer<>(this, context.getModelSet(), CustomEyesLayer::scleraColor, CustomEyesLayer::glowingIrisColorLeft, CustomEyesLayer::glowingIrisColorRight, CustomEyesLayer::noRender, CustomEyesLayer::noRender));
        this.addLayer(TransfurCapeLayer.normalCape(this, context.getModelSet()));
        this.addLayer(new GasMaskLayer<>(this, context.getModelSet()));
        this.addLayer(new EmissiveBodyLayer<>(this, ChangedAdditionsMod.modResource("textures/entities/latex_kitsune_male/latex_kitsune_male_stripes.png")){
            @Override
            public @NotNull RenderType renderType() {
                return RenderType.energySwirl(getEmissiveTexture(), 0, 0);
            }
        });
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LatexKitsuneMaleEntity entity) {
        return ChangedAdditionsMod.modResource("textures/entities/latex_kitsune_male/latex_kitsune_male.png");
    }
}
