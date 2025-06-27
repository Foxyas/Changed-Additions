package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.client.models.FengQIWolfModel;
import net.foxyas.changed_additions.client.models.LatexSnowFoxFemaleModel;
import net.foxyas.changed_additions.client.models.LatexSnowFoxMaleModel;
import net.foxyas.changed_additions.client.models.armors.DarkLatexCoatModel;
import net.foxyas.changed_additions.client.models.armors.SkinLayerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ChangedAdditionsModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FengQIWolfModel.LAYER_LOCATION, FengQIWolfModel::createBodyLayer);
        event.registerLayerDefinition(LatexSnowFoxMaleModel.LAYER_LOCATION, LatexSnowFoxMaleModel::createBodyLayer);
        event.registerLayerDefinition(LatexSnowFoxFemaleModel.LAYER_LOCATION, LatexSnowFoxFemaleModel::createBodyLayer);

        //Armors Models
        event.registerLayerDefinition(DarkLatexCoatModel.LAYER_LOCATION, DarkLatexCoatModel::createBodyLayer);
        event.registerLayerDefinition(SkinLayerModel.LAYER_LOCATION, SkinLayerModel::createBodyLayer);
    }
}