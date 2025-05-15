
package net.foxyas.changed_additions.client.renderer;

import net.foxyas.changed_additions.client.models.LatexSnowFoxMaleModel;
import net.foxyas.changed_additions.entities.LatexSnowFoxMale;
import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.CustomEyesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.GasMaskLayer;
import net.ltxprogrammer.changed.client.renderer.layers.TransfurCapeLayer;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLatexMaleWolfModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LatexSnowFoxMaleRenderer extends AdvancedHumanoidRenderer<LatexSnowFoxMale, LatexSnowFoxMaleModel, ArmorLatexMaleWolfModel<LatexSnowFoxMale>> {
	public LatexSnowFoxMaleRenderer(EntityRendererProvider.Context context) {
		super(context, new LatexSnowFoxMaleModel(context.bakeLayer(LatexSnowFoxMaleModel.LAYER_LOCATION)), ArmorLatexMaleWolfModel::new, ArmorLatexMaleWolfModel.INNER_ARMOR, ArmorLatexMaleWolfModel.OUTER_ARMOR, 0.5F);
		//this.addLayer(new LatexParticlesLayer<>(this, this.getModel()));
		this.addLayer(TransfurCapeLayer.normalCape(this, context.getModelSet()));
		this.addLayer(new CustomEyesLayer<>(this, context.getModelSet()));
		this.addLayer(GasMaskLayer.forSnouted(this, context.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(LatexSnowFoxMale entity) {
		return new ResourceLocation("changed_additions:textures/entities/latex_snow_foxes/latex_snow_fox_male.png");
	}
}
