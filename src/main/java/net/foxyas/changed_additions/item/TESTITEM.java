package net.foxyas.changed_additions.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.client.models.accessories.IAccessoryItem;
import net.foxyas.changed_additions.init.ChangedAdditionsTabs;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.Gender;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.function.Consumer;

public class TESTITEM extends ArmorItem implements IAccessoryItem {
    public TESTITEM() {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForSlot(EquipmentSlot p_40410_) {
                return 0;
            }

            @Override
            public int getDefenseForSlot(EquipmentSlot p_40411_) {
                return 0;
            }

            @Override
            public int getEnchantmentValue() {
                return 0;
            }

            @Override
            public SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_LEATHER;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.EMPTY;
            }

            @Override
            public @NotNull String getName() {
                return "TEST";
            }

            @Override
            public float getToughness() {
                return 0;
            }

            @Override
            public float getKnockbackResistance() {
                return 0;
            }
        }, EquipmentSlot.HEAD, new Item.Properties().tab(ChangedAdditionsTabs.CHANGED_ADDITIONS_TAB).stacksTo(64).rarity(Rarity.COMMON));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return ImmutableMultimap.of(); // Sem atributos
    }


    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {
        return true;
    }

    @Override
    public ResourceLocation getAccessoryTexture(ChangedEntity entity, EquipmentSlot slot, Gender gender) {
        if (gender == Gender.MALE) {
            if (slot == EquipmentSlot.HEAD) {
                return ChangedAdditionsMod.modResource("textures/entities/head_test.png");
            } else if (slot == EquipmentSlot.CHEST) {
                return ChangedAdditionsMod.modResource("textures/entities/torso_arms.png");
            }
        }


        return null;
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return super.getArmorTexture(stack, entity, slot, type);
    }

    @Override
    public Color getColor(ChangedEntity entity, EquipmentSlot slot) {
        if (entity.isShiftKeyDown()) {
            return new Color(255, 255, 255);
        }

        if (slot == EquipmentSlot.HEAD) {
            return new Color(251, 171, 0);
        } else if (slot == EquipmentSlot.CHEST) {
            return new Color(0, 255, 199);
        }



        return new Color(255, 0, 0);
    }
}
