package net.foxyas.changed_additions.process.util;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public  class GlobalEntityUtil {
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