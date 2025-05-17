package net.foxyas.changed_additions.variants;

import net.foxyas.changed_additions.process.util.FoxyasUtils;
import net.ltxprogrammer.changed.init.ChangedTags;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TransfurEvents {

    @SubscribeEvent
    public static void AddAdvancement(ProcessTransfur.EntityVariantAssigned.ChangedVariant changedVariant) {
        if (changedVariant.newVariant != null && !changedVariant.newVariant.getEntityType().is(ChangedTags.EntityTypes.LATEX)) {
            if (changedVariant.livingEntity instanceof Player player){
                FoxyasUtils.grandPlayerAdvancement(player, "changed_additions:organic_transfur");
            }
        }
    }
}