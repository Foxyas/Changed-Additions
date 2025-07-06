package net.foxyas.changed_additions.variants;

import net.minecraft.world.damagesource.DamageSource;

public interface ExtraVariantStats {

    // Variable Set By Entity
    float BlockBreakSpeed();

    // Multiplier Based on % amount [Vanilla Attribute Style]
    default float getBlockBreakSpeedMultiplier() {
        return this.BlockBreakSpeed() + 1;
    }
}
