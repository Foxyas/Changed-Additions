package net.foxyas.changed_additions.mixins.goals;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.entities.goals.FollowAndLookAtLaser;
import net.foxyas.changed_additions.entities.goals.SleepingNearOwnerGoal;
import net.foxyas.changed_additions.variants.TransfurVariantTags;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.beast.AbstractDarkLatexWolf;
import net.ltxprogrammer.changed.entity.beast.DarkLatexWolfPup;
import net.ltxprogrammer.changed.init.ChangedRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChangedEntity.class)
public class ChangedEntityGoalsMixin {

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void addExtraGoal(CallbackInfo ci) {
        ChangedEntity thisFixed = ((ChangedEntity) (Object) this);
        if (thisFixed instanceof AbstractDarkLatexWolf) {
            thisFixed.goalSelector.addGoal(5, new SleepingNearOwnerGoal.BipedSleepGoal(thisFixed, true, SleepingNearOwnerGoal.BipedSleepGoal.BedSearchType.NEAREST));
        } else if (thisFixed instanceof DarkLatexWolfPup) {
            thisFixed.goalSelector.addGoal(5, new SleepingNearOwnerGoal(thisFixed, true));
        }
        if (thisFixed.getSelfVariant() != null
                && (thisFixed.getSelfVariant().is(TransfurVariantTags.CAT_LIKE)
                || thisFixed.getSelfVariant().is(TransfurVariantTags.LEOPARD_LIKE)
                || thisFixed.getSelfVariant().is(TagKey.create(ChangedRegistry.TRANSFUR_VARIANT.get().getRegistryKey(), new ResourceLocation("changed_additions", "leopard_like")))
                || thisFixed.getSelfVariant().is(TagKey.create(ChangedRegistry.TRANSFUR_VARIANT.get().getRegistryKey(), new ResourceLocation("changed_additions", "cat_like"))))
        ) {
            thisFixed.goalSelector.addGoal(5, new FollowAndLookAtLaser(thisFixed, 0.4));
        }
    }
}
