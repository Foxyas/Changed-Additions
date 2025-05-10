package net.foxyas.changed_additions.commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.entity.BasicPlayerInfo;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.common.util.FakePlayerFactory;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.commands.Commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;

@Mod.EventBusSubscriber
public class ChangedAdditionsCommands {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        final LiteralArgumentBuilder<CommandSourceStack> literalBuilder = Commands.literal("changed-additions");
        event.getDispatcher().register(literalBuilder);
        LiteralArgumentBuilder<CommandSourceStack> setBPISizeCommand = literalBuilder.then(Commands.literal("setBPISize")
                .then(Commands.argument("size", FloatArgumentType.floatArg())
                        .executes(arguments -> {
                                    ServerLevel world = arguments.getSource().getLevel();
                                    Entity entity = arguments.getSource().getEntity();
                                    if (entity == null) {
                                        entity = FakePlayerFactory.getMinecraft(world);
                                    }
                                    float amount = FloatArgumentType.getFloat(arguments, "size");

                                    return SizeManipulator.SizeChange(arguments, entity, amount);
                                }
                        )
                )
        ).requires(source -> source.getEntity() instanceof Player player && player.getLevel().isClientSide());
        event.getDispatcher().register(setBPISizeCommand);

        LiteralArgumentBuilder<CommandSourceStack> setMaxBPISize = literalBuilder.then(Commands.literal("setMaxBPISize")
                .then(Commands.argument("MaxSize", DoubleArgumentType.doubleArg())
                        .executes(arguments -> {
                                    ServerLevel world = arguments.getSource().getLevel();
                                    double amount = DoubleArgumentType.getDouble(arguments, "MaxSize");
                                    return SizeManipulator.MaxSizeChange(arguments, amount);
                                }
                        )
                )
        ).requires(source -> !source.getLevel().isClientSide);
        event.getDispatcher().register(setMaxBPISize);

        LiteralArgumentBuilder<CommandSourceStack> getMaxBPISize = literalBuilder.then(Commands.literal("getMaxSizeTolerance")
                .executes(SizeManipulator::SendMaxSizeTolerance
                )
        ).requires(source -> !source.getLevel().isClientSide);
        event.getDispatcher().register(getMaxBPISize);
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

        public static int SizeChange(CommandContext<CommandSourceStack> arguments, Entity entity, float amount) {
            if (entity instanceof Player player) {
                float newSize = getSize(amount, true);
                Changed.config.client.basicPlayerInfo.setSize(newSize); // Change Size
                ChangedAdditionsMod.LOGGER.info("Size changed to: " + newSize + " for player: " + player.getName().getString()); // Command Classic Log
                player.displayClientMessage(new TextComponent("Size changed to: " + newSize), false); // Chat log for the player
                arguments.getSource().sendSuccess(new TranslatableComponent("changed_additions.commands.setBpiSize.success", amount), false);
                return 1;
            } else {
                ChangedAdditionsMod.LOGGER.atError().log("Entity is not a player, cannot change size."); // Command Classic Error
                arguments.getSource().sendSuccess(new TranslatableComponent("changed_additions.commands.setBpiSize.fail"), false);
                return 0;
            }
        }


        public static int MaxSizeChange(CommandContext<CommandSourceStack> arguments, double amount) {
            try {
                Changed.config.server.bpiSizeTolerance.set(amount); // Change Size
                arguments.getSource().sendSuccess(new TranslatableComponent("changed_additions.commands.setMaxBPISize.success", amount), false);
                return 1;
            } catch (Exception exception) {
                arguments.getSource().sendFailure(new TextComponent(exception.getMessage()));
                return 0;
            }
        }

        public static int SendMaxSizeTolerance(CommandContext<CommandSourceStack> arguments) {
            try {
                double value = Changed.config.server.bpiSizeTolerance.get();
                arguments.getSource().sendSuccess(new TranslatableComponent("changed_additions.commands.getMaxSizeTolerance", value), false);
                return 1;
            } catch (Exception e) {
                arguments.getSource().sendFailure(new TranslatableComponent(e.getMessage()));
                return 0;
            }
        }
    }


}
