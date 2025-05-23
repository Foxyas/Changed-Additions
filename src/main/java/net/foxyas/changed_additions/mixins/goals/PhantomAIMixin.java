package net.foxyas.changed_additions.mixins.goals;

import net.foxyas.changed_additions.entities.goals.phantom.AvoidCatlikePlayerGoal;
import net.minecraft.world.entity.monster.Phantom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Phantom.class)
public abstract class PhantomAIMixin {

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void addCustomAI(CallbackInfo ci) {
        Phantom thisFixed = ((Phantom) (Object) this);
        thisFixed.goalSelector.addGoal(3, new AvoidCatlikePlayerGoal(thisFixed));
    }
}
