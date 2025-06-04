package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.client.models.FengQIFoxModel;
import net.foxyas.changed_additions.client.models.LatexSnowFoxFemaleModel;
import net.foxyas.changed_additions.client.models.LatexSnowFoxMaleModel;
import net.foxyas.changed_additions.client.models.accessories.models.*;
import net.foxyas.changed_additions.client.models.armors.DarkLatexCoatModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ChangedAdditionsModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FengQIFoxModel.LAYER_LOCATION, FengQIFoxModel::createBodyLayer);
        event.registerLayerDefinition(LatexSnowFoxMaleModel.LAYER_LOCATION, LatexSnowFoxMaleModel::createBodyLayer);
        event.registerLayerDefinition(LatexSnowFoxFemaleModel.LAYER_LOCATION, LatexSnowFoxFemaleModel::createBodyLayer);

        //Accessories
        event.registerLayerDefinition(AccessoriesFemaleWolf.LAYER_LOCATION, AccessoriesFemaleWolf::createBodyLayer);
        event.registerLayerDefinition(AccessoriesMaleWolf.LAYER_LOCATION, AccessoriesMaleWolf::createBodyLayer);
        event.registerLayerDefinition(AccessoriesStiger.LAYER_LOCATION, AccessoriesStiger::createBodyLayer);
        event.registerLayerDefinition(AccessoriesMaleFox.LAYER_LOCATION, AccessoriesMaleFox::createBodyLayer);
        event.registerLayerDefinition(AccessoriesFemaleWolf.LAYER_LOCATION, AccessoriesFemaleFox::createBodyLayer);


        //Armors Models
        event.registerLayerDefinition(DarkLatexCoatModel.LAYER_LOCATION, DarkLatexCoatModel::createBodyLayer);
    }
}