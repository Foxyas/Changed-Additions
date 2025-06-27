package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.client.renderer.SimpleColorfulClothingRenderer;
import net.ltxprogrammer.changed.client.renderer.accessory.*;
import net.ltxprogrammer.changed.client.renderer.layers.AccessoryLayer;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ChangedAdditionsAccessoryRenderers {
    @SubscribeEvent
    public static void registerAccessoryRenderers(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            AccessoryLayer.registerRenderer(ChangedAdditionsItems.DYEABLE_SHORTS.get(), SimpleColorfulClothingRenderer.of(ArmorModel.CLOTHING_INNER, EquipmentSlot.LEGS));
            AccessoryLayer.registerRenderer(ChangedAdditionsItems.DYEABLE_SHIRT.get(), SimpleColorfulClothingRenderer.of(ArmorModel.CLOTHING_INNER, EquipmentSlot.CHEST));
        });
    }
}