package net.foxyas.changed_additions.advancements.critereon;

import net.foxyas.changed_additions.entities.extras.CustomPatReaction;
import net.foxyas.changed_additions.init.ChangedAdditionsCriteriaTriggers;
import net.foxyas.changed_additions.init.ChangedAdditionsTags;
import net.foxyas.changed_additions.process.util.PlayerUtil;
import net.ltxprogrammer.changed.ability.GrabEntityAbility;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.Emote;
import net.ltxprogrammer.changed.entity.beast.AbstractDarkLatexWolf;
import net.ltxprogrammer.changed.init.ChangedAbilities;
import net.ltxprogrammer.changed.init.ChangedParticles;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

import static net.foxyas.changed_additions.init.ChangedAdditionsItems.DARK_LATEX_COAT;
import static net.foxyas.changed_additions.init.ChangedAdditionsItems.DARK_LATEX_HEAD_CAP;

public class PatFeatureHandle {

    public static boolean isPossibleToPat(Player player) {
        var variant = ProcessTransfur.getPlayerTransfurVariant(player);
        if (variant != null) {
            var ability = variant.getAbilityInstance(ChangedAbilities.GRAB_ENTITY_ABILITY.get());
            if (ability != null
                    && ability.suited
                    && ability.grabbedHasControl) {
                return false;
            }
        }


        return GrabEntityAbility.getGrabber(player) == null;
    }

