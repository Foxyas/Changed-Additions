package net.foxyas.changed_additions.init;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.item.*;
import net.foxyas.changed_additions.item.armor.DarkLatexCoatItem;
import net.foxyas.changed_additions.item.armor.DyeableShorts;
import net.foxyas.changed_additions.item.armor.TShirtClothing;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ChangedAdditionsItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ChangedAdditionsMod.MODID);
    public static final RegistryObject<Item> BIOMASS = REGISTRY.register("biomass", BiomassItem::new);
    public static final RegistryObject<Item> ANTI_LATEX_BASE = REGISTRY.register("anti_latex_base", AntiLatexbaseItem::new);
    public static final RegistryObject<Item> AMMONIUM_CHLORIDE = REGISTRY.register("ammonium_chloride", AmmoniumchlorideItem::new);
    public static final RegistryObject<Item> LAETHIN_SYRINGE = REGISTRY.register("laethin_syringe", LaethinSyringeItem::new);
    public static final RegistryObject<Item> NEOFUSER = block(ChangedAdditionsBlocks.NEOFUSER);

    public static final RegistryObject<Item> GOLDEN_ORANGE = REGISTRY.register("golden_orange", GoldenOrange::new);

    public static final RegistryObject<Item> DYEABLE_SHORTS = REGISTRY.register("dyeable_shorts", DyeableShorts::new);
    public static final RegistryObject<Item> DYEABLE_SHIRT = REGISTRY.register("dyeable_shirt", TShirtClothing::new);
    public static final RegistryObject<Item> LASER_POINTER = REGISTRY.register("laser_pointer", LaserPointer::new);

    public static final RegistryObject<Item> LATEX_SNOW_FOX_SPAWN_EGG = REGISTRY.register("latex_snow_fox_male_spawn_egg", () -> new ForgeSpawnEggItem(ChangedAdditionsEntities.LATEX_SNOW_FOX_MALE, 0xFFFFFFF, 0xfD6DDF7, new Item.Properties()));

    public static final RegistryObject<Item> LATEX_SNOW_FOX_FEMALE_SPAWN_EGG = REGISTRY.register("latex_snow_fox_female_spawn_egg", () -> new ForgeSpawnEggItem(ChangedAdditionsEntities.LATEX_SNOW_FOX_FEMALE, 0xFFFFFFF, 0xfD6DDF7, new Item.Properties()));

    public static final RegistryObject<Item> feng_qi_wolf_SPAWN_EGG = REGISTRY.register("feng_qi_wolf_spawn_egg", () -> new ForgeSpawnEggItem(ChangedAdditionsEntities.FENG_QI_WOLF, Color3.getColor("#93c6fd").toInt(), Color3.getColor("#FAC576").toInt(), new Item.Properties()));

    public static final RegistryObject<Item> DARK_LATEX_COAT = REGISTRY.register("dark_latex_coat",
            () -> new DarkLatexCoatItem(ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> DARK_LATEX_HEAD_CAP = REGISTRY.register("dark_latex_coat_cap",
            () -> new DarkLatexCoatItem.HeadPart(ArmorItem.Type.HELMET, new Item.Properties()));

    private static RegistryObject<Item> block(RegistryObject<Block> block) {
        return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientInitializer {
        @SubscribeEvent
        public static void onItemColorsInit(RegisterColorHandlersEvent.Item event) {
            event.register(
                    (stack, layer) -> layer == 0 ? ((DyeableLeatherItem) stack.getItem()).getColor(stack) : -1,
                    ChangedAdditionsItems.DYEABLE_SHIRT.get()
            );

            event.register(
                    (stack, layer) -> ((DyeableLeatherItem)stack.getItem()).getColor(stack),
                    ChangedAdditionsItems.DYEABLE_SHORTS.get());
        }
    }
}
