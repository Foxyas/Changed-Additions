package net.foxyas.changed_additions.process.util;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GlobalEntityUtil {

    /**
     * Checks if one entity (eyeEntity) can see another (targetToSee), using raycasting and FOV.
     * @param eyeEntity The entity doing the looking.
     * @param targetToSee The target entity being looked at.
     * @param fovDegrees Field of view angle in degrees (e.g., 90 means 45 degrees to each side).
     * @return true if visible and within FOV, false otherwise.
     */
    public static boolean canEntitySeeOther(LivingEntity eyeEntity, LivingEntity targetToSee, double fovDegrees) {
        Level level = eyeEntity.level();
        if (level != targetToSee.level()) return false;

        Vec3 from = eyeEntity.getEyePosition(1.0F);
        Vec3 to = targetToSee.getEyePosition(1.0F);

        // First, check field of view using dot product
        Vec3 lookVec = eyeEntity.getLookAngle().normalize();
        Vec3 directionToTarget = to.subtract(from).normalize();

        double dot = lookVec.dot(directionToTarget);
        double requiredDot = Math.cos(Math.toRadians(fovDegrees / 2.0));
        if (dot < requiredDot)
            return false; // Outside of FOV

        // Then, raycast from eyeEntity to targetToSee to check if the view is blocked
        HitResult result = level.clip(new ClipContext(
                from, to, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, eyeEntity
        ));

        // If result is MISS or hit point is very close to target, it's considered visible
        return result.getType() == HitResult.Type.MISS ||
                result.getLocation().distanceToSqr(to) < 1.0;
    }

    /**
     * Verifica se eyeEntity consegue ver targetToSee com base na linha de visão.
     * @param eyeEntity A entidade que está observando.
     * @param targetToSee A entidade que deve ser visível.
     * @return true se for visível, false se houver obstrução.
     */
    public static boolean canEntitySeeOther(LivingEntity eyeEntity, LivingEntity targetToSee) {
        Level level = eyeEntity.level();
        if (level != targetToSee.level()) return false;

        Vec3 from = eyeEntity.getEyePosition(1.0F);
        Vec3 to = targetToSee.getEyePosition(1.0F);

        HitResult result = level.clip(new ClipContext(
                from, to, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, eyeEntity
        ));

        // Se o hit for MISS (sem blocos no caminho), ou o bloco atingido estiver além da entidade-alvo
        return result.getType() == HitResult.Type.MISS || result.getLocation().distanceToSqr(to) < 1.0;
    }

    @Nullable
    public static Entity getEntityByUUID(LevelAccessor world, String uuid) {
        try {
            Stream<Entity> entities;

            if (world instanceof ServerLevel serverLevel) {
                entities = StreamSupport.stream(serverLevel.getAllEntities().spliterator(), false);
            } else if (world instanceof ClientLevel clientLevel) {
                entities = StreamSupport.stream(clientLevel.entitiesForRendering().spliterator(), false);
            } else {
                return null;
            }

            return entities.filter(entity -> entity.getStringUUID().equals(uuid)).findFirst().orElse(null);
        } catch (Exception e) {
            ChangedAdditionsMod.LOGGER.error(e.getMessage()); // Log the exception for debugging purposes
            return null;
        }
    }


    @Nullable
    public static Entity getEntityByUUID(ServerLevel serverLevel, String uuid) {
        try {
            Stream<Entity> entities;
            entities = StreamSupport.stream(serverLevel.getAllEntities().spliterator(), false);
            return entities.filter(entity -> entity.getStringUUID().equals(uuid)).findFirst().orElse(null);
        } catch (Exception e) {
            ChangedAdditionsMod.LOGGER.error(e.getMessage()); // Log the exception for debugging purposes
            return null;
        }
    }

    @Nullable
    public static Entity getEntityByName(LevelAccessor world, String name) {
        try {
            Stream<Entity> entities;

            if (world instanceof ClientLevel clientLevel) {
                entities = StreamSupport.stream(clientLevel.entitiesForRendering().spliterator(), false);
            } else if (world instanceof ServerLevel serverLevel) {
                entities = StreamSupport.stream(serverLevel.getAllEntities().spliterator(), false);
            } else {
                return null;
            }

            return entities
                    .filter(entity -> {
                        String entityName = entity.getName().getString();
                        return entityName.equalsIgnoreCase(name);
                    })
                    .findFirst()
                    .orElse(null);

        } catch (Exception e) {
            ChangedAdditionsMod.LOGGER.error("Error getting entity by name: " + e.getMessage());
            return null;
        }
    }

}