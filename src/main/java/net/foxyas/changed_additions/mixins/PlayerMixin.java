package net.foxyas.changed_additions.mixins;

import net.foxyas.changed_additions.abilities.ClawsAbility;
import net.foxyas.changed_additions.init.ChangedAdditionsAbilities;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QTEManager;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QuickTimeEvent;
import net.ltxprogrammer.changed.ability.AbstractAbility;
import net.ltxprogrammer.changed.ability.AbstractAbilityInstance;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "attack", at = @At("HEAD"))
    private void CustomClawSweepAttack(Entity entity, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (player.getAttackStrengthScale(0.0f) < 0.9)
            return;
        ProcessTransfur.getPlayerTransfurVariantSafe(player).ifPresent((variantInstance -> {
            AbstractAbilityInstance abilityInstance = variantInstance.getAbilityInstance(ChangedAdditionsAbilities.CLAWS_ABILITY.get());
            if (abilityInstance != null) {
                AbstractAbility<?> clawAbility = variantInstance.getAbilityInstance(ChangedAdditionsAbilities.CLAWS_ABILITY.get()).ability;
                if (clawAbility instanceof ClawsAbility ability && ability.isActive && player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                    // ⚔ AOE
                    double radius = 1;
                    AABB attackArea = entity.getBoundingBox().inflate(radius, 0.25, radius);
                    List<LivingEntity> nearbyEntities = player.level.getEntitiesOfClass(LivingEntity.class, attackArea);
                    float f = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    float f3 = nearbyEntities.isEmpty() ? f : f / nearbyEntities.size();
                    // 🔥 Knock back
                    for (LivingEntity livingEntity : nearbyEntities) {
                        if (livingEntity != entity && livingEntity != player && (!(livingEntity instanceof ArmorStand) || !((ArmorStand) livingEntity).isMarker()) && player.canHit(livingEntity, 0)) {
                            livingEntity.knockback(0.4, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
                            livingEntity.hurt(DamageSource.playerAttack(player), f3);
                        }
                    }
                    // visual
                    double d0 = (double) (-Mth.sin(player.getYRot() * 0.017453292F)) * 1;
                    double d1 = (double) Mth.cos(player.getYRot() * 0.017453292F) * 1;
                    if (player.level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5), player.getZ() + d1, 0, d0, 0.0, d1, 0.0);
                        serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.6), player.getZ() + d1, 0, d0, 0.0, d1, 0.0);
                        serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.7), player.getZ() + d1, 0, d0, 0.0, d1, 0.0);
                        player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1f, 0.75f);
                    }
                }
            }
        }));

    }
}
