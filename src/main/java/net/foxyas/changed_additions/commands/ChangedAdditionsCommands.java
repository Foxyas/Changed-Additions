package net.foxyas.changed_additions.commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.entity.BasicPlayerInfo;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.checkerframework.checker.units.qual.s;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.common.util.FakePlayerFactory;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;

@Mod.EventBusSubscriber
public class ChangedAdditionsCommands {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        LiteralArgumentBuilder<CommandSourceStack> literalBuilder = Commands.literal("changed-additions");
        literalBuilder.then(Commands.literal("setBPISize")
                .then(Commands.argument("size", FloatArgumentType.floatArg())
                        .executes(arguments -> {
                                    ServerLevel world = arguments.getSource().getLevel();
                                    Entity entity = arguments.getSource().getEntity();
                                    if (entity == null) {
                                        entity = FakePlayerFactory.getMinecraft(world);
                                    }
                                    float amount = FloatArgumentType.getFloat(arguments, "size");
                                    return SizeManipulator.SizeChange(entity, amount);
                                }
                        )
                )
        ).requires(source -> !source.getServer().isDedicatedServer() && source.getEntity() instanceof Player);

        literalBuilder.then(Commands.literal("setMaxBPISize")
                .then(Commands.argument("MaxSize", DoubleArgumentType.doubleArg())
                        .executes(arguments -> {
                                    ServerLevel world = arguments.getSource().getLevel();
                                    double amount = DoubleArgumentType.getDouble(arguments, "MaxSize");
                                    return SizeManipulator.MaxSizeChange(amount);
                                }
                        )
                )
        ).requires(source -> source.getServer().isDedicatedServer());


        event.getDispatcher().register(literalBuilder);
    }

    private static class SizeManipulator {
        private static final float SIZE_TOLERANCE = BasicPlayerInfo.getSizeTolerance();

        public static float getSize(float size, boolean OverrideSize) {
            if (size < 1.0f - SIZE_TOLERANCE) {
                ChangedAdditionsMod.LOGGER.atWarn().log("Size value is too low: " + size + ", The Size Value is going to be auto set to limit"); // Too Low Warn
            } else if (size > 1.0f + SIZE_TOLERANCE) {
                ChangedAdditionsMod.LOGGER.atWarn().log("Size value is too high: " + size + ", The Size Value is going to be auto set to max"); // Too High Warn
            }
            return OverrideSize ? Mth.clamp(size, 1.0f - SIZE_TOLERANCE, 1.0f + SIZE_TOLERANCE) : size;

            /*
             * if(newSize < 1.0f - SIZE_TOLERANCE) {
             *		player.displayClientMessage(new TextComponent ("Size value is too low: " + newSize + ", The Size Value is going to be auto set to 0.95"),true);
             *	} else if (newSize > 1.0f + SIZE_TOLERANCE) {
             *		player.displayClientMessage(new TextComponent ("Size value is too high: " + newSize + ", The Size Value is going to be auto set to 1.05"),true);
             *	}
             */
        }

        public static int SizeChange(Entity entity, float amount) {
            if (entity instanceof Player player) {
                float newSize = getSize(amount, true);
                Changed.config.client.basicPlayerInfo.setSize(newSize); // Change Size
                ChangedAdditionsMod.LOGGER.info("Size changed to: " + newSize + " for player: " + player.getName().getString()); // Command Classic Log
                player.displayClientMessage(new TextComponent("Size changed to: " + newSize), false); // Chat log for the player
                return 1;
            } else {
                ChangedAdditionsMod.LOGGER.atError().log("Entity is not a player, cannot change size."); // Command Classic Error
                return 0;
            }
        }


        public static int MaxSizeChange(double amount) {
            try {
                Changed.config.server.bpiSizeTolerance.set(amount); // Change Size
                return 1;
            } catch (Exception exception) {
                return 0;
            }
        }
    }


}
