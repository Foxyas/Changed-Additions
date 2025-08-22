package net.foxyas.changed_additions.entities.goals.phantom;

import net.foxyas.changed_additions.mixins.entities.PhantomAccessor;
import net.foxyas.changed_additions.variants.TransfurVariantTags;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;
import java.util.List;

public class AvoidCatlikePlayerGoal extends Goal {
    private final Phantom phantom;
    private int nextSearchTick;
    private boolean isScared;

    public AvoidCatlikePlayerGoal(Phantom phantom) {
        this.phantom = phantom;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (phantom.getTarget() == null || !phantom.getTarget().isAlive()) return false;
        return checkNearbyCatlikeEntities();
    }

    @Override
    public boolean canContinueToUse() {
        return !isScared;
    }

    @Override
    public void start() {
        super.start();
        phantom.setTarget(null);
        if (phantom instanceof PhantomAccessor phantomAccessor) {
            phantomAccessor.setAttackPhase(Phantom.AttackPhase.CIRCLE);
        }
    }

    private boolean checkNearbyCatlikeEntities() {
        if (phantom.tickCount < nextSearchTick)
            return isScared;

        nextSearchTick = phantom.tickCount + 20;

        List<LivingEntity> list = phantom.level().getEntitiesOfClass(
                LivingEntity.class,
                phantom.getBoundingBox().inflate(16.0D),
                e -> {
                    if (e instanceof Player player) {
                        return ProcessTransfur.getPlayerTransfurVariantSafe(player).map(v ->
                                v.getParent().is(TransfurVariantTags.CAT_LIKE) || v.getParent().is(TransfurVariantTags.LEOPARD_LIKE)
                        ).orElse(false);
                    } else if (e instanceof ChangedEntity changed && changed.getSelfVariant() != null) {
                        return changed.getSelfVariant().is(TransfurVariantTags.CAT_LIKE)
                                || changed.getSelfVariant().is(TransfurVariantTags.LEOPARD_LIKE);
                    }
                    return false;
                });

        if (!list.isEmpty()) {
            for (LivingEntity entity : list) {
                if (entity instanceof Player player) {
                    phantom.level().playSound(
                            null,
                            player.blockPosition(),
                            SoundEvents.CAT_HISS,
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F
                    );
                }
            }
            isScared = true;
        } else {
            isScared = false;
        }

        return isScared;
    }
}
