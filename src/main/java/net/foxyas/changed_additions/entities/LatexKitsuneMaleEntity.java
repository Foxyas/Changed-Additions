package net.foxyas.changed_additions.entities;

import net.foxyas.changed_additions.init.ChangedAdditionsEntities;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.Gender;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;

public class LatexKitsuneMaleEntity extends AbstractKitsuneEntity {
    public LatexKitsuneMaleEntity(EntityType<? extends ChangedEntity> type, Level level) {
        super(type, level);
    }

    public LatexKitsuneMaleEntity(PlayMessages.SpawnEntity ignoredPacket, Level world) {
        this(ChangedAdditionsEntities.LATEX_KITSUNE_MALE.get(), world);
    }

    @Override
    public Gender getGender() {
        return Gender.MALE;
    }
}
