package net.foxyas.changed_additions.init;

import com.mojang.serialization.Codec;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.world.biome.ChangedAdditionsSpawnBiomeModifier;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChangedAdditionsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedAdditionsBiomeModifierSerializers {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ChangedAdditionsMod.MODID);

    public static final RegistryObject<Codec<? extends BiomeModifier>> MOB_SPAWN_CODEC =
            BIOME_MODIFIERS.register("simple_mob_spawn", () -> ChangedAdditionsSpawnBiomeModifier.CODEC);


    private static void register(String name, Codec<? extends BiomeModifier> codec){
        BIOME_MODIFIERS.register(name, () -> codec);
    }
}
