package net.foxyas.changed_additions.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChangedAdditionsTabs {
    public static CreativeModeTab CHANGED_ADDITIONS_TAB;

    public static void load() {
        CHANGED_ADDITIONS_TAB = new CreativeModeTab("changed_additions_tab") {
            @Override
            public ItemStack makeIcon() {
                return new ItemStack(ChangedAdditionsBlocks.NEOFUSER.get());
            }

            @OnlyIn(Dist.CLIENT)
            public boolean hasSearchBar() {
                return false;
            }
        };
    }
}
