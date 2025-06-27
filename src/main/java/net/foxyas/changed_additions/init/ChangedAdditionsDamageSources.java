package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.process.util.FoxyasUtils;
import net.foxyas.changed_additions.process.util.PlayerUtil;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.init.ChangedParticles;
import net.ltxprogrammer.changed.init.ChangedTags;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

public class ChangedAdditionsDamageSources {
    public record DamageHolder(ResourceKey<DamageType> key) {
        public DamageSource source(RegistryAccess access) {
            final Holder<DamageType> type = access.lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key);
            return new DamageSource(type);
        }

        public DamageSource source(RegistryAccess access, Entity sourceEntity) {
            final Holder<DamageType> type = access.lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key);
            return new DamageSource(type, sourceEntity);
        }
    }

    private static DamageHolder holder(String name) {
        return new DamageHolder(ResourceKey.create(Registries.DAMAGE_TYPE, ChangedAdditionsMod.modResource(name)));
    }

    public static final DamageHolder LATEX_SOLVENT = holder("latex_solvent");
    public static final DamageHolder CONSCIENCE_LOST = holder("conscience_lost");


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
                player.level().playSound(null, player, SoundEvents.FIRE_EXTINGUISH, SoundSource.MASTER, 2.5f, 0f);
            } else {
                // Efeito sonoro
                entity.playSound(SoundEvents.FIRE_EXTINGUISH, 2.5f, 0);
            }

            // Dynamic Power
            int amount = (int) event.getAmount() / 4;

            // Partícula solvente base
            PlayerUtil.ParticlesUtil.sendParticles(
                    entity.level(),
                    ChangedAdditionsParticles.solventDrips(10, FoxyasUtils.clamp(new Random().nextFloat(0.45f), 0.15f, 0.45f)),
                    entity.position().add(0, entity.getBbHeight() / 2f, 0),
                    0.25f, 0.25f, 0.25f,
                    8 + FoxyasUtils.clamp(amount, 0, 22), 0.25f
            );

            // Partículas de latex coloridas
            sendLatexParticlesIfApplicable(entity, amount);
        }

        private static boolean isSolventDamage(DamageSource source) {
            return source.is(LATEX_SOLVENT.key());
        }

        private static boolean isLatexTarget(Entity entity) {
            if (entity.getType().is(ChangedTags.EntityTypes.LATEX)) return true;

            return entity instanceof Player player
                    && ProcessTransfur.isPlayerLatex(player);
        }

        private static void sendLatexParticlesIfApplicable(Entity entity, int amount) {
            var level = entity.level();
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
            var level = entity.level();
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
