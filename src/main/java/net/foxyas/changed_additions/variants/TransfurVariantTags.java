package net.foxyas.changed_additions.variants;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.init.ChangedRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class TransfurVariantTags {
    public static final TagKey<TransfurVariant<?>> WOLF_LIKE = create("wolf_like");
    public static final TagKey<TransfurVariant<?>> SHARK_LIKE = create("shark_like");
    public static final TagKey<TransfurVariant<?>> CAT_LIKE = create("cat_like");
    public static final TagKey<TransfurVariant<?>> LEOPARD_LIKE = create("leopard_like");
    public static final TagKey<TransfurVariant<?>> CAUSE_FREEZE_DMG = create("cause_freeze_dmg");

    public TransfurVariantTags() {
    }

    private static TagKey<TransfurVariant<?>> create(String name) {
        return TagKey.create(ChangedRegistry.TRANSFUR_VARIANT.get().getRegistryKey(),
                new ResourceLocation(ChangedAdditionsMod.MODID, name));
    }
}