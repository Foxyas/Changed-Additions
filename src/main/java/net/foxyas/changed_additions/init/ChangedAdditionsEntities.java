package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.entities.FengQIWolf;
import net.foxyas.changed_additions.entities.LatexSnowFoxFemale;
import net.foxyas.changed_additions.entities.LatexSnowFoxMale;
import net.ltxprogrammer.changed.init.ChangedMobCategories;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ChangedAdditionsMod.MODID);
    public static final RegistryObject<EntityType<FengQIWolf>> FENG_QI_WOLF = register("feng_qi_wolf",
            EntityType.Builder.<FengQIWolf>of(FengQIWolf::new, ChangedMobCategories.CHANGED)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64).setUpdateInterval(3)
                    .setCustomClientFactory(FengQIWolf::new)
                    .sized(0.7f, 1.93f));

    public static final RegistryObject<EntityType<LatexSnowFoxMale>> LATEX_SNOW_FOX_MALE = register("latex_snow_fox_male",
            EntityType.Builder.<LatexSnowFoxMale>of(LatexSnowFoxMale::new, ChangedMobCategories.CHANGED)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64).setUpdateInterval(3)
                    .setCustomClientFactory(LatexSnowFoxMale::new)
                    .sized(0.7f, 1.93f));

    public static final RegistryObject<EntityType<LatexSnowFoxFemale>> LATEX_SNOW_FOX_FEMALE = register("latex_snow_fox_female",
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
            FengQIWolf.init();
            LatexSnowFoxMale.init();
            LatexSnowFoxFemale.init();
        });
    }

    @SubscribeEvent
    public static void initSpawnPlacements(SpawnPlacementRegisterEvent event) {
        LatexSnowFoxMale.setSpawnPlacement(event);
        LatexSnowFoxFemale.setSpawnPlacement(event);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(FENG_QI_WOLF.get(), FengQIWolf.createAttributes().build());
        event.put(LATEX_SNOW_FOX_MALE.get(), LatexSnowFoxMale.createAttributes().build());
        event.put(LATEX_SNOW_FOX_FEMALE.get(), LatexSnowFoxFemale.createAttributes().build());
    }
}
