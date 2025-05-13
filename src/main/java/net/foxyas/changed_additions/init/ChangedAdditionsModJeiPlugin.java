package net.foxyas.changed_additions.init;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.foxyas.changed_additions.extension.info.JeiDescriptionHandler;
import net.foxyas.changed_additions.recipes.NeofuserRecipe;
import net.foxyas.changed_additions.extension.jei_recipe.NeofuserRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class ChangedAdditionsModJeiPlugin implements IModPlugin {
    public static mezz.jei.api.recipe.RecipeType<NeofuserRecipe> NeofuserRecipe_Type = new mezz.jei.api.recipe.RecipeType<>(NeofuserRecipeCategory.UID, NeofuserRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation("changed_additions:jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new NeofuserRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<NeofuserRecipe> neofuserRecipes = recipeManager.getAllRecipesFor(NeofuserRecipe.Type.INSTANCE);
        registration.addRecipes(NeofuserRecipe_Type, neofuserRecipes);

        JeiDescriptionHandler.registerDescriptions(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ChangedAdditionsBlocks.NEOFUSER.get().asItem()), NeofuserRecipe_Type);
    }
}
