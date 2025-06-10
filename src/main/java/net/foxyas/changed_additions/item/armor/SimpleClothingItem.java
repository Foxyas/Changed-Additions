package net.foxyas.changed_additions.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SimpleClothingItem extends DyeableArmorItem implements ClothingItem {
    public SimpleClothingItem(EquipmentSlot p_41092_, Properties p_41093_) {
        super(MATERIAL, p_41092_, p_41093_);
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        return ImmutableMultimap.of();
    }

    @Override
    public @Nullable Color3 getClothColor(ChangedEntity entity, EquipmentSlot slot, ItemStack itemBySlot) {
        return Color3.fromInt(getColor(itemBySlot));
    }

    @Override
    public float getClothAlpha(@NotNull ChangedEntity entity, EquipmentSlot slot, ItemStack itemBySlot) {
        return 1;
    }

    @Override
    public @Nullable ResourceLocation getClothTexture(ChangedEntity entity, EquipmentSlot slot, ItemStack itemBySlot) {
        return null;
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (entity instanceof ChangedEntity changedEntity && this.getClothTexture(changedEntity, slot, stack) != null) {
            return "changed_additions:textures/models/armor/nothing_layer_1.png";
        } else if (entity instanceof Player player && ProcessTransfur.getPlayerTransfurVariant(player) != null && this.getClothTexture(ProcessTransfur.getPlayerTransfurVariant(player).getChangedEntity(), slot, stack) != null) {
            return "changed_additions:textures/models/armor/nothing_layer_1.png";
        }
        return null;
    }
}
