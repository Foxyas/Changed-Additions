package net.foxyas.changed_additions.mobEffects;

import net.foxyas.changed_additions.init.ChangedAdditionsDamageSources;
import net.foxyas.changed_additions.init.ChangedAdditionsTags;
import net.ltxprogrammer.changed.init.ChangedTags;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class LatexSolventEffect extends MobEffect {
    public LatexSolventEffect() {
        super(MobEffectCategory.HARMFUL, Color3.WHITE.toInt());
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // Somente se a entidade for da tag LATEX
        if ((entity.getType()).is(ChangedTags.EntityTypes.LATEX)) {
            entity.hurt(ChangedAdditionsDamageSources.LATEX_SOLVENT, 1.0F + amplifier); // dano escalável
        } else if (entity instanceof Player player
                && ProcessTransfur.isPlayerLatex(player)
                && !ProcessTransfur.getPlayerTransfurVariant(player).getParent().is(ChangedAdditionsTags.TransfurVariants.LATEX_SOLVENT_IMMUNE)) {
            entity.hurt(ChangedAdditionsDamageSources.LATEX_SOLVENT, 1.0F + amplifier); // dano escalável
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 5 == 0; // a cada 0.25 segundos
    }
}
