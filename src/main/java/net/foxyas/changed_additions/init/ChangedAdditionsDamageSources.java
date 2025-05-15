package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.process.util.PlayerUtil;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.init.ChangedParticles;
import net.ltxprogrammer.changed.init.ChangedTags;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ChangedAdditionsDamageSources {
    public static final DamageSource SOLVENT = new DamageSource("latex_solvent");

    public static EntityDamageSource mobAttack(LivingEntity mob) {
        return new EntityDamageSource("latex_solvent", mob);
    }

    @Mod.EventBusSubscriber
    public static class DamageHandle {

        @SubscribeEvent
        public static void onLatexSolventHurt(LivingHurtEvent event) {
            var entity = event.getEntity();
            var source = event.getSource();

            if (!isSolventDamage(source)) return;

            if (!isLatexTarget(entity)) return;

            // Efeito sonoro
            entity.playSound(SoundEvents.FIRE_EXTINGUISH, 2.5f, 0);

            // Partícula solvente base
            PlayerUtil.ParticlesUtil.sendParticles(
                    entity.level,
                    ChangedAdditionsParticles.solventDrips(),
                    entity.getEyePosition(),
                    0.25f, 0.25f, 0.25f,
                    8, 0.25f
            );

            // Partículas de latex coloridas
            sendLatexParticlesIfApplicable(entity);
        }

        private static boolean isSolventDamage(DamageSource source) {
            if (source == SOLVENT) return true;

            return source instanceof EntityDamageSource eds
                    && eds.getMsgId().startsWith(SOLVENT.getMsgId());
        }

        private static boolean isLatexTarget(Entity entity) {
            if (entity.getType().is(ChangedTags.EntityTypes.LATEX)) return true;

            return entity instanceof Player player
                    && ProcessTransfur.isPlayerLatex(player);
        }

        private static void sendLatexParticlesIfApplicable(Entity entity) {
            var level = entity.level;
            var position = entity.getEyePosition();

            if (entity instanceof ChangedEntity changed) {
                var colors = changed.getSelfVariant().getColors();
                var color = changed.getRandom().nextBoolean() ? colors.getFirst() : colors.getSecond();

                PlayerUtil.ParticlesUtil.sendParticles(
                        level,
                        ChangedParticles.drippingLatex(color),
                        position,
                        0.25f, 0.25f, 0.25f,
                        8, 0.25f
                );
            } else if (entity instanceof Player player) {
                var variant = ProcessTransfur.getPlayerTransfurVariant(player);
                if (variant != null) {
                    var colors = variant.getParent().getColors();
                    var color = player.getRandom().nextBoolean() ? colors.getFirst() : colors.getSecond();

                    PlayerUtil.ParticlesUtil.sendParticles(
                            level,
                            ChangedParticles.drippingLatex(color),
                            position,
                            0.25f, 0.25f, 0.25f,
                            8, 0.25f
                    );
                }
            }
        }
    }

}
