package net.foxyas.changed_additions.entities;

import net.foxyas.changed_additions.init.ChangedAdditionsEntities;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.Gender;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;

public class LatexKitsuneFemaleEntity extends AbstractKitsuneEntity {
    public LatexKitsuneFemaleEntity(EntityType<? extends ChangedEntity> type, Level level) {
        super(type, level);
    }

    public LatexKitsuneFemaleEntity(PlayMessages.SpawnEntity ignoredPacket, Level world) {
        this(ChangedAdditionsEntities.LATEX_KITSUNE_FEMALE.get(), world);
    }

    @Override
    public Gender getGender() {
        return Gender.FEMALE;
    }
}
