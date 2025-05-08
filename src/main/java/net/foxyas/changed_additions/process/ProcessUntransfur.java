package net.foxyas.changed_additions.process;

import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.world.entity.player.Player;

public class ProcessUntransfur {
    public static void UntransfurPlayer(Player player) {
        ProcessTransfur.ifPlayerTransfurred(player, (variant) -> {
            variant.unhookAll(player);
            ProcessTransfur.removePlayerTransfurVariant(player);
            ProcessTransfur.setPlayerTransfurProgress(player, 0.0F);
        });
    }
}
