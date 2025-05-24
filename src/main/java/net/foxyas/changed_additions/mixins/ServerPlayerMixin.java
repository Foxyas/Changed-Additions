package net.foxyas.changed_additions.mixins;

import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QTEManager;
import net.foxyas.changed_additions.process.quickTimeEvents.serverSide.QTEPendingManager;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QuickTimeEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Unique
    private static final String QTE_TAG = "QTE";

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void onSave(CompoundTag tag, CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        QuickTimeEvent qte = QTEManager.getActiveQTE(player);
        if (qte != null && !qte.isFinished()) {
            tag.put(QTE_TAG, qte.saveToTag());
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void onRead(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains(QTE_TAG)) {
            ServerPlayer player = (ServerPlayer) (Object) this;
            if (!player.getLevel().isClientSide()) {
                QuickTimeEvent qte = QuickTimeEvent.loadFromTag(player, tag.getCompound(QTE_TAG));
                QTEPendingManager.queueQTE(player, qte);
            }
        }
    }
}
