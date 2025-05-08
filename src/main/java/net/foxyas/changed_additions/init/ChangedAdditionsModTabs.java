
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.foxyas.changed_additions.init;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;

public class ChangedAdditionsModTabs {
	public static CreativeModeTab TAB_CHANGED_ADDITIONS_TAB;

	public static void load() {
		TAB_CHANGED_ADDITIONS_TAB = new CreativeModeTab("tabchanged_additions_tab") {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(ChangedAdditionsModBlocks.NEOFUSER.get());
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}
}
