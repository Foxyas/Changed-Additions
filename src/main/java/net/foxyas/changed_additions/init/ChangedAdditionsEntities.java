package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.entities.*;
import net.foxyas.changed_additions.entities.simple.LatexCalicoCatEntity;
import net.ltxprogrammer.changed.init.ChangedMobCategories;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, ChangedAdditionsMod.MODID);
	public static final RegistryObject<EntityType<FengQIWolf>> FENG_QI_WOLF = register("feng_qi_wolf",
			EntityType.Builder.<FengQIWolf>of(FengQIWolf::new, ChangedMobCategories.CHANGED)
					.setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(FengQIWolf::new)
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


	public static final RegistryObject<EntityType<LatexKitsuneMaleEntity>> LATEX_KITSUNE_MALE = register("latex_kitsune_male",
			EntityType.Builder.<LatexKitsuneMaleEntity>of(LatexKitsuneMaleEntity::new, ChangedMobCategories.CHANGED)
					.setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(LatexKitsuneMaleEntity::new)
					.clientTrackingRange(10)

					.sized(0.7f, 1.93f));

	public static final RegistryObject<EntityType<LatexKitsuneFemaleEntity>> LATEX_KITSUNE_FEMALE = register("latex_kitsune_female",
			EntityType.Builder.<LatexKitsuneFemaleEntity>of(LatexKitsuneFemaleEntity::new, ChangedMobCategories.CHANGED)
					.setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(LatexKitsuneFemaleEntity::new)
					.clientTrackingRange(10)

					.sized(0.7f, 1.93f));

	public static final RegistryObject<EntityType<LatexCalicoCatEntity>> LATEX_CALICO_CAT = register("latex_calico_cat",
			EntityType.Builder.<LatexCalicoCatEntity>of(LatexCalicoCatEntity::new, ChangedMobCategories.CHANGED)
					.setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(LatexCalicoCatEntity::new)
					.clientTrackingRange(10)

					.sized(0.7f, 1.93f));

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			FengQIWolf.init();
			LatexSnowFoxMale.init();
			LatexSnowFoxFemale.init();
			LatexKitsuneMaleEntity.init();
			LatexKitsuneFemaleEntity.init();
			LatexCalicoCatEntity.init();
		});
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(FENG_QI_WOLF.get(), FengQIWolf.createAttributes().build());
		event.put(LATEX_SNOW_FOX_MALE.get(), LatexSnowFoxMale.createAttributes().build());
		event.put(LATEX_SNOW_FOX_FEMALE.get(), LatexSnowFoxFemale.createAttributes().build());
		event.put(LATEX_KITSUNE_MALE.get(), LatexKitsuneMaleEntity.createAttributes().build());
		event.put(LATEX_KITSUNE_FEMALE.get(), LatexKitsuneFemaleEntity.createAttributes().build());
		event.put(LATEX_CALICO_CAT.get(), LatexCalicoCatEntity.createAttributes().build());
	}
}
