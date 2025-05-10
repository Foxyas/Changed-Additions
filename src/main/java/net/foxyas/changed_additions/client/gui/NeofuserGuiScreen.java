
package net.foxyas.changed_additions.client.gui;

import net.foxyas.changed_additions.block.entity.NeofuserBlockEntity;
import net.foxyas.changed_additions.init.ChangedAdditionsModBlockEntities;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.Minecraft;

import net.foxyas.changed_additions.world.inventory.NeofuserGuiMenu;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NeofuserGuiScreen extends AbstractContainerScreen<NeofuserGuiMenu> {
	private final static HashMap<String, Object> guistate = NeofuserGuiMenu.guistate;

	private final NeofuserGuiMenu container;
	private final Level world;
	private final int x, y, z;
	private final Player entity;

	public NeofuserGuiScreen(NeofuserGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.container = container;
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 200;
		this.imageHeight = 187;
	}

	private static final ResourceLocation texture = new ResourceLocation("changed_additions:textures/screens/neofuser_gui.png");

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);
	}

	@Override
	protected void renderBg(PoseStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShaderTexture(0, texture);
		blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		double math = 0;
		float progress = getValue(this.world,new BlockPos(x,y,z)) / 3.57f;

		int progressint = (int) progress;

        RenderSystem.setShaderTexture(0, new ResourceLocation("changed_additions:textures/screens/empty_bar.png"));
        blit(ms, this.leftPos + 84, this.topPos + 59, 0, 0, 32, 12, 32, 12);
		
        RenderSystem.setShaderTexture(0, new ResourceLocation("changed_additions:textures/screens/bar_full.png"));
        blit(ms, this.leftPos + 84+2, this.topPos + 59+2, 0, 0, progressint, 8, progressint, 8);
		

        RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
	}

	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		float progress = getValue(this.world,new BlockPos(x,y,z)) / NeofuserBlockEntity.MAX_RECIPE_PROGRESS;

		this.font.draw(poseStack, this.container.getDisplayName(), 9, 10, -12829636);
		this.font.draw(poseStack, new TextComponent((progress * 100) + "%"), 89, 47, -12829636);
	}

	public static float getValue(LevelAccessor world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof NeofuserBlockEntity neofuserBlockEntity) {
			return neofuserBlockEntity.getRecipeProgress();
		}
		return -1;
	}

	@Override
	public void onClose() {
		super.onClose();
		Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
	}

	@Override
	public void init() {
		super.init();
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
	}
}
