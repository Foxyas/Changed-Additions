package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.client.models.LatexKitsuneFemaleModel;
import net.foxyas.changed_additions.client.models.LatexKitsuneMaleModel;
import net.foxyas.changed_additions.client.renderer.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ChangedAdditionsEntityRenderers {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ChangedAdditionsEntities.FENG_QI_WOLF.get(), FengQIWolfRenderer::new);
        event.registerEntityRenderer(ChangedAdditionsEntities.LATEX_SNOW_FOX_MALE.get(), LatexSnowFoxMaleRenderer::new);
        event.registerEntityRenderer(ChangedAdditionsEntities.LATEX_SNOW_FOX_FEMALE.get(), LatexSnowFoxFemaleRenderer::new);
        event.registerEntityRenderer(ChangedAdditionsEntities.LATEX_KITSUNE_MALE.get(), LatexKitsuneMaleRenderer::new);
        event.registerEntityRenderer(ChangedAdditionsEntities.LATEX_KITSUNE_FEMALE.get(), LatexKitsuneFemaleRenderer::new);

    }
}