package net.foxyas.changed_additions.entities;

import net.foxyas.changed_additions.entities.defaults.AbstractBasicChangedEntity;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.GenderedEntity;
import net.ltxprogrammer.changed.entity.TransfurMode;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AbstractKitsuneEntity extends AbstractBasicChangedEntity implements GenderedEntity {
    public AbstractKitsuneEntity(EntityType<? extends ChangedEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public TransfurMode getTransfurMode() {
        return this.getRandom().nextBoolean() ? TransfurMode.ABSORPTION : TransfurMode.REPLICATION;
    }

    public Color3 getDripColor() {
        return Color3.WHITE;
    }
}
