package net.foxyas.changed_additions.client.renderer.item;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.item.LaserPointer;
import net.foxyas.changed_additions.item.armor.TShirtClothing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class StyleItemPredicate {

    public static void DynamicStyleRender(RegistryObject<Item> item, String name) {
        ItemProperties.register(item.get(), ChangedAdditionsMod.modResource(name),
                (itemStackToRender, clientWorld, entity, itemEntityId) -> (float) TShirtClothing.getShirtType(itemStackToRender).ordinal());

    }


}
