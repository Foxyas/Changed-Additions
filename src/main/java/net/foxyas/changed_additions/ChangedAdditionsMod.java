package net.foxyas.changed_additions;

import net.foxyas.changed_additions.client.renderer.item.LaserItemDynamicRender;
import net.foxyas.changed_additions.client.renderer.item.StyleItemPredicate;
import net.foxyas.changed_additions.init.*;
import net.foxyas.changed_additions.item.armor.TShirtClothing;
import net.foxyas.changed_additions.variants.ChangedAdditionsTransfurVariants;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.foxyas.changed_additions.init.ChangedAdditionsItems.DYEABLE_SHIRT;
import static net.foxyas.changed_additions.init.ChangedAdditionsItems.LASER_POINTER;

@Mod("changed_additions")
public class ChangedAdditionsMod {
    public static final Logger LOGGER = LogManager.getLogger(ChangedAdditionsMod.class);
    public static final String MODID = "changed_additions";
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(ResourceLocation.fromNamespaceAndPath(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID = 0;

    public ChangedAdditionsMod() {
        @SuppressWarnings("removal")
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ChangedAdditionsTabs.REGISTRY.register(bus);
        ChangedAdditionsEnchantments.REGISTRY.register(bus);
        ChangedAdditionsMobEffects.REGISTRY.register(bus);
        ChangedAdditionsBlocks.REGISTRY.register(bus);
        ChangedAdditionsItems.REGISTRY.register(bus);
        ChangedAdditionsEntities.REGISTRY.register(bus);
        ChangedAdditionsTransfurVariants.REGISTRY.register(bus);
        ChangedAdditionsAbilities.REGISTRY.register(bus);
        ChangedAdditionsMenus.REGISTRY.register(bus);
        ChangedAdditionsSounds.SOUNDS.register(bus);
        ChangedAdditionsParticles.REGISTRY.register(bus);

        ChangedAdditionsBlockEntities.REGISTRY.register(bus);

        bus.addListener(this::clientLoad); // Client Stuff goes Here
    }

    public static <T extends Event> boolean postModEvent(T event) {
        return MinecraftForge.EVENT_BUS.post(event);
    }

    public static ResourceLocation modResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static ModelLayerLocation modModelLayerLocation(String path, String type) {
        return new ModelLayerLocation(modResource(path), type);
    }

    public static String modResourceString(String path) {
        return modResource(path).toString();
    }

    public void clientLoad(FMLClientSetupEvent event) {
        // Dynamic Color
        LaserItemDynamicRender.DynamicLaserColor(LASER_POINTER);
        StyleItemPredicate.DynamicStyleRender(DYEABLE_SHIRT, "style_check");
    }

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }
}
