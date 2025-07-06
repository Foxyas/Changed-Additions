package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.item.*;
import net.foxyas.changed_additions.item.armor.DarkLatexCoatItem;
import net.foxyas.changed_additions.item.armor.DyeableShorts;
import net.foxyas.changed_additions.item.armor.TShirtClothing;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ChangedAdditionsItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ChangedAdditionsMod.MODID);
    public static final RegistryObject<Item> BIOMASS = REGISTRY.register("biomass", BiomassItem::new);
    public static final RegistryObject<Item> ANTI_LATEX_BASE = REGISTRY.register("anti_latex_base", AntiLatexbaseItem::new);
    public static final RegistryObject<Item> AMMONIUM_CHLORIDE = REGISTRY.register("ammonium_chloride", AmmoniumchlorideItem::new);
    public static final RegistryObject<Item> LAETHIN_SYRINGE = REGISTRY.register("laethin_syringe", LaethinSyringeItem::new);
    public static final RegistryObject<Item> NEOFUSER = block(ChangedAdditionsBlocks.NEOFUSER, ChangedAdditionsTabs.CHANGED_ADDITIONS_TAB);

    public static final RegistryObject<Item> GOLDEN_ORANGE = REGISTRY.register("golden_orange", GoldenOrange::new);

    public static final RegistryObject<Item> DYEABLE_SHORTS = REGISTRY.register("dyeable_shorts", DyeableShorts::new);
    public static final RegistryObject<Item> DYEABLE_SHIRT = REGISTRY.register("dyeable_shirt", TShirtClothing::new);
    public static final RegistryObject<Item> LASER_POINTER = REGISTRY.register("laser_pointer", LaserPointer::new);

    public static final RegistryObject<Item> LATEX_SNOW_FOX_SPAWN_EGG = REGISTRY.register("latex_snow_fox_male_spawn_egg", () -> new ForgeSpawnEggItem(ChangedAdditionsEntities.LATEX_SNOW_FOX_MALE, 0xFFFFFFF, 0xfD6DDF7, new Item.Properties().tab(ChangedAdditionsTabs.CHANGED_ADDITIONS_TAB)));
    public static final RegistryObject<Item> LATEX_SNOW_FOX_FEMALE_SPAWN_EGG = REGISTRY.register("latex_snow_fox_female_spawn_egg", () -> new ForgeSpawnEggItem(ChangedAdditionsEntities.LATEX_SNOW_FOX_FEMALE, 0xFFFFFFF, 0xfD6DDF7, new Item.Properties().tab(ChangedAdditionsTabs.CHANGED_ADDITIONS_TAB)));
    public static final RegistryObject<Item> FENG_QI_WOLF_SPAWN_EGG = REGISTRY.register("feng_qi_wolf_spawn_egg", () -> new ForgeSpawnEggItem(ChangedAdditionsEntities.FENG_QI_WOLF, Color3.getColor("#93c6fd").toInt(), Color3.getColor("#FAC576").toInt(), new Item.Properties().tab(ChangedAdditionsTabs.CHANGED_ADDITIONS_TAB)));
    public static final RegistryObject<Item> LATEX_KITSUNE_MALE_SPAWN_EGG = REGISTRY.register("latex_kitsune_male_spawn_egg", () -> new ForgeSpawnEggItem(ChangedAdditionsEntities.LATEX_KITSUNE_MALE, 0xffeeee, 0xfff6f6, new Item.Properties().tab(ChangedAdditionsTabs.CHANGED_ADDITIONS_TAB)));
    public static final RegistryObject<Item> LATEX_KITSUNE_FEMALE_SPAWN_EGG = REGISTRY.register("latex_kitsune_female_spawn_egg", () -> new ForgeSpawnEggItem(ChangedAdditionsEntities.LATEX_KITSUNE_FEMALE, 0xffeeee, 0xfff6f6, new Item.Properties().tab(ChangedAdditionsTabs.CHANGED_ADDITIONS_TAB)));
    public static final RegistryObject<Item> LATEX_CALICO_CAT_MALE_SPAWN_EGG = REGISTRY.register("latex_calico_cat_spawn_egg", () -> new ForgeSpawnEggItem(ChangedAdditionsEntities.LATEX_CALICO_CAT, 0xffece4, 0xd56f53, new Item.Properties().tab(ChangedAdditionsTabs.CHANGED_ADDITIONS_TAB)));


    public static final RegistryObject<Item> DARK_LATEX_COAT = REGISTRY.register("dark_latex_coat",
            () -> new DarkLatexCoatItem(EquipmentSlot.CHEST, new Item.Properties().tab(ChangedAdditionsTabs.CHANGED_ADDITIONS_TAB)));

    public static final RegistryObject<Item> DARK_LATEX_HEAD_CAP = REGISTRY.register("dark_latex_coat_cap",
            () -> new DarkLatexCoatItem.HeadPart(EquipmentSlot.HEAD, new Item.Properties().tab(ChangedAdditionsTabs.CHANGED_ADDITIONS_TAB)));

    private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
        return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
}
