
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.foxyas.changed_additions.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

import net.foxyas.changed_additions.block.entity.NeofuserBlockEntity;
import net.foxyas.changed_additions.ChangedAdditionsMod;

public class ChangedAdditionsModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ChangedAdditionsMod.MODID);
	public static final RegistryObject<BlockEntityType<?>> NEOFUSER = register("neofuser", ChangedAdditionsModBlocks.NEOFUSER, NeofuserBlockEntity::new);

	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
}
