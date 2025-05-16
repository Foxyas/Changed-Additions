package net.foxyas.changed_additions.enchantments;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.init.ChangedAdditionsDamageSources;
import net.foxyas.changed_additions.init.ChangedAdditionsMobEffects;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.init.ChangedTags;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.extensions.IForgeEnchantment;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SolventEnchantment extends Enchantment implements IForgeEnchantment {
    public SolventEnchantment(EquipmentSlot... slots) {
        super(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, slots);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    protected boolean checkCompatibility(Enchantment ench) {
        return this != ench && !List.of(Enchantments.SHARPNESS).contains(ench);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack itemstack) {
        if (itemstack.getItem() instanceof SwordItem || itemstack.getItem() instanceof TridentItem) {
            return true;
        }


        return Ingredient.of(ItemTags.create(new ResourceLocation("changed_additions:latex_solvent_appliable"))).test(itemstack);
    }

    @Override
    public void doPostAttack(@NotNull LivingEntity source, @NotNull Entity target, int level) {
        super.doPostAttack(source, target, level);
        if (target.getType().is(ChangedTags.EntityTypes.LATEX) && target instanceof ChangedEntity changedEntity) {
            changedEntity.addEffect(new MobEffectInstance(ChangedAdditionsMobEffects.SOLVENT.get(),40, level - 1)); // 5s
        } else if (target instanceof Player player && ProcessTransfur.isPlayerLatex(player)) {
            player.addEffect(new MobEffectInstance(ChangedAdditionsMobEffects.SOLVENT.get(),40, level - 1)); // 5s
        }
    }

    @Override
    public void doPostHurt(@NotNull LivingEntity source, @NotNull Entity target, int level) {
        super.doPostHurt(source, target, level);
    }
}