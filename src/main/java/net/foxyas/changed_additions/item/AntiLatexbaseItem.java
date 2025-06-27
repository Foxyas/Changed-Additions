package net.foxyas.changed_additions.item;

import net.foxyas.changed_additions.init.ChangedAdditionsTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class AntiLatexbaseItem extends Item {
    public AntiLatexbaseItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
}
