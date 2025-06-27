
package net.foxyas.changed_additions.client.renderer;

import net.foxyas.changed_additions.client.models.LatexSnowFoxFemaleModel;
import net.foxyas.changed_additions.entities.LatexSnowFoxFemale;
import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.CustomEyesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.GasMaskLayer;
import net.ltxprogrammer.changed.client.renderer.layers.TransfurCapeLayer;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLatexFemaleWolfModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LatexSnowFoxFemaleRenderer extends AdvancedHumanoidRenderer<LatexSnowFoxFemale, LatexSnowFoxFemaleModel, ArmorLatexFemaleWolfModel<LatexSnowFoxFemale>> {
	public LatexSnowFoxFemaleRenderer(EntityRendererProvider.Context context) {
		super(context, new LatexSnowFoxFemaleModel(context.bakeLayer(LatexSnowFoxFemaleModel.LAYER_LOCATION)), ArmorLatexFemaleWolfModel.MODEL_SET, 0.5F);
		//this.addLayer(new LatexParticlesLayer<>(this, this.getModel()));
		this.addLayer(TransfurCapeLayer.normalCape(this, context.getModelSet()));
		this.addLayer(new CustomEyesLayer<>(this, context.getModelSet()));
		this.addLayer(GasMaskLayer.forSnouted(this, context.getModelSet()));
	}

	@Override
	public ResourceLocation getTextureLocation(LatexSnowFoxFemale entity) {
		return ResourceLocation.parse("changed_additions:textures/entities/latex_snow_foxes/latex_snow_fox_female.png");
	}
}