    //Thanks gengyoubo for the code
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null) return;

        Entity targetEntity = getEntityLookingAt(entity, 3);
        if (targetEntity == null) return;

        if (isInSpectatorMode(entity)) return;

        if (entity instanceof Player p && !(isPossibleToPat(p))) return;


        if (targetEntity instanceof ChangedEntity) {
            handleLatexEntity(entity, targetEntity, world);
        } else if (targetEntity instanceof Player && entity instanceof Player) {
            handlePlayerEntity((Player) entity, (Player) targetEntity, world);
        } else if (targetEntity.getType().is(ChangedAdditionsTags.EntityTypes.PATABLE_ENTITIES)) {
            handlePatableEntity(entity, targetEntity, world);
        }
    }

    private static Entity getEntityLookingAt(Entity entity, double reach) {
        double distance = reach * reach;
        Vec3 eyePos = entity.getEyePosition(1.0f);
        HitResult hitResult = entity.pick(reach, 1.0f, false);

        if (hitResult.getType() != HitResult.Type.MISS) {
            distance = hitResult.getLocation().distanceToSqr(eyePos);
        }

        Vec3 viewVec = entity.getViewVector(1.0F);
        Vec3 toVec = eyePos.add(viewVec.x * reach, viewVec.y * reach, viewVec.z * reach);
        AABB aabb = entity.getBoundingBox().expandTowards(viewVec.scale(reach)).inflate(1.0D, 1.0D, 1.0D);

        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(entity, eyePos, toVec, aabb, e -> {
            if (e.isSpectator()) return false;
            if (!(e instanceof LivingEntity le)) return false;
            if (GrabEntityAbility.getGrabber(le) == null) return true;
            LivingEntity livingEntity = Objects.requireNonNull(GrabEntityAbility.getGrabber(le)).getEntity();
            return livingEntity != entity;
        }, distance);

        if (entityHitResult != null) {
            Entity hitEntity = entityHitResult.getEntity();
            if (eyePos.distanceToSqr(entityHitResult.getLocation()) <= reach * reach) {
                return hitEntity;
            }
        }
        return null;
    }

    private static boolean isInSpectatorMode(Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            return serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
        } else if (entity.level.isClientSide() && entity instanceof Player player) {
            return Objects.requireNonNull(Objects.requireNonNull(Minecraft.getInstance().getConnection()).getPlayerInfo(player.getGameProfile().getId())).getGameMode() == GameType.SPECTATOR;
        }
        return false;
    }

    private static void handleSpecialEntities(Entity player, Entity target) {
        //if (!isInCreativeMode(player)) return;

        if (isHandEmpty(player, InteractionHand.MAIN_HAND) || isHandEmpty(player, InteractionHand.OFF_HAND)) {
            if (player instanceof Player) {
                ((Player) player).swing(getSwingHand(player), true);
            }
            if (player instanceof Player p && !p.level.isClientSide()) {
                p.displayClientMessage(new TranslatableComponent("key.changed_additions.pat_message", target.getDisplayName().getString()), true);
				if (target instanceof CustomPatReaction pat) {
					pat.WhenPattedReaction(p);
                    pat.WhenPattedReaction();
                    //p.displayClientMessage(new TextComponent("pat_message:" + target.getDisplayName().getString()), false);
				}
            }
        }
    }

    private static void handleLatexEntity(Entity player, Entity target, LevelAccessor world) {
        boolean isPlayerTransfur = ProcessTransfur.isPlayerTransfurred((Player) player);
        //if (!isPlayerTransfur) return;

        if (isHandEmpty(player, InteractionHand.MAIN_HAND)
                || isHandEmpty(player, InteractionHand.OFF_HAND)) {
            Player p = (Player) player;
            p.swing(getSwingHand(player), true);

            if (world instanceof ServerLevel serverLevel) {
                p.swing(getSwingHand(player), true);
                serverLevel.sendParticles(ParticleTypes.HEART, target.getX(), target.getY() + 1, target.getZ(), 4, 0.3, 0.3, 0.3, 1);
                serverLevel.sendParticles(ChangedParticles.emote(target, Emote.HEART), target.getX(), target.getY() + 1, target.getZ(), 1, 0.3, 0.3, 0.3, 1);
                // Dispara o trigger personalizado
                SpawnEmote(p, target);
                if (p instanceof ServerPlayer sp) {
                    GiveStealthPatAdvancement(sp, target);
                }

                if (target instanceof CustomPatReaction e) {
                    e.WhenPattedReaction(p);
                    e.WhenPattedReaction();
                    //p.displayClientMessage(new TextComponent("pat_message:" + target.getDisplayName().getString()), false);
                }

                // Exibe mensagens
                p.displayClientMessage(new TranslatableComponent("key.changed_additions.pat_message", target.getDisplayName().getString()), true);
            }


        }
    }

    private static void handlePlayerEntity(Player player, Player target, LevelAccessor world) {
        boolean isPlayerTransfur =  ProcessTransfur.isPlayerTransfurred(player);
        boolean isTargetTransfur =  ProcessTransfur.isPlayerTransfurred(target);

        if (isHandEmpty(player, InteractionHand.MAIN_HAND) || isHandEmpty(player, InteractionHand.OFF_HAND)) {
            if (!isPlayerTransfur && !isTargetTransfur) {
                return;
            }//Don't Be Able to Pet if at lest one is Transfur :P

            player.swing(getSwingHand(player), true);
            if (isPlayerTransfur && isTargetTransfur) {
                // Todo later i guess
            }


            if (isTargetTransfur && world instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HEART, target.getX(), target.getY() + 1, target.getZ(), 4, 0.3, 0.3, 0.3, 1);
                serverLevel.sendParticles(ChangedParticles.emote(target, Emote.HEART), target.getX(), target.getY() + 1, target.getZ(), 1, 0.3, 0.3, 0.3, 1);
                if (serverLevel.random.nextFloat(100) <= 2.5f) {
                    target.heal(6f);
                    GivePatAdvancement(player);
                }
            }

            if (!player.level.isClientSide()) {
                player.displayClientMessage(new TranslatableComponent("key.changed_additions.pat_message", target.getDisplayName().getString()), true);
                target.displayClientMessage(new TranslatableComponent("key.changed_additions.pat_received", player.getDisplayName().getString()), true);
            }
        }
    }


    private static void handlePatableEntity(Entity player, Entity target, LevelAccessor world) {
        if (isHandEmpty(player, InteractionHand.MAIN_HAND) || isHandEmpty(player, InteractionHand.OFF_HAND)) {
            if (player instanceof Player) {
                ((Player) player).swing(getSwingHand(player), true);
            }
            if (world instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HEART, target.getX(), target.getY() + 1, target.getZ(), 7, 0.3, 0.3, 0.3, 1);
            }
            if (player instanceof Player p && !p.level.isClientSide()) {
                p.displayClientMessage(new TranslatableComponent("key.changed_additions.pat_message", target.getDisplayName().getString()), true);
            }
        }
    }

    private static boolean isInCreativeMode(Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            return serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
        } else if (entity.level.isClientSide() && entity instanceof Player player) {
            return Objects.requireNonNull(Objects.requireNonNull(Minecraft.getInstance().getConnection()).getPlayerInfo(player.getGameProfile().getId())).getGameMode() == GameType.CREATIVE;
        }
        return false;
    }

    private static boolean isHandEmpty(Entity entity, InteractionHand hand) {
        return entity instanceof LivingEntity livingEntity && livingEntity.getItemInHand(hand).getItem() == Blocks.AIR.asItem();
    }

    private static InteractionHand getSwingHand(Entity entity) {
        return isHandEmpty(entity, InteractionHand.MAIN_HAND) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    public static void GivePatAdvancement(Entity entity) {
        if (entity instanceof ServerPlayer _player) {
            Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("changed_additions:pat_advancement"));
            assert _adv != null;
            if (_player.getAdvancements().getOrStartProgress(_adv).isDone()) {
                return;
            }
            AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
            if (!_ap.isDone()) {
                for (String string : _ap.getRemainingCriteria()) {
                    _player.getAdvancements().award(_adv, string);
                }
            }
        }
    }

    public static void SpawnEmote(Player player, Entity target){
        if (target instanceof ChangedEntity changedEntity){
            if (changedEntity.getTarget() == player){
                return;
            }
            if (shouldBeConfused(player, target)) {
                PlayerUtil.ParticlesUtil.sendParticles(player.getLevel(),
                        ChangedParticles.emote(changedEntity, Emote.CONFUSED),
                        target.getX(),
                        target.getY() + (double) target.getDimensions(target.getPose()).height + 0.65,
                        target.getZ(),
                        0.0f,
                        0.0f,
                        0.0f, 1, 0f
                );
            }
        }
    }

    private static boolean shouldBeConfused(Player player, Entity entity) {
        if (entity instanceof AbstractDarkLatexWolf) {
            // Verificando se o jogador usa a armadura correta
            return player.getItemBySlot(EquipmentSlot.HEAD).is(DARK_LATEX_HEAD_CAP.get())
                    && player.getItemBySlot(EquipmentSlot.CHEST).is(DARK_LATEX_COAT.get());
        }
        return false;
    }

    public static void GiveStealthPatAdvancement(Entity entity, Entity target) {
        if (entity instanceof ServerPlayer _player) {
			/*Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("changed_additions:stealthpats"));
            assert _adv != null;
            if (_player.getAdvancements().getOrStartProgress(_adv).isDone()){
				return;
			}
			AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
			if (!_ap.isDone()) {
				for (String string : _ap.getRemainingCriteria()) {
					_player.getAdvancements().award(_adv, string);
				}
			}*/
            ChangedAdditionsCriteriaTriggers.PAT_ENTITY_TRIGGER.Trigger(_player, target);
        }

    }
}
