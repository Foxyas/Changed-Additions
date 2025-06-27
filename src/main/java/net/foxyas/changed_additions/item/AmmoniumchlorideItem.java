package net.foxyas.changed_additions.item;

import net.foxyas.changed_additions.init.ChangedAdditionsTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class AmmoniumchlorideItem extends Item {
    public AmmoniumchlorideItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
}
