package net.foxyas.changed_additions.abilities;

import net.ltxprogrammer.changed.ability.AbstractAbility;
import net.ltxprogrammer.changed.ability.AbstractAbilityInstance;
import net.ltxprogrammer.changed.ability.IAbstractChangedEntity;
import net.minecraft.world.entity.player.Player;


public class CustomInteractionInstance extends AbstractAbilityInstance {

    public CustomInteractionInstance(AbstractAbility<?> ability, IAbstractChangedEntity entity){
        super(ability,entity);
    }

    @Override
    public boolean canUse() {
        return true;
    }


    @Override
    public AbstractAbility.UseType getUseType() {
        return AbstractAbility.UseType.INSTANT;
    }

    @Override
    public boolean canKeepUsing() {
        return true;
    }

    @Override
    public void startUsing() {
    }

    @Override
    public void tick() {

    }

    @Override
    public void stopUsing() {

    }

    public void onSelected() {
        if (entity.getEntity() instanceof Player player && ability.getSelectedDisplayText(this.entity) != null){
            player.displayClientMessage(ability.getSelectedDisplayText(this.entity),true);
        }
    }
}
