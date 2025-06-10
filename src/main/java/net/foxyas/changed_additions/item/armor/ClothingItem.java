package net.foxyas.changed_additions.item.armor;

import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.item.Clothing;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Wearable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ClothingItem extends Wearable, Clothing {

    @Nullable
    ResourceLocation getClothTexture(ChangedEntity entity, EquipmentSlot slot, ItemStack itemBySlot);

    @Nullable Color3 getClothColor(ChangedEntity entity, EquipmentSlot slot, ItemStack itemBySlot);

    float getClothAlpha(@NotNull ChangedEntity entity, EquipmentSlot slot, ItemStack itemBySlot);
}
