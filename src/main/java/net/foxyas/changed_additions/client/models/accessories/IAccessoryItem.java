package net.foxyas.changed_additions.client.models.accessories;

import net.foxyas.changed_additions.client.models.accessories.models.*;
import net.foxyas.changed_additions.variants.TransfurVariantTags;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.Gender;
import net.ltxprogrammer.changed.entity.beast.LatexStiger;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Wearable;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public interface IAccessoryItem extends Wearable {

    @Nullable
    default <E extends ChangedEntity> AdvancedHumanoidModel<E> getAccessoryModel(E entity, EquipmentSlot slot, Gender gender, EntityRendererProvider.Context context) {
        if (entity.getSelfVariant() != null && entity.getSelfVariant().is(TransfurVariantTags.WOLF_LIKE)) {
            if (gender == Gender.MALE) {
                return new AccessoriesMaleWolf<>(context.bakeLayer(AccessoriesMaleWolf.LAYER_LOCATION));
            } else if (gender == Gender.FEMALE) {
                return new AccessoriesFemaleWolf<>(context.bakeLayer(AccessoriesFemaleWolf.LAYER_LOCATION));
            }
        } else if (entity instanceof LatexStiger) {
            return new AccessoriesStiger<>(context.bakeLayer(AccessoriesStiger.LAYER_LOCATION));
        } else if (entity.getSelfVariant() != null && entity.getSelfVariant().is(TransfurVariantTags.FOX_LIKE)) {
            if (gender == Gender.MALE) {
                return new AccessoriesMaleFox<>(context.bakeLayer(AccessoriesMaleFox.LAYER_LOCATION));
            } else if (gender == Gender.FEMALE) {
                return new AccessoriesFemaleFox<>(context.bakeLayer(AccessoriesFemaleFox.LAYER_LOCATION));
            }
        }

        return null;
    }

    enum AccessoryType {
        NONE,
        WOLF_MALE,
        WOLF_FEMALE,
        STIGER,
        CAT_MALE,
        CAT_FEMALE
    }

    @Nullable
    ResourceLocation getAccessoryTexture(ChangedEntity entity, EquipmentSlot slot, Gender gender, EntityRendererProvider.Context context);

    @Nullable
    Color getRenderColor(ChangedEntity entity, EquipmentSlot slot);

}
