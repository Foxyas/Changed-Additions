package net.foxyas.changed_additions.entities;

import net.foxyas.changed_additions.init.ChangedAdditionsEntities;
import net.ltxprogrammer.changed.entity.*;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nullable;
import java.util.List;

import static net.ltxprogrammer.changed.entity.HairStyle.BALD;

public class LatexSnowFoxMale extends AbstractLatexSnowFox {
    public LatexSnowFoxMale(EntityType<? extends ChangedEntity> type, Level level) {
        super(type, level);
    }

    public LatexSnowFoxMale(PlayMessages.SpawnEntity packet, Level world) {
        this(ChangedAdditionsEntities.LATEX_SNOW_FOX_MALE.get(), world);
    }

    @Override
    public Color3 getHairColor(int i) {
        return Color3.getColor("#E5E5E5");
    }


    @Override
    public TransfurMode getTransfurMode() {
        if(level().random.nextInt(10) > 5){
            return TransfurMode.ABSORPTION;
        }
        return TransfurMode.REPLICATION;
    }

    @Override
    public HairStyle getDefaultHairStyle() {
        HairStyle Hair;
        if(level().random.nextInt(10) > 5){ Hair = HairStyle.SHORT_MESSY.get();
        } else {
            Hair = BALD.get();
        }
        return Hair;
    }

    @Override
    public @Nullable List<HairStyle> getValidHairStyles() {
        return HairStyle.Collection.MALE.getStyles();
    }

    public Color3 getDripColor() {
        Color3 color;
        if(level().random.nextInt(10) > 5){ color = Color3.getColor("#ffffff");
        } else {
            color = Color3.getColor("#e0e0e0");
        }
        return color;
    }

    public Color3 getTransfurColor(TransfurCause cause) {
        return Color3.getColor("#e0e0e0");
    }

    @Override
    public Gender getGender() {
        return Gender.MALE;
    }
}
