package net.foxyas.changed_additions.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChangedAdditionsSounds {
    public static Map<ResourceLocation, SoundEvent> REGISTRY = new HashMap<>();
    public static final SoundEvent UNTRANSFUR = register("changed_additions:player.untransfur");
    public static final SoundEvent ARMOR_EQUIP = register("changed_additions:armor.equip");
    private static IForgeRegistry<SoundEvent> MINECRAFT_REGISTRY;

    public static SoundEvent register(String name) {
        ResourceLocation location = new ResourceLocation(name);
        SoundEvent soundEvent = new SoundEvent(location);
        REGISTRY.put(location, soundEvent);
        return soundEvent;
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        Iterator<Map.Entry<ResourceLocation, SoundEvent>> var1 = REGISTRY.entrySet().iterator();

        while (var1.hasNext()) {
            Map.Entry<ResourceLocation, SoundEvent> sound = var1.next();
            event.getRegistry().register((sound.getValue()).setRegistryName(sound.getKey()));
        }

        MINECRAFT_REGISTRY = event.getRegistry();
    }
}
