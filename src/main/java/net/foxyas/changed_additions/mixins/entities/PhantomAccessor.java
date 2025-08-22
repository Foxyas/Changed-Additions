package net.foxyas.changed_additions.mixins.entities;

import net.minecraft.world.entity.monster.Phantom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Phantom.class)
public interface PhantomAccessor {

    @Accessor("attackPhase")
    Phantom.AttackPhase getAttackPhase();

    @Accessor("attackPhase")
    void setAttackPhase(Phantom.AttackPhase attackPhase);
}
