package net.foxyas.changed_additions.init;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraft.world.item.crafting.RecipeSerializer;

import net.foxyas.changed_additions.jei_recipes.NeofuserRecipeRecipe;
import net.foxyas.changed_additions.ChangedAdditionsMod;

@Mod.EventBusSubscriber(modid = ChangedAdditionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsModRecipeTypes {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "changed_additions");

	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		event.enqueueWork(() -> {
			SERIALIZERS.register(bus);
			SERIALIZERS.register("neofuser_recipe", () -> NeofuserRecipeRecipe.Serializer.INSTANCE);
		});
	}
}
