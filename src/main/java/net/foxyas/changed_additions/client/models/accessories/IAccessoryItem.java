package net.foxyas.changed_additions.client.models.accessories;

import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.Gender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Wearable;

import java.awt.*;

public interface IAccessoryItem extends Wearable {

    enum AccessoryType {
        NONE,
        WOLF_MALE,
        WOLF_FEMALE,
        STIGER,
        CAT_MALE,
        CAT_FEMALE
    }

    ResourceLocation getAccessoryTexture(ChangedEntity entity, EquipmentSlot slot, Gender gender);

    Color getColor(ChangedEntity entity, EquipmentSlot slot);

}
