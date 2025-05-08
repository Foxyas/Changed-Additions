
package net.foxyas.changed_additions.init;

import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;

import net.foxyas.changed_additions.jei_recipes.NeofuserRecipeRecipeCategory;
import net.foxyas.changed_additions.jei_recipes.NeofuserRecipeRecipe;

import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.IModPlugin;

import java.util.Objects;
import java.util.List;

@JeiPlugin
public class ChangedAdditionsModJeiPlugin implements IModPlugin {
	public static mezz.jei.api.recipe.RecipeType<NeofuserRecipeRecipe> NeofuserRecipe_Type = new mezz.jei.api.recipe.RecipeType<>(NeofuserRecipeRecipeCategory.UID, NeofuserRecipeRecipe.class);

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation("changed_additions:jei_plugin");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new NeofuserRecipeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
		List<NeofuserRecipeRecipe> NeofuserRecipeRecipes = recipeManager.getAllRecipesFor(NeofuserRecipeRecipe.Type.INSTANCE);
		registration.addRecipes(NeofuserRecipe_Type, NeofuserRecipeRecipes);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(ChangedAdditionsModBlocks.NEOFUSER.get().asItem()), NeofuserRecipe_Type);
	}
}
