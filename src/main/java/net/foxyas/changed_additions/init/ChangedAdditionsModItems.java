
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.foxyas.changed_additions.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.BlockItem;

import net.foxyas.changed_additions.item.LaethinSyringeItem;
import net.foxyas.changed_additions.item.BiomassItem;
import net.foxyas.changed_additions.item.AntiLatexbaseItem;
import net.foxyas.changed_additions.item.AmmoniumchlorideItem;
import net.foxyas.changed_additions.ChangedAdditionsMod;

public class ChangedAdditionsModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ChangedAdditionsMod.MODID);
	public static final RegistryObject<Item> ANTI_LATEXBASE = REGISTRY.register("anti_latexbase", () -> new AntiLatexbaseItem());
	public static final RegistryObject<Item> NEOFUSER = block(ChangedAdditionsModBlocks.NEOFUSER, ChangedAdditionsModTabs.TAB_CHANGED_ADDITIONS_TAB);
	public static final RegistryObject<Item> AMMONIUM_CHLORIDE = REGISTRY.register("ammonium_chloride", () -> new AmmoniumchlorideItem());
	public static final RegistryObject<Item> LAETHIN_SYRINGE = REGISTRY.register("laethin_syringe", () -> new LaethinSyringeItem());
	public static final RegistryObject<Item> BIOMASS = REGISTRY.register("biomass", () -> new BiomassItem());

	private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}
}
