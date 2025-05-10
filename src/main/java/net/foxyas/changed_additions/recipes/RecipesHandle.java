package net.foxyas.changed_additions.recipes;

import net.foxyas.changed_additions.jei_recipes.NeofuserRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.LevelAccessor;

import java.util.List;

public class RecipesHandle {

    public static boolean CheckNeofuserRecipeRecipe(LevelAccessor level, ItemStack input1, ItemStack input2, ItemStack input3) {
        if (level instanceof ServerLevel serverLevel) {
            RecipeManager recipeManager = serverLevel.getRecipeManager();

            // Obtém todas as receitas do tipo JeiNeofuserRecipeRecipe
            List<NeofuserRecipe> neofuserRecipes = recipeManager.getAllRecipesFor(NeofuserRecipe.Type.INSTANCE);

            // Cria um contêiner simples com os inputs fornecidos
            SimpleContainer container = new SimpleContainer(3);
            container.setItem(0, input1);
            container.setItem(1, input2);
            container.setItem(2, input3);

            // Verifica cada receita para ver se ela corresponde aos inputs fornecidos
            for (NeofuserRecipe recipe : neofuserRecipes) {
                if (recipe.matches(container, serverLevel)) {
                    return true; // Receita correspondente encontrada
                }
            }
        }
        return false; // Nenhuma receita correspondente encontrada
    }

    public static ItemStack getNeofuserRecipeRecipeOutputOrDefault(LevelAccessor level, ItemStack input1, ItemStack input2, ItemStack input3) {
        if (level instanceof ServerLevel serverLevel) {
            RecipeManager recipeManager = serverLevel.getRecipeManager();

            // Obtém todas as receitas do tipo JeiNeofuserRecipeRecipe
            List<NeofuserRecipe> neofuserRecipes = recipeManager.getAllRecipesFor(NeofuserRecipe.Type.INSTANCE);

            // Cria um contêiner simples com os inputs fornecidos
            SimpleContainer container = new SimpleContainer(3);
            container.setItem(0, input1);
            container.setItem(1, input2);
            container.setItem(2, input3);

            // Verifica cada receita para ver se ela corresponde aos inputs fornecidos
            for (NeofuserRecipe recipe : neofuserRecipes) {
                NonNullList<Ingredient> ingredients = recipe.getIngredients();
                if (!ingredients.get(0).test(input1))
                    continue;
                if (!ingredients.get(1).test(input2))
                    continue;
                if (!ingredients.get(2).test(input3))
                    continue;
                return recipe.getResultItem(); // Retorna o ItemStack do resultado
            }
        }
        return ItemStack.EMPTY; // Retorna um ItemStack vazio se nenhuma receita correspondente for encontrada
    }

    public static float getNeofuserRecipeRecipeProgressSpeed(LevelAccessor level, ItemStack input1, ItemStack input2, ItemStack input3) {
        if (level instanceof ServerLevel serverLevel) {
            RecipeManager recipeManager = serverLevel.getRecipeManager();

            // Obtém todas as receitas do tipo JeiNeofuserRecipeRecipe
            List<NeofuserRecipe> neofuserRecipes = recipeManager.getAllRecipesFor(NeofuserRecipe.Type.INSTANCE);

            // Cria um contêiner simples com os inputs fornecidos
            SimpleContainer container = new SimpleContainer(3);
            container.setItem(0, input1);
            container.setItem(1, input2);
            container.setItem(2, input3);

            // Verifica cada receita para ver se ela corresponde aos inputs fornecidos
            for (NeofuserRecipe recipe : neofuserRecipes) {
                NonNullList<Ingredient> ingredients = recipe.getIngredients();
                if (!ingredients.get(0).test(input1))
                    continue;
                if (!ingredients.get(1).test(input2))
                    continue;
                if (!ingredients.get(2).test(input3))
                    continue;
                return recipe.getProgressSpeed(); // Retorna o ItemStack do resultado
            }
        }
        return 1f; // Retorna um ItemStack vazio se nenhuma receita correspondente for encontrada
    }

}
