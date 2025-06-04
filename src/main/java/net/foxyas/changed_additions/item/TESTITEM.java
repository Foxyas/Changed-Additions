package net.foxyas.changed_additions.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.client.models.accessories.DefaultClothesColors;
import net.foxyas.changed_additions.client.models.accessories.IAccessoryItem;
import net.foxyas.changed_additions.client.models.accessories.models.AccessoriesMaleWolf;
import net.foxyas.changed_additions.init.ChangedAdditionsItems;
import net.foxyas.changed_additions.init.ChangedAdditionsTabs;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.Gender;
import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class TESTITEM extends ArmorItem implements IAccessoryItem, DyeableLeatherItem {
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
        if (entity instanceof Player player) {
            TransfurVariantInstance<?> variant = ProcessTransfur.getPlayerTransfurVariant(player);
            if (variant != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ResourceLocation getAccessoryTexture(ChangedEntity entity, EquipmentSlot slot, Gender gender, EntityRendererProvider.Context context) {
        if (getAccessoryModel(entity,slot,gender,context) instanceof AccessoriesMaleWolf<?>) {
            if (gender == Gender.MALE) {
                if (slot == EquipmentSlot.HEAD) {
                    return ChangedAdditionsMod.modResource("textures/entities/head_test.png");
                } else if (slot == EquipmentSlot.CHEST) {
                    return ChangedAdditionsMod.modResource("textures/entities/torso_arms.png");
                }
            }
        }


        return null;
    }

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientInitializer {
        @SubscribeEvent
        public static void onItemColorsInit(ColorHandlerEvent.Item event) {
            event.getItemColors().register(
                    (stack, layer) -> ((DyeableLeatherItem) stack.getItem()).getColor(stack),
                    ChangedAdditionsItems.TEST.get());
        }
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        this.setColor(stack, Color3.WHITE.toInt());
        return stack;
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        if (this.allowdedIn(tab)) {
            for (DefaultClothesColors color : DefaultClothesColors.values()) {
                ItemStack stack = new ItemStack(this);
                this.setColor(stack, color.getColorToInt());
                items.add(stack);
            }
        }
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return super.getArmorTexture(stack, entity, slot, type);
    }

    @Override
    public Color getRenderColor(ChangedEntity entity, EquipmentSlot slot) {
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

    @Override
    @Nullable
    public <E extends ChangedEntity> AdvancedHumanoidModel<E> getAccessoryModel(E entity, EquipmentSlot slot, Gender gender, EntityRendererProvider.Context context) {
        return IAccessoryItem.super.getAccessoryModel(entity, slot, gender, context);
    }
}
