package net.foxyas.changed_additions.mixins.goals;

import net.foxyas.changed_additions.variants.TransfurVariantTags;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(AvoidEntityGoal.class)
public class AvoidEntityGoalMixin {

    @Shadow @Nullable protected LivingEntity toAvoid;

    @Shadow @Final protected PathfinderMob mob;

    @Inject(method = "canUse", at = @At("TAIL"), cancellable = true)
    public void preventAvoidLatexSnep(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob instanceof Ocelot || this.mob instanceof Cat){
            if (toAvoid != null && toAvoid instanceof Player player){
                if (ProcessTransfur.getPlayerTransfurVariant(player) != null) {
                   if (ProcessTransfur.getPlayerTransfurVariant(player).getParent().is(TransfurVariantTags.CAT_LIKE)
                            || ProcessTransfur.getPlayerTransfurVariant(player).getParent().is(TransfurVariantTags.LEOPARD_LIKE)) {
                        cir.setReturnValue(false);
                   }
                }
            }
        }
    }
}
