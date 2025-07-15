package net.foxyas.changed_additions.process.util;

import net.ltxprogrammer.changed.client.renderer.AdvancedHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.init.ChangedRegistry;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class PlayerUtil {

    public static void TransfurPlayer(Entity entity, TransfurVariant<?> latexVariant) {
        LivingEntity livingEntity = (LivingEntity) entity;
        ProcessTransfur.transfur(livingEntity, entity.getLevel(), latexVariant, true);
    }

    public static void TransfurPlayer(Entity entity, String id) {
        ResourceLocation form;
        try {
            form = new ResourceLocation(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LivingEntity livingEntity = (LivingEntity) entity;
        if (TransfurVariant.getPublicTransfurVariants().map(TransfurVariant::getRegistryName).anyMatch(form::equals)) {
            TransfurVariant<?> latexVariant = ChangedRegistry.TRANSFUR_VARIANT.get().getValue(form);
            ProcessTransfur.transfur(livingEntity, entity.getLevel(), latexVariant, true);
        }
    }

    public static void UnTransfurPlayer(Entity entity) {
        Player player = (Player) entity;
        ProcessTransfur.ifPlayerTransfurred(player, (variant) -> {
            variant.unhookAll(player);
            ProcessTransfur.removePlayerTransfurVariant(player);
            ProcessTransfur.setPlayerTransfurProgress(player, 0.0f);
        });
    }

    public static void UnTransfurPlayerAndKill(Entity entity) {
        Player player = (Player) entity;
        ProcessTransfur.ifPlayerTransfurred(player, (variant) -> {
            variant.getParent().replaceEntity(player);
            variant.unhookAll(player);
            ProcessTransfur.removePlayerTransfurVariant(player);
            ProcessTransfur.setPlayerTransfurProgress(player, 0.0f);
        });
    }

    @Nullable
    public static Entity getEntityPlayerLookingAt(Player player, double range) {
        Level world = player.level;
        Vec3 startVec = player.getEyePosition(1.0F); // Player's eye position
        Vec3 lookVec = player.getLookAngle(); // Player's look direction
        Vec3 endVec = startVec.add(lookVec.scale(range)); // End point of the line of sight

        Entity closestEntity = null;
        double closestDistance = range;

        // Iterate over all entities within range
        for (Entity entity : world.getEntities(player, player.getBoundingBox().expandTowards(lookVec.scale(range)).inflate(1.0D))) {
            // Ignore entities in spectator mode
            if (entity.isSpectator()) {
                continue;
            }

            AABB entityBoundingBox = entity.getBoundingBox().inflate(entity.getPickRadius());

            // Check if the line of sight intersects the entity's bounding box
            if (entityBoundingBox.contains(startVec) || entityBoundingBox.clip(startVec, endVec).isPresent()) {
                double distanceToEntity = startVec.distanceTo(entity.position());

                if (distanceToEntity < closestDistance) {
                    closestEntity = entity;
                    closestDistance = distanceToEntity;
                }
            }
        }

        return closestEntity; // Return the closest entity the player is looking at
    }

    @Nullable
    public static Entity getEntityPlayerLookingAt(Entity player, double range) {
        Level world = player.level;
        Vec3 startVec = player.getEyePosition(1.0F); // Player's eye position
        Vec3 lookVec = player.getLookAngle(); // Player's look direction
        Vec3 endVec = startVec.add(lookVec.scale(range)); // End point of the line of sight

        Entity closestEntity = null;
        double closestDistance = range;

        // Iterate over all entities within range
        for (Entity entity : world.getEntities(player, player.getBoundingBox().expandTowards(lookVec.scale(range)).inflate(1.0D))) {
            // Ignore entities in spectator mode
            if (entity.isSpectator()) {
                continue;
            }

            AABB entityBoundingBox = entity.getBoundingBox().inflate(entity.getPickRadius());

            // Check if the line of sight intersects the entity's bounding box
            if (entityBoundingBox.contains(startVec) || entityBoundingBox.clip(startVec, endVec).isPresent()) {
                double distanceToEntity = startVec.distanceTo(entity.position());

                if (distanceToEntity < closestDistance) {
                    closestEntity = entity;
                    closestDistance = distanceToEntity;
                }
            }
        }

        return closestEntity; // Return the closest entity the player is looking at
    }

    @Nullable
    public static Entity getEntityLookingAt(Entity entity, double reach) {
        double distance = reach * reach;
        Vec3 eyePos = entity.getEyePosition(1.0f);
        HitResult hitResult = entity.pick(reach, 1.0f, false);

        if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
            distance = hitResult.getLocation().distanceToSqr(eyePos);
        }

        Vec3 viewVec = entity.getViewVector(1.0F);
        Vec3 toVec = eyePos.add(viewVec.x * reach, viewVec.y * reach, viewVec.z * reach);
        AABB aabb = entity.getBoundingBox().expandTowards(viewVec.scale(reach)).inflate(1.0D, 1.0D, 1.0D);

        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(entity, eyePos, toVec, aabb, e -> !e.isSpectator(), distance);

        if (entityHitResult != null) {
            Entity hitEntity = entityHitResult.getEntity();
            if (eyePos.distanceToSqr(entityHitResult.getLocation()) <= reach * reach) {
                return hitEntity;
            }
        }
        return null;
    }

    @Nullable
    public static EntityHitResult getEntityHitLookingAt(Entity entity, double reach) {
        double distance = reach * reach;
        Vec3 eyePos = entity.getEyePosition(1.0f);
        HitResult hitResult = entity.pick(reach, 1.0f, false);

        if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
            distance = hitResult.getLocation().distanceToSqr(eyePos);
        }

        Vec3 viewVec = entity.getViewVector(1.0F);
        Vec3 toVec = eyePos.add(viewVec.x * reach, viewVec.y * reach, viewVec.z * reach);
        AABB aabb = entity.getBoundingBox().expandTowards(viewVec.scale(reach)).inflate(1.0D, 1.0D, 1.0D);

        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(entity, eyePos, toVec, aabb, e -> !e.isSpectator(), distance);

        if (entityHitResult != null) {
            if (eyePos.distanceToSqr(entityHitResult.getLocation()) <= reach * reach) {
                return entityHitResult;
            }
        }
        return null;
    }

    public static HitResult getEntityBlockHitLookingAt(Entity entity, double reach, float deltaTicks, boolean affectByFluids) {
        return entity.pick(reach, deltaTicks, affectByFluids);
    }

    @Nullable
    public static Vec3 getRelativeHitPosition(LivingEntity entity, double distance) {
        EntityHitResult hitResult = PlayerUtil.getEntityHitLookingAt(entity, distance);
        if (hitResult != null) {
            Vec3 hitLocation = hitResult.getLocation();
            Vec3 entityPosition = hitResult.getEntity().getPosition(1);
            return hitLocation.subtract(entityPosition);
        }
        return null;
    }


    public static boolean isLineOfSightClear(Player player, Entity entity) {
        var level = player.getLevel();
        var playerEyePos = player.getEyePosition(1.0F); // Posição dos olhos do jogador
        var entityEyePos = entity.getBoundingBox().getCenter(); // Centro da entidade

        // Realiza o traçado de linha
        var result = level.clip(new ClipContext(
                playerEyePos,
                entityEyePos,
                ClipContext.Block.VISUAL, // Apenas blocos visuais são considerados
                ClipContext.Fluid.NONE, // Ignorar fluidos
                player
        ));

        // Retorna true se o resultado for MISS (nenhum bloco obstruindo)
        return result.getType() == HitResult.Type.MISS;
    }

    @Nullable
    public static Entity getEntityPlayerLookingAtType2(Entity entity, Entity player, double entityReach) {
        double distance = entityReach * entityReach;
        Vec3 eyePos = player.getEyePosition(1.0f);
        HitResult hitResult = entity.pick(entityReach, 1.0f, false);

        if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
            distance = hitResult.getLocation().distanceToSqr(eyePos);
            double blockReach = 5;

            if (distance > blockReach * blockReach) {
                Vec3 pos = hitResult.getLocation();
                hitResult = BlockHitResult.miss(pos, Direction.getNearest(eyePos.x, eyePos.y, eyePos.z), new BlockPos(pos));
            }
        }

        Vec3 viewVec = player.getViewVector(1.0F);
        Vec3 toVec = eyePos.add(viewVec.x * entityReach, viewVec.y * entityReach, viewVec.z * entityReach);
        AABB aabb = entity.getBoundingBox().expandTowards(viewVec.scale(entityReach)).inflate(1.0D, 1.0D, 1.0D);
        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(player, eyePos, toVec, aabb, (p_234237_) -> !p_234237_.isSpectator(), distance);

        if (entityHitResult != null) {
            Entity targetEntity = entityHitResult.getEntity();
            Vec3 targetPos = entityHitResult.getLocation();
            double distanceToTarget = eyePos.distanceToSqr(targetPos);

            if (distanceToTarget > distance || distanceToTarget > entityReach * entityReach) {
                hitResult = BlockHitResult.miss(targetPos, Direction.getNearest(viewVec.x, viewVec.y, viewVec.z), new BlockPos(targetPos));
            } else if (distanceToTarget < distance) {
                hitResult = entityHitResult;
            }
        }

        if (hitResult.getType() == HitResult.Type.ENTITY) {
            return ((EntityHitResult) hitResult).getEntity();
        }

        return null;
    }

    public static boolean isProjectileMovingTowardsPlayer(Player player, Entity projectile) {
        Vec3 projectilePosition = projectile.position();
        Vec3 projectileMotion = projectile.getDeltaMovement();

        Vec3 directionToPlayer = player.position().subtract(projectilePosition).normalize();

        double dotProduct = projectileMotion.normalize().dot(directionToPlayer);

        return dotProduct > 0;
    }

    public static void shootDynamicLaser(ServerLevel world, Player player, int maxRange, int horizontalRadius, int verticalRadius) {
        Vec3 eyePosition = player.getEyePosition(1.0F); // Posição dos olhos do jogador
        Vec3 lookDirection = player.getLookAngle();    // Direção para onde o jogador está olhando

        for (int i = 0; i <= maxRange; i++) {
            // Calcula a posição do bloco na trajetória do laser
            Vec3 targetVec = eyePosition.add(lookDirection.scale(i));
            BlockPos targetPos = new BlockPos(targetVec);

            // Verifica se o bloco é ar; se for, ignora essa fileira
            if (world.getBlockState(targetPos).isAir()) {
                continue;
            }

            // Afeta os blocos ao redor do ponto atual
            affectSurroundingBlocks(world, targetPos, horizontalRadius, verticalRadius);
        }
    }

    private static void affectSurroundingBlocks(Level world, BlockPos center, int horizontalRadius, int verticalRadius) {
        int horizontalRadiusSphere = horizontalRadius - 1;
        int verticalRadiusSphere = verticalRadius - 1;

        for (int y = -verticalRadiusSphere; y <= verticalRadiusSphere; y++) {
            for (int x = -horizontalRadiusSphere; x <= horizontalRadiusSphere; x++) {
                for (int z = -horizontalRadiusSphere; z <= horizontalRadiusSphere; z++) {
                    // Calcula a distância ao centro para uma forma esférica
                    double distanceSq = (x * x) / (double) (horizontalRadiusSphere * horizontalRadiusSphere) +
                            (y * y) / (double) (verticalRadiusSphere * verticalRadiusSphere) +
                            (z * z) / (double) (horizontalRadiusSphere * horizontalRadiusSphere);

                    if (distanceSq <= 1.0) { // Dentro da área de efeito
                        BlockPos affectedPos = center.offset(x, y, z);
                        if (world.getBlockState(affectedPos).isAir()) {
                            break;
                        }
                        // Insira a lógica para afetar os blocos
                        affectBlock(world, affectedPos);
                    }
                }
            }
        }
    }

    private static void affectBlock(Level world, BlockPos pos) {
        // Exemplo de lógica personalizada para afetar blocos
        if (!world.getBlockState(pos).isAir()) {
            // Substituir bloco por vidro como exemplo
            world.setBlock(pos, Blocks.GLASS.defaultBlockState(), 3);

            // Adicionar partículas no bloco afetado
            world.addParticle(ParticleTypes.FLAME,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0.1, 0);
        }
    }


    @OnlyIn(Dist.CLIENT)
    public static class ModelFetcher {

        @OnlyIn(Dist.CLIENT)
        public static EntityModel<?> getModelOfEntity(Entity entity) {
            // Obtém o EntityRendererManager (gerenciador de renderizadores)
            EntityRenderer<?> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);

            // Verifica se o renderizador é para uma entidade viva (LivingEntity)
            if (renderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
                // Retorna o modelo da entidade
                return livingRenderer.getModel();
            }

            return null; // Retorna null se não for uma entidade viva com um modelo
        }

        @OnlyIn(Dist.CLIENT)
        public static AdvancedHumanoidModel<?> getChangedEntityModel(ChangedEntity entity) {
            // Obtém o EntityRendererManager (gerenciador de renderizadores)
            EntityRenderer<?> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);

            // Verifica se o renderizador é para uma entidade viva (LivingEntity)
            if (renderer instanceof AdvancedHumanoidRenderer<?, ?, ?> ChangedEntityModel) {
                // Retorna o modelo da entidade
                return ChangedEntityModel.getModel();
            }
            return null; // Retorna null se não for uma entidade viva com um modelo
        }

        @OnlyIn(Dist.CLIENT)
        public static AdvancedHumanoidModel<?> getChangedEntityArmorModel(ChangedEntity entity, boolean outerModel) {
            // Obtém o EntityRendererManager (gerenciador de renderizadores)
            EntityRenderer<?> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);

            // Verifica se o renderizador é para uma entidade viva (LivingEntity)
            if (renderer instanceof AdvancedHumanoidRenderer<?, ?, ?> ChangedEntityModel) {
                if (outerModel) {
                    // Retorna o modelo da entidade
                    return ChangedEntityModel.getArmorLayer().getArmorModel(EquipmentSlot.CHEST);
                }
                // Retorna o modelo da entidade
                return ChangedEntityModel.getArmorLayer().getArmorModel(EquipmentSlot.LEGS);
            }
            return null; // Retorna null se não for uma entidade viva com um modelo
        }

        @OnlyIn(Dist.CLIENT)
        public static AdvancedHumanoidRenderer<?, ?, ?> getChangedEntityRender(ChangedEntity entity) {
            EntityRenderer<?> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
            if (renderer instanceof AdvancedHumanoidRenderer<?, ?, ?> ChangedEntityModel) {
                return ChangedEntityModel;
            }
            return null;
        }
    }
}
