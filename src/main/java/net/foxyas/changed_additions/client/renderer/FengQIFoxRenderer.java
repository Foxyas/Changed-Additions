
package net.foxyas.changed_additions.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.foxyas.changed_additions.client.models.FengQIFoxModel;
import net.foxyas.changed_additions.entities.FengQIFox;
import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.CustomEyesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.GasMaskLayer;
import net.ltxprogrammer.changed.client.renderer.layers.TransfurCapeLayer;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLatexMaleWolfModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FengQIFoxRenderer extends AdvancedHumanoidRenderer<FengQIFox, FengQIFoxModel, ArmorLatexMaleWolfModel<FengQIFox>> {
	public FengQIFoxRenderer(EntityRendererProvider.Context context) {
		super(context, new FengQIFoxModel(context.bakeLayer(FengQIFoxModel.LAYER_LOCATION)), ArmorLatexMaleWolfModel::new, ArmorLatexMaleWolfModel.INNER_ARMOR, ArmorLatexMaleWolfModel.OUTER_ARMOR, 0.5F);
		//this.addLayer(new LatexParticlesLayer<>(this, this.getModel()));
		this.addLayer(TransfurCapeLayer.normalCape(this, context.getModelSet()));
		this.addLayer(new CustomEyesLayer<>(this, context.getModelSet()));
		this.addLayer(GasMaskLayer.forSnouted(this, context.getModelSet()));
	}

	@Override
	public void render(FengQIFox entity, float yRot, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		super.render(entity, yRot, partialTicks, poseStack, bufferSource, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(FengQIFox entity) {
		return new ResourceLocation("changed_additions:textures/entities/feng_qi_fox.png");
	}
}
