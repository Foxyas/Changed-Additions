package net.foxyas.changed_additions.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.foxyas.changed_additions.client.renderer.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ChangedAdditionsEntityRenderers {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ChangedAdditionsEntities.FENG_QI_FOX.get(), FengQIFoxRenderer::new);
        event.registerEntityRenderer(ChangedAdditionsEntities.LATEX_SNOW_FOX_MALE.get(), LatexSnowFoxMaleRenderer::new);
        event.registerEntityRenderer(ChangedAdditionsEntities.LATEX_SNOW_FOX_FEMALE.get(), LatexSnowFoxFemaleRenderer::new);
    }
}