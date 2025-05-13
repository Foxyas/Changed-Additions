package net.foxyas.changed_additions.item;

import net.foxyas.changed_additions.init.ChangedAdditionsModTabs;
import net.foxyas.changed_additions.process.util.PlayerUtil;
import net.ltxprogrammer.changed.init.ChangedParticles;
import net.ltxprogrammer.changed.init.ChangedSounds;
import net.ltxprogrammer.changed.item.SpecializedAnimations;
import net.ltxprogrammer.changed.item.Syringe;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static net.foxyas.changed_additions.process.ProcessUntransfur.UntransfurPlayer;

public class LaethinSyringeItem extends Item implements SpecializedAnimations {
    public LaethinSyringeItem() {
        super(new Item.Properties().tab(ChangedAdditionsModTabs.CHANGED_ADDITIONS_TAB).stacksTo(64).rarity(Rarity.COMMON));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 32;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        Player player = entity instanceof Player ? (Player) entity : null;


        if (player instanceof ServerPlayer serverPlayer) {
            ProcessTransfur.ifPlayerTransfurred(serverPlayer, (transfurVariantInstance) -> {
                PlayerUtil.ParticlesUtil.sendParticles(serverPlayer.getLevel(), ChangedParticles.drippingLatex(transfurVariantInstance.getTransfurColor()), serverPlayer.getEyePosition(), 0.25f, 0.25f, 0.25f, 10, 0);
            });
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
        }

        UntransfurPlayer(player);
        ChangedSounds.broadcastSound(entity, ChangedSounds.SWORD1, 1.0F, 1.0F);
        assert player != null;
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Nullable
    public SpecializedAnimations.AnimationHandler getAnimationHandler() {
        return new Syringe.SyringeAnimation(this);
    }
}
