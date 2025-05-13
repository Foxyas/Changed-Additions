package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.item.*;
import net.foxyas.changed_additions.item.armor.DyeableShorts;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ChangedAdditionsModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ChangedAdditionsMod.MODID);
    public static final RegistryObject<Item> BIOMASS = REGISTRY.register("biomass", BiomassItem::new);
    public static final RegistryObject<Item> ANTI_LATEX_BASE = REGISTRY.register("anti_latex_base", AntiLatexbaseItem::new);
    public static final RegistryObject<Item> AMMONIUM_CHLORIDE = REGISTRY.register("ammonium_chloride", AmmoniumchlorideItem::new);
    public static final RegistryObject<Item> LAETHIN_SYRINGE = REGISTRY.register("laethin_syringe", LaethinSyringeItem::new);
    public static final RegistryObject<Item> NEOFUSER = block(ChangedAdditionsModBlocks.NEOFUSER, ChangedAdditionsModTabs.CHANGED_ADDITIONS_TAB);
    public static final RegistryObject<Item> DYEABLE_SHORTS = REGISTRY.register("dyeable_shorts", DyeableShorts::new);
    public static final RegistryObject<Item> LASER_POINTER = REGISTRY.register("laser_pointer", LaserPointer::new);

    private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
        return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
}
