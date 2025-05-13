package net.foxyas.changed_additions.item;

import net.foxyas.changed_additions.init.ChangedAdditionsModTabs;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class BiomassItem extends Item {
    public BiomassItem() {
        super(new Item.Properties().tab(ChangedAdditionsModTabs.CHANGED_ADDITIONS_TAB).stacksTo(64).rarity(Rarity.COMMON).food(
                new FoodProperties.Builder().alwaysEat().nutrition(-6).saturationMod(1).effect(() -> new MobEffectInstance(MobEffects.HUNGER), 0.5f).build())
        );
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 32;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        entity.playSound(SoundEvents.GENERIC_EAT, 1, 0);
        return super.finishUsingItem(stack, level, entity);
    }
}
