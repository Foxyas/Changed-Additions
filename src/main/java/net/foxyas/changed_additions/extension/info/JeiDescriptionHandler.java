package net.foxyas.changed_additions.extension.info;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.foxyas.changed_additions.init.ChangedAdditionsModItems;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.List;
import java.util.Map;

public class JeiDescriptionHandler {
    public static void registerDescriptions(IRecipeRegistration registration) {
        // Item Information
        registration.addIngredientInfo(new ItemStack(ChangedAdditionsModItems.DYEABLE_SHORTS.get()), VanillaTypes.ITEM_STACK, new TranslatableComponent("changed_addon.jei_descriptions.dyeable_shorts"));
        registration.addIngredientInfo(new ItemStack(ChangedAdditionsModItems.LASER_POINTER.get()), VanillaTypes.ITEM_STACK, new TranslatableComponent("changed_addon.jei_descriptions.laser_pointer"));
    }

    private static void addSharedDescriptions(IRecipeRegistration registration, List<Item> items, String translationKey) {
        items.forEach(item ->
                registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM_STACK, new TranslatableComponent(translationKey))
        );
    }
}