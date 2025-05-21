package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.process.util.FoxyasUtils;
import net.foxyas.changed_additions.process.util.PlayerUtil;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.init.ChangedParticles;
import net.ltxprogrammer.changed.init.ChangedTags;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

public class ChangedAdditionsDamageSources {
    public static final DamageSource LATEX_SOLVENT = new DamageSource("latex_solvent") {
        @Override
        public float getFoodExhaustion() {
            return super.getFoodExhaustion() + 0.5f;
        }
    };

    public static EntityDamageSource LatexSolventMobAttack(LivingEntity mob) {
        return new EntityDamageSource("latex_solvent", mob);
    }


    public static final DamageSource CONSCIENCE_LOST = new DamageSource("conscience_lost") {
        @Override
        public float getFoodExhaustion() {
            return 0;
        }
    }.bypassArmor();

    public static EntityDamageSource ConscienceLostMobAttack(LivingEntity mob) {
        return new EntityDamageSource("conscience_lost", mob);
    }


    @Mod.EventBusSubscriber
    public static class DamageHandle {

        @SubscribeEvent
        public static void onLatexSolventHurt(LivingHurtEvent event) {
            var entity = event.getEntity();
            var source = event.getSource();

            if (!isSolventDamage(source)) {
                return;
            }

            if (!isLatexTarget(entity)) {
                return;
            }


            if (entity instanceof Player player) {
                // Efeito sonoro
                player.getLevel().playSound(null, player, SoundEvents.FIRE_EXTINGUISH, SoundSource.MASTER, 2.5f, 0f);
            } else {
                // Efeito sonoro
                entity.playSound(SoundEvents.FIRE_EXTINGUISH, 2.5f, 0);
            }

            // Dynamic Power
            int amount = (int) event.getAmount() / 4;

            // Partícula solvente base
            PlayerUtil.ParticlesUtil.sendParticles(
                    entity.level,
                    ChangedAdditionsParticles.solventDrips(10, FoxyasUtils.clamp(entity.getLevel().getRandom().nextFloat(0.45f), 0.15f, 0.45f)),
                    entity.position().add(0, entity.getBbHeight() / 2f, 0),
                    0.25f, 0.25f, 0.25f,
                    8 + FoxyasUtils.clamp(amount, 0, 22), 0.25f
            );

            // Partículas de latex coloridas
            sendLatexParticlesIfApplicable(entity, amount);
        }

        private static boolean isSolventDamage(DamageSource source) {
            if (source == LATEX_SOLVENT) return true;

            return source instanceof EntityDamageSource eds
                    && eds.getMsgId().startsWith(LATEX_SOLVENT.getMsgId());
        }

        private static boolean isLatexTarget(Entity entity) {
            if (entity.getType().is(ChangedTags.EntityTypes.LATEX)) return true;

            return entity instanceof Player player
                    && ProcessTransfur.isPlayerLatex(player);
        }

        private static void sendLatexParticlesIfApplicable(Entity entity, int amount) {
            var level = entity.level;
            var position = entity.position();
            float speed = 0.05f;

            if (entity instanceof ChangedEntity changed) {
                var colors = changed.getSelfVariant().getColors();
                var color = changed.getRandom().nextBoolean() ? colors.getFirst() : colors.getSecond();

                PlayerUtil.ParticlesUtil.sendParticles(
                        level,
                        ChangedParticles.drippingLatex(color),
                        position,
                        0.25f, 0.25f, 0.25f,
                        8 + FoxyasUtils.clamp(amount, 0, 22), speed
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
                            8 + FoxyasUtils.clamp(amount, 0, 22), speed
                    );
                }
            }
        }

        private static void sendLatexParticlesIfApplicable(Entity entity) {
            var level = entity.level;
            var position = entity.getEyePosition();
            float speed = 0.05f;

            if (entity instanceof ChangedEntity changed) {
                var colors = changed.getSelfVariant().getColors();
                var color = changed.getRandom().nextBoolean() ? colors.getFirst() : colors.getSecond();

                PlayerUtil.ParticlesUtil.sendParticles(
                        level,
                        ChangedParticles.drippingLatex(color),
                        position,
                        0.25f, 0.25f, 0.25f,
                        8, speed
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
                            8, speed
                    );
                }
            }
        }
    }

}
