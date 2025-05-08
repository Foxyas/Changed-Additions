
package net.foxyas.changed_additions.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

import net.foxyas.changed_additions.init.ChangedAdditionsModTabs;

public class AntiLatexbaseItem extends Item {
	public AntiLatexbaseItem() {
		super(new Item.Properties().tab(ChangedAdditionsModTabs.TAB_CHANGED_ADDITIONS_TAB).stacksTo(64).rarity(Rarity.COMMON));
	}
}
