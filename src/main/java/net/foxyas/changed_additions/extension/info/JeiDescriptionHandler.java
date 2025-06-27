package net.foxyas.changed_additions.extension.info;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.foxyas.changed_additions.init.ChangedAdditionsItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class JeiDescriptionHandler {
    public static void registerDescriptions(IRecipeRegistration registration) {
        // Item Information
        registration.addIngredientInfo(new ItemStack(ChangedAdditionsItems.DYEABLE_SHORTS.get()), VanillaTypes.ITEM_STACK, Component.translatable("changed_additions.jei_descriptions.dyeable_shorts"));
        registration.addIngredientInfo(new ItemStack(ChangedAdditionsItems.DYEABLE_SHIRT.get()), VanillaTypes.ITEM_STACK, Component.translatable("changed_additions.jei_descriptions.dyeable_shirt"));
        registration.addIngredientInfo(new ItemStack(ChangedAdditionsItems.LASER_POINTER.get()), VanillaTypes.ITEM_STACK, Component.translatable("changed_additions.jei_descriptions.laser_pointer"));
    }

    private static void addSharedDescriptions(IRecipeRegistration registration, List<Item> items, String translationKey) {
        items.forEach(item ->
                registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM_STACK, Component.translatable(translationKey))
        );
    }
}