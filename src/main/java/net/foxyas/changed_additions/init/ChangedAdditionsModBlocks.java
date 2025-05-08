
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.foxyas.changed_additions.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import net.foxyas.changed_additions.block.NeofuserBlock;
import net.foxyas.changed_additions.ChangedAdditionsMod;

public class ChangedAdditionsModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, ChangedAdditionsMod.MODID);
	public static final RegistryObject<Block> NEOFUSER = REGISTRY.register("neofuser", () -> new NeofuserBlock());
}
