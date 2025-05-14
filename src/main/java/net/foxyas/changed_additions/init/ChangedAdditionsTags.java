package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ChangedAdditionsTags {

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> HAVE_CLAWS = create("have_claws");
        public static final TagKey<EntityType<?>> NO_CLAWS = create("no_claws");

        public static final TagKey<EntityType<?>> FELINES_LIKE = create("feline_like");

        public EntityTypes() {
        }

        private static TagKey<EntityType<?>> create(String name) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, ChangedAdditionsMod.modResource(name));
        }
    }
}
