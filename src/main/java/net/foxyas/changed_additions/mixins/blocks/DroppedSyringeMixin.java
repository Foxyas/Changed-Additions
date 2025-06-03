package net.foxyas.changed_additions.mixins.blocks;

import net.foxyas.changed_additions.variants.ChangedAdditionsTransfurVariants;
import net.ltxprogrammer.changed.block.DroppedSyringe;
import net.ltxprogrammer.changed.block.entity.DroppedSyringeBlockEntity;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(value = DroppedSyringeBlockEntity.class)
public class DroppedSyringeMixin {

    @Shadow (remap = false)
    private TransfurVariant<?> variant;
    @Unique
    private boolean changed_Additions$allowAnyForm = false;

    @Inject(method = "getVariant", at = @At("RETURN"), cancellable = true, remap = false)
    private void checkAllowBossTag(CallbackInfoReturnable<TransfurVariant<?>> cir) {
        if (!changed_Additions$allowAnyForm) {
            if (ChangedAdditionsTransfurVariants.getRemovedVariantsList().contains(this.variant)) {
                List<TransfurVariant<?>> list = new ArrayList<>(TransfurVariant.getPublicTransfurVariants().toList());
                list.removeIf(transfurVariant -> ChangedAdditionsTransfurVariants.getRemovedVariantsList().contains(transfurVariant));
                TransfurVariant<?> transfurVariant = list.get(new Random().nextInt(list.size()));
                this.variant = transfurVariant;
                cir.setReturnValue(transfurVariant);
            }
        }
    }

    @Inject(method = "load", at = @At("HEAD"))
    private void getDataMod(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("allowAnyForm")) {
            this.changed_Additions$allowAnyForm = tag.getBoolean("allowAnyForm");
        }
    }

    @Inject(method = "saveAdditional", at = @At("HEAD"))
    private void AddDataMod(CompoundTag tag, CallbackInfo ci) {
        if (this.variant != null && ChangedAdditionsTransfurVariants.getRemovedVariantsList().contains(this.variant)) {
            tag.putBoolean("allowAnyForm", changed_Additions$allowAnyForm);
        }
    }

}
