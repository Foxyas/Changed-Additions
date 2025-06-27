package net.foxyas.changed_additions.recipes.special;

import com.google.gson.JsonObject;
import net.foxyas.changed_additions.init.ChangedAdditionsRecipeTypes;
import net.foxyas.changed_additions.item.armor.DyeableShorts;
import net.ltxprogrammer.changed.item.BenignShorts;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import static net.foxyas.changed_additions.init.ChangedAdditionsItems.DYEABLE_SHORTS;

public class DyeableShortsColoringRecipe extends CustomRecipe {
    public DyeableShortsColoringRecipe(ResourceLocation id) {
        super(id, CraftingBookCategory.MISC);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        boolean hasShorts = false;
        boolean hasDye = false;

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();
                if (item == DYEABLE_SHORTS.get() || item instanceof DyeableShorts || item instanceof BenignShorts) {
                    if (hasShorts) return false;
                    hasShorts = true;
                } else if (item instanceof DyeItem) {
                    hasDye = true;
                } else {
                    return false;
                }
            }
        }

        return hasShorts && hasDye;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack pants = ItemStack.EMPTY;

        int totalR = 0;
        int totalG = 0;
        int totalB = 0;
        int dyeCount = 0;

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();
                if (item == DYEABLE_SHORTS.get() || item instanceof DyeableShorts || item instanceof BenignShorts) {
                    pants = stack;
                } else if (item instanceof DyeItem dyeItem) {
                    int color = dyeItem.getDyeColor().getTextColor(); // 0xRRGGBB
                    totalR += (color >> 16) & 0xFF;
                    totalG += (color >> 8) & 0xFF;
                    totalB += color & 0xFF;
                    dyeCount++;
                }
            }
        }

        if (!pants.isEmpty() && dyeCount > 0) {
            int r = totalR / dyeCount;
            int g = totalG / dyeCount;
            int b = totalB / dyeCount;
            int finalColor = (r << 16) | (g << 8) | b;

            ItemStack result = pants.copy();
            result.setCount(1);
            if (result.getItem() instanceof DyeableLeatherItem dyeableLeatherItem) {
                dyeableLeatherItem.setColor(result, finalColor);
            } else if (result.getItem() instanceof BenignShorts) {
                ItemStack backUp = result;
                result = new ItemStack(DYEABLE_SHORTS.get(), backUp.getCount());
                result.setTag(backUp.getTag());
                if (result.getItem() instanceof DyeableLeatherItem dyeableLeatherItem) {
                	dyeableLeatherItem.setColor(result, finalColor);
            	}
            }
            return result;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ChangedAdditionsRecipeTypes.SHORTS_COLORING.get();
    }

    public static class Serializer implements RecipeSerializer<DyeableShortsColoringRecipe> {

        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("changed_additions", "shorts_coloring");

        @Override
        public DyeableShortsColoringRecipe fromJson(ResourceLocation id, JsonObject json) {
            // Nenhum dado necessário no JSON
            return new DyeableShortsColoringRecipe(id);
        }

        @Override
        public DyeableShortsColoringRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            // Nenhum dado transmitido, então só retorna a instância
            return new DyeableShortsColoringRecipe(id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, DyeableShortsColoringRecipe recipe) {
            // Nada para escrever
        }
    }

}
