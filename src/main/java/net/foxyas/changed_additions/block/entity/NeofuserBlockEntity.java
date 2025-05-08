package net.foxyas.changed_additions.block.entity;

import net.foxyas.changed_additions.recipes.RecipesHandle;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.foxyas.changed_additions.world.inventory.NeofuserGuiMenu;
import net.foxyas.changed_additions.init.ChangedAdditionsModBlockEntities;

import javax.annotation.Nullable;

import java.util.stream.IntStream;

import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;
import org.checkerframework.checker.units.qual.Speed;

public class NeofuserBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer , BlockEntityWithTick {
	private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
	private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());
	private float recipeProgress = 0f;
	public static final float MAX_RECIPE_PROGRESS = 100f;


	public NeofuserBlockEntity(BlockPos position, BlockState state) {
		super(ChangedAdditionsModBlockEntities.NEOFUSER.get(), position, state);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		if (!this.tryLoadLootTable(compound))
			this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.stacks);
		this.recipeProgress = compound.getFloat("recipe_progress");
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		if (!this.trySaveLootTable(compound)) {
			ContainerHelper.saveAllItems(compound, this.stacks);
		}
		compound.putFloat("recipe_progress", this.recipeProgress);
	}

	@Override
	public CompoundTag getTileData() {
		return super.getTileData();
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	public float getRecipeProgress() {
		return this.recipeProgress;
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithFullMetadata();
	}

	@Override
	public int getContainerSize() {
		return stacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.stacks)
			if (!itemstack.isEmpty())
				return false;
		return true;
	}

	@Override
	public Component getDefaultName() {
		return new TextComponent("neofuser");
	}

	@Override
	public int getMaxStackSize() {
		return 64;
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return new NeofuserGuiMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition));
	}

	@Override
	public Component getDisplayName() {
		return new TextComponent("Neofuser");
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.stacks;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		if (index == 3)
			return false;
		return true;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return IntStream.range(0, this.getContainerSize()).toArray();
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
		return this.canPlaceItem(index, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		if (index == 0)
			return false;
		if (index == 1)
			return false;
		if (index == 2)
			return false;
		return true;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (!this.remove && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return handlers[facing.ordinal()].cast();
		return super.getCapability(capability, facing);
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		for (LazyOptional<? extends IItemHandler> handler : handlers)
			handler.invalidate();
	}

	private int tickCooldown = 0; // usado para o delay entre os avanços de progresso
	private static final int DELAY_TICKS = 5; // define o delay em ticks (5 ticks = 0.25s)

	@Override
	public void tick() {
		if (this.level == null || this.level.isClientSide()) return;

		this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 1);
		this.setChanged();

		ItemStack s0 = getItem(0);
		ItemStack s1 = getItem(1);
		ItemStack s2 = getItem(2);
		ItemStack currentOutput = getItem(3);

		ItemStack result = RecipesHandle.getNeofuserRecipeRecipeOutputOrDefault(this.level, s0, s1, s2);
		float speed = RecipesHandle.getNeofuserRecipeRecipeProgressSpeed(this.level, s0, s1, s2);

		// Delay entre ticks de progresso
		if (tickCooldown > 0) {
			tickCooldown--;
			return;
		}

		if (!result.isEmpty() && (currentOutput.isEmpty() || (ItemStack.isSameItemSameTags(result, currentOutput) && currentOutput.getCount() < currentOutput.getMaxStackSize()))) {
			this.recipeProgress += speed;

			if (this.recipeProgress >= MAX_RECIPE_PROGRESS) {
				s0.shrink(1);
				s1.shrink(1);
				s2.shrink(1);

				if (currentOutput.isEmpty()) {
					this.setItem(3, result.copy());
				} else {
					currentOutput.grow(result.getCount());
				}
				this.recipeProgress = 0f;
			}

			tickCooldown = DELAY_TICKS; // reinicia o cooldown

		} else {
			// Reduz lentamente se nada está sendo craftado
			if (this.recipeProgress > 0f)
				this.recipeProgress -= 1f;
			if (this.recipeProgress < 0f)
				this.recipeProgress = 0f;

			tickCooldown = 0; // sem cooldown quando não está processando
		}
	}
	public @NotNull BlockPos getPos() {
		return this.worldPosition;
	}
}
