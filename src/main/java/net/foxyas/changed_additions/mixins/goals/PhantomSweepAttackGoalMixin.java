package net.foxyas.changed_additions.mixins.goals;

import net.foxyas.changed_additions.variants.TransfurVariantTags;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = Phantom.PhantomSweepAttackGoal.class)
public abstract class PhantomSweepAttackGoalMixin extends Goal {
    @Shadow
    private boolean isScaredOfCat;

    @Shadow
    private int catSearchTick;

    @Shadow @Final
    Phantom this$0;

    @Inject(method = "canContinueToUse", at = @At("HEAD"), cancellable = true)
    private void canContinueToUseInject(CallbackInfoReturnable<Boolean> cir) {
        Phantom phantomOwner = this$0;

        LivingEntity livingentity = phantomOwner.getTarget();
        if (livingentity != null && livingentity.isAlive()) {
            if (this.canUse()) {
                if (phantomOwner.tickCount > this.catSearchTick) {
                    this.catSearchTick = phantomOwner.tickCount + 20;


                    List<LivingEntity> list = phantomOwner.level.getEntitiesOfClass(
                            LivingEntity.class,
                            phantomOwner.getBoundingBox().inflate(16.0D),
                            (e) -> {
                                if (e instanceof Player player) {
                                    return ProcessTransfur.getPlayerTransfurVariantSafe(player).map(transfurVariantInstance ->
                                            transfurVariantInstance.getParent().is(TransfurVariantTags.CAT_LIKE)
                                                    || transfurVariantInstance.getParent().is(TransfurVariantTags.LEOPARD_LIKE)
                                    ).orElse(false);
                                } else if (e instanceof ChangedEntity changedEntity && changedEntity.getSelfVariant() != null) {
                                    return changedEntity.getSelfVariant().is(TransfurVariantTags.CAT_LIKE)
                                            || changedEntity.getSelfVariant().is(TransfurVariantTags.LEOPARD_LIKE);
                                }
                                return false;
                            }
                    );

                    for (LivingEntity entity : list) {
                        if (entity instanceof Player player) {
                            // Toca o som de hiss
                            phantomOwner.level.playSound(
                                    null, // null = som global (todos ouvem)
                                    player.blockPosition(),
                                    SoundEvents.CAT_HISS,
                                    SoundSource.PLAYERS,
                                    1.0F,
                                    1.0F
                            );
                        }
                    }

                    this.isScaredOfCat = !list.isEmpty();
                }
                cir.setReturnValue(!this.isScaredOfCat);
            }
        }
    }
}
