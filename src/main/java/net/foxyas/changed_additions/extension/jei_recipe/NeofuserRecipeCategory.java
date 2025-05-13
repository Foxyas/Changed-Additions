package net.foxyas.changed_additions.extension.jei_recipe;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.foxyas.changed_additions.init.ChangedAdditionsModBlocks;
import net.foxyas.changed_additions.init.ChangedAdditionsModJeiPlugin;
import net.foxyas.changed_additions.recipes.NeofuserRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Deprecated
public class NeofuserRecipeCategory implements IRecipeCategory<NeofuserRecipe> {
    public final static ResourceLocation UID = new ResourceLocation("changed_additions", "neofuser_recipe");
    public final static ResourceLocation TEXTURE = new ResourceLocation("changed_additions", "textures/screens/jei_neofuser_screen.png");
    private final IDrawable background;
    private final IDrawable icon;

    public NeofuserRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 116, 54);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ChangedAdditionsModBlocks.NEOFUSER.get().asItem()));
    }

    @Override
    public mezz.jei.api.recipe.RecipeType<NeofuserRecipe> getRecipeType() {
        return ChangedAdditionsModJeiPlugin.NeofuserRecipe_Type;
    }

    @Override
    public Component getTitle() {
        return new TextComponent("Neofuser Recipe");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Deprecated
    @Override
    public Class<? extends NeofuserRecipe> getRecipeClass() {
        return NeofuserRecipe.class;
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, NeofuserRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 36).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 37, 18).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 18).addItemStack(recipe.getResultItem());

        // Exibir o campo progress como um texto ou barra de progresso
        float progressSpeed = recipe.getProgressSpeed();
        builder.addSlot(RecipeIngredientRole.CATALYST, 64, 36).addItemStack(new ItemStack(Items.KNOWLEDGE_BOOK)) // Substitua por um item adequado
                .addTooltipCallback((recipeSlotView, tooltip) -> {
                    // Adiciona uma nova linha ao tooltip com o progresso da receita
                    tooltip.add(new TranslatableComponent("changed_additions.gui.recipe_progress", progressSpeed));
                });
    }
}
