package net.foxyas.changed_additions.item.eventsHandle;

import net.ltxprogrammer.changed.block.AbstractLabDoor;
import net.ltxprogrammer.changed.block.AbstractLargeLabDoor;
import net.ltxprogrammer.changed.block.NineSection;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class PryOpenFeature {

    @SubscribeEvent
    public static void PryOpenDoor(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) {
            return;
        }

        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        ItemStack itemStack = event.getItemStack();
        BlockState state = event.getLevel().getBlockState(pos);
        if (itemStack.getItem() instanceof PickaxeItem) {
            if (state.getBlock() instanceof AbstractLabDoor labDoor) {
                if (!labDoor.isOpen(state, level, pos) && !labDoor.wantPowered(state, level, pos)) {
                    labDoor.openDoor(state, level, pos);
                }
            } else if (state.getBlock() instanceof AbstractLargeLabDoor labDoor) {
                if (!labDoor.isOpen(state, level, pos) && !labDoor.wantPowered(state, level, pos)) {
                    if (state.getValue(AbstractLargeLabDoor.SECTION) == NineSection.CENTER) {
                        labDoor.openDoor(state, level, pos);
                        itemStack.hurtAndBreak(4, player, (livingEntity) -> livingEntity.broadcastBreakEvent(event.getHand()));
                    }
                }
            }
        }
    }
}
