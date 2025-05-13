package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.jei_recipes.NeofuserRecipe;
import net.foxyas.changed_additions.recipes.special.DyeableShortsColoringRecipe;
import net.foxyas.changed_additions.recipes.special.LaserPointerColoringRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ChangedAdditionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsModRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "changed_additions");

    public static final RegistryObject<RecipeSerializer<?>> LAZER_POINTER_COLORING =
            SERIALIZERS.register("laser_pointer_coloring", LaserPointerColoringRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> SHORTS_COLORING =
            SERIALIZERS.register("shorts_coloring", DyeableShortsColoringRecipe.Serializer::new);

    public static final RegistryObject<RecipeSerializer<NeofuserRecipe>> NEOFUSER_RECIPE_TYPE = SERIALIZERS.register("neofuser_recipe", () -> NeofuserRecipe.Serializer.INSTANCE);


    @SubscribeEvent
    public static void register(FMLConstructModEvent event) {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        event.enqueueWork(() -> SERIALIZERS.register(bus));
    }
}
