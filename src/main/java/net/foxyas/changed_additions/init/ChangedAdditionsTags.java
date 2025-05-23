package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.init.ChangedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

public class ChangedAdditionsTags {

    public static class Blocks {

        public static final TagKey<Block> PASSABLE_BLOCKS = create("passable_blocks");
        public static final TagKey<Block> LAB_DOORS = create("lab_doors");
        public static final TagKey<Block> LAB_BIG_DOORS = create("lab_big_doors");

        private static TagKey<Block> create(String name) {
            return TagKey.create(Registry.BLOCK_REGISTRY, ChangedAdditionsMod.modResource(name));
        }
    }

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> HAVE_CLAWS = create("have_claws");
        public static final TagKey<EntityType<?>> NO_CLAWS = create("no_claws");
        public static final TagKey<EntityType<?>> FELINES_LIKE = create("feline_like");
        public static final TagKey<EntityType<?>> PATABLE_ENTITIES = create("patable_entities");
        public static final TagKey<EntityType<?>> DRAGONS_ENTITIES = create("dragons_entities");

        // Make this make the tags didn't need to load the entry successfully to fully load
        // { "id": "resource id ", "required": <boolean value> }

        public EntityTypes() {
        }

        private static TagKey<EntityType<?>> create(String name) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, ChangedAdditionsMod.modResource(name));
        }
    }

    public static class TransfurVariants {

        public static final TagKey<TransfurVariant<?>> LATEX_SOLVENT_IMMUNE = create("latex_solvent_immune");
        public TransfurVariants() {
        }

        private static TagKey<TransfurVariant<?>> create(String name) {
            return TagKey.create(ChangedRegistry.TRANSFUR_VARIANT.get().getRegistryKey(),
                    new ResourceLocation(ChangedAdditionsMod.MODID, name));
        }
    }
}
