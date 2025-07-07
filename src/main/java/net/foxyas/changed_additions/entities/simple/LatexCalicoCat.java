package net.foxyas.changed_additions.entities.simple;

import net.foxyas.changed_additions.entities.defaults.AbstractBasicChangedEntity;
import net.foxyas.changed_additions.init.ChangedAdditionsEntities;
import net.ltxprogrammer.changed.entity.AttributePresets;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.TransfurMode;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;

public class LatexCalicoCat extends AbstractBasicChangedEntity {
    public LatexCalicoCat(EntityType<? extends ChangedEntity> type, Level level) {
        super(type, level);
    }

    public LatexCalicoCat(PlayMessages.SpawnEntity ignoredPacket, Level world) {
        this(ChangedAdditionsEntities.LATEX_CALICO_CAT.get(), world);
    }

    @Override
    protected void setAttributes(AttributeMap attributes) {
        AttributePresets.catLike(attributes);
    }

    @Override
    public TransfurMode getTransfurMode() {
        return TransfurMode.REPLICATION;
    }

    public Color3 getDripColor() {
        return this.random.nextBoolean() ? Color3.parseHex("#d67053") : Color3.parseHex("#67423f");
    }
}
