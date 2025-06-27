package net.foxyas.changed_additions.abilities;

import net.foxyas.changed_additions.init.ChangedAdditionsAbilities;
import net.foxyas.changed_additions.init.ChangedAdditionsTags;
import net.ltxprogrammer.changed.ability.AbstractAbility;
import net.ltxprogrammer.changed.ability.IAbstractChangedEntity;
import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.init.ChangedAbilities;
import net.ltxprogrammer.changed.init.ChangedTags;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;
import java.util.List;

public class SoftenAbility extends AbstractAbility<SoftenAbilityInstance> {
    public SoftenAbility() {
        super(SoftenAbilityInstance::new);
    }

    @Override
    public Component getAbilityName(IAbstractChangedEntity entity) {
        return Component.translatable("changed_additions.ability.soften");
    }

    @Override
    public Collection<Component> getAbilityDescription(IAbstractChangedEntity entity) {
        Collection<Component> Description = List.of(Component.translatable("changed_additions.ability.soften.description"));
        return Description;
    }

    public ResourceLocation getTexture(IAbstractChangedEntity entity) {
        return ResourceLocation.parse("changed_additions:textures/abilities/gooey_paw.png");
    }

    @Override
    public UseType getUseType(IAbstractChangedEntity entity) {
        return UseType.HOLD;
    }

    @Override
    public boolean canUse(IAbstractChangedEntity entity) {
        return entity.getTransfurVariant() != null && entity.getTransfurVariant().getEntityType().is(ChangedTags.EntityTypes.LATEX);
    }

    @Override
    public void startUsing(IAbstractChangedEntity entity) {
        super.startUsing(entity);
    }

    @Override
    public void onRemove(IAbstractChangedEntity entity) {
        super.onRemove(entity);
    }


    public static boolean canPassThrough(Entity collidingEntity, BlockState state) {
        if (collidingEntity != null && state.is(ChangedAdditionsTags.Blocks.PASSABLE_BLOCKS)) {
            // Verifica se a entidade é um jogador
            if (collidingEntity instanceof Player player) {
                // Verifica uma condição específica do jogador (no caso, ProcessTransfur)
                if (ProcessTransfur.isPlayerLatex(player)) {
                    TransfurVariantInstance<?> transfurVariantInstance = ProcessTransfur.getPlayerTransfurVariant(player);
                    if ((transfurVariantInstance.getAbilityInstance(ChangedAbilities.GRAB_ENTITY_ABILITY.get()) == null) || (transfurVariantInstance.getAbilityInstance(ChangedAbilities.GRAB_ENTITY_ABILITY.get()) != null
                            && transfurVariantInstance.getAbilityInstance(ChangedAbilities.GRAB_ENTITY_ABILITY.get()).grabbedEntity == null)) {
                        SoftenAbilityInstance instance = transfurVariantInstance.getAbilityInstance(ChangedAdditionsAbilities.SOFTEN_ABILITY.get());
                        if (player.getItemBySlot(EquipmentSlot.HEAD).isEmpty()
                                && player.getItemBySlot(EquipmentSlot.CHEST).isEmpty()
                                && player.getItemBySlot(EquipmentSlot.LEGS).isEmpty()
                                && player.getItemBySlot(EquipmentSlot.FEET).isEmpty()){
                            return instance != null && instance.isActivate();
                        }
                    }
                }
            }
        }
        return false;
    }
}
