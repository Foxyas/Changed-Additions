package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.entities.FengQIFox;
import net.foxyas.changed_additions.entities.LatexSnowFoxFemale;
import net.foxyas.changed_additions.entities.LatexSnowFoxMale;
import net.ltxprogrammer.changed.init.ChangedMobCategories;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, ChangedAdditionsMod.MODID);
	public static final RegistryObject<EntityType<FengQIFox>> FENG_QI_FOX = register("feng_qi_fox",
			EntityType.Builder.<FengQIFox>of(FengQIFox::new, ChangedMobCategories.CHANGED)
					.setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(FengQIFox::new)
					.sized(0.7f, 1.93f));

	public static final RegistryObject<EntityType<LatexSnowFoxMale>> LATEX_SNOW_FOX_MALE  = register("latex_snow_fox_male",
			EntityType.Builder.<LatexSnowFoxMale>of(LatexSnowFoxMale::new, ChangedMobCategories.CHANGED)
					.setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(LatexSnowFoxMale::new)
					.sized(0.7f, 1.93f));

	public static final RegistryObject<EntityType<LatexSnowFoxFemale>> LATEX_SNOW_FOX_FEMALE  = register("latex_snow_fox_female",
			EntityType.Builder.<LatexSnowFoxFemale>of(LatexSnowFoxFemale::new, ChangedMobCategories.CHANGED)
					.setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(LatexSnowFoxFemale::new)
					.sized(0.7f, 1.93f));

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			FengQIFox.init();
			LatexSnowFoxMale.init();
			LatexSnowFoxFemale.init();
		});
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(FENG_QI_FOX.get(), FengQIFox.createAttributes().build());
		event.put(LATEX_SNOW_FOX_MALE.get(), LatexSnowFoxMale.createAttributes().build());
		event.put(LATEX_SNOW_FOX_FEMALE.get(), LatexSnowFoxFemale.createAttributes().build());
	}
}
