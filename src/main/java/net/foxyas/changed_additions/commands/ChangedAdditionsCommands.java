package net.foxyas.changed_additions.commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.entities.AbstractTamableLatexEntity;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.block.AbstractLatexBlock;
import net.ltxprogrammer.changed.entity.BasicPlayerInfo;
import net.ltxprogrammer.changed.entity.LatexType;
import net.ltxprogrammer.changed.entity.beast.AbstractDarkLatexEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;

@Mod.EventBusSubscriber
public class ChangedAdditionsCommands {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        final LiteralArgumentBuilder<CommandSourceStack> literalBuilder = Commands.literal("changed-additions");
        event.getDispatcher().register(literalBuilder);
        LiteralArgumentBuilder<CommandSourceStack> setMaxBPISize = literalBuilder.then(Commands.literal("setMaxBPISize").requires(source -> !source.getLevel().isClientSide && source.hasPermission(2))
                .then(Commands.argument("MaxSize", DoubleArgumentType.doubleArg())
                        .executes(arguments -> {
                                    ServerLevel world = arguments.getSource().getLevel();
                                    double amount = DoubleArgumentType.getDouble(arguments, "MaxSize");
                                    return SizeManipulator.MaxSizeChange(arguments, amount);
                                }
                        )
                )
        );
        event.getDispatcher().register(setMaxBPISize);

        LiteralArgumentBuilder<CommandSourceStack> getMaxBPISize = literalBuilder.then(Commands.literal("getMaxSizeTolerance").requires(source -> !source.getLevel().isClientSide)
                .executes(SizeManipulator::SendMaxSizeTolerance
                )
        );
        event.getDispatcher().register(getMaxBPISize);

        LiteralArgumentBuilder<CommandSourceStack> BlocksHandle = literalBuilder.requires(source -> !source.getLevel().isClientSide && source.hasPermission(2)).then(Commands.literal("BlocksHandle").requires(source -> !source.getLevel().isClientSide && source.hasPermission(2))
                .then(Commands.literal("setBlocksInfectionType")
                        .then(Commands.argument("minPos", BlockPosArgument.blockPos())
                                .then(Commands.argument("maxPos", BlockPosArgument.blockPos())
                                        .then(Commands.literal("white_latex")
                                                .executes(ctx -> {
                                                    ServerLevel world = ctx.getSource().getLevel();
                                                    BlockPos minPos = BlockPosArgument.getLoadedBlockPos(ctx, "minPos");
                                                    BlockPos maxPos = BlockPosArgument.getLoadedBlockPos(ctx, "maxPos");

                                                    long value = BlockPos.betweenClosedStream(minPos, maxPos).count();
                                                    int SAFE_LIMIT = 32768;

                                                    if (value > SAFE_LIMIT) {
                                                        ctx.getSource().sendFailure(new TextComponent("Too many blocks selected: " + value + " > " + SAFE_LIMIT));
                                                        return 0;
                                                    }

                                                    BlockHandle.run(world, minPos, maxPos, LatexType.WHITE_LATEX);
                                                    ctx.getSource().sendSuccess(new TextComponent("Set Infection of " + value + " blocks to white_latex"), true);
                                                    return 1;
                                                })
                                        ).then(Commands.literal("dark_latex")
                                                .executes(ctx -> {
                                                    ServerLevel world = ctx.getSource().getLevel();
                                                    BlockPos minPos = BlockPosArgument.getLoadedBlockPos(ctx, "minPos");
                                                    BlockPos maxPos = BlockPosArgument.getLoadedBlockPos(ctx, "maxPos");

                                                    long value = BlockPos.betweenClosedStream(minPos, maxPos).count();
                                                    int SAFE_LIMIT = 32768;

                                                    if (value > SAFE_LIMIT) {
                                                        ctx.getSource().sendFailure(new TextComponent("Too many blocks selected: " + value + " > " + SAFE_LIMIT));
                                                        return 0;
                                                    }

                                                    BlockHandle.run(world, minPos, maxPos, LatexType.DARK_LATEX);
                                                    ctx.getSource().sendSuccess(new TextComponent("Set Infection of " + value + " blocks to dark_latex"), true);
                                                    return 1;

                                                })
                                        ).then(Commands.literal("neutral")
                                                .executes(ctx -> {
                                                    ServerLevel world = ctx.getSource().getLevel();
                                                    BlockPos minPos = BlockPosArgument.getLoadedBlockPos(ctx, "minPos");
                                                    BlockPos maxPos = BlockPosArgument.getLoadedBlockPos(ctx, "maxPos");

                                                    long value = BlockPos.betweenClosedStream(minPos, maxPos).count();
                                                    int SAFE_LIMIT = 32768;

                                                    if (value > SAFE_LIMIT) {
                                                        ctx.getSource().sendFailure(new TextComponent("Too many blocks selected: " + value + " > " + SAFE_LIMIT));
                                                        return 0;
                                                    }

                                                    BlockHandle.run(world, minPos, maxPos, LatexType.NEUTRAL);
                                                    ctx.getSource().sendSuccess(new TextComponent("Set Infection of " + value + " blocks to neutral"), true);
                                                    return 1;

                                                })
                                        )
                                )
                        )
                )
        );


        event.getDispatcher().register(BlocksHandle);

        LiteralArgumentBuilder<CommandSourceStack> tameEntity = literalBuilder.then(Commands.literal("tameChangedEntity").requires(source -> !source.getLevel().isClientSide && source.hasPermission(2))
                .then(Commands.argument("tameTarget", EntityArgument.entities())
                        .then(Commands.argument("tamer", EntityArgument.player())
                                .then(Commands.literal("overwrite_owner")
                                        .executes((ctx) -> {
                                            TameHandle.run(EntityArgument.getEntities(ctx, "tameTarget"), EntityArgument.getPlayer(ctx, "tamer"), true);
                                            return 1; // overwrite the owner
                                        }))
                                .executes((ctx) -> {
                                    TameHandle.run(EntityArgument.getEntities(ctx, "tameTarget"), EntityArgument.getPlayer(ctx, "tamer"), false);
                                    return 1; // no overwrite
                                })
                        )
                )
        );
        event.getDispatcher().register(tameEntity);
    }

    @SubscribeEvent
    public static void registerClientCommand(RegisterClientCommandsEvent event) {
        final LiteralArgumentBuilder<CommandSourceStack> literalBuilder = Commands.literal("changed-additions");
        event.getDispatcher().register(literalBuilder);
        LiteralArgumentBuilder<CommandSourceStack> setBPISizeCommand = literalBuilder.then(Commands.literal("setBPISize")
                .then(Commands.argument("size", FloatArgumentType.floatArg())
                        .executes(arguments -> {
                                    Entity entity = arguments.getSource().getEntity();
                                    float amount = FloatArgumentType.getFloat(arguments, "size");

                                    return SizeManipulator.SizeChange(arguments, entity, amount);
                                }
                        )
                )
        );
        event.getDispatcher().register(setBPISizeCommand);
    }

    public static class BlockHandle {
        public static void run(LevelAccessor world, BlockPos minPos, BlockPos maxPos, LatexType enumValue) {
            for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
                BlockState state = world.getBlockState(pos);
                if (state.hasProperty(AbstractLatexBlock.COVERED)) {
                    BlockState newState = state.setValue(AbstractLatexBlock.COVERED, enumValue);
                    world.setBlock(pos, newState, 3);
                }
            }
        }
    }

    public static class TameHandle {
        public static void run(Collection<?> tameTargets, Player tamer, boolean overwriteOwner) {
            tameTargets.forEach((tameTarget) -> {
                if (tameTarget instanceof AbstractTamableLatexEntity tamableLatexEntity) {
                    if (tamableLatexEntity.getOwner() != null && overwriteOwner) {
                        tamableLatexEntity.tame(tamer);
                    } else if (tamableLatexEntity.getOwner() == null) {
                        tamableLatexEntity.tame(tamer);
                    }
                } else if (tameTarget instanceof AbstractDarkLatexEntity tamableDarkLatexEntity) {
                    if (tamableDarkLatexEntity.getOwner() != null && overwriteOwner) {
                        tamableDarkLatexEntity.setOwnerUUID(tamer.getUUID());
                    } else if (tamableDarkLatexEntity.getOwner() == null) {
                        tamableDarkLatexEntity.setOwnerUUID(tamer.getUUID());
                    }
                }
            });
        }
    }

    public static class SizeManipulator {

        public static float getSize(Player player, float size, boolean OverrideSize) {
            float SIZE_TOLERANCE = BasicPlayerInfo.getSizeTolerance();
            if (size < 1.0f - SIZE_TOLERANCE) {
                player.displayClientMessage(new TextComponent("Size value is too low: " + size + ", The Size Value is going to be auto set to limit").withStyle((style -> style.withColor(ChatFormatting.YELLOW).withBold(true))), false); // Too Low Warn
            } else if (size > 1.0f + SIZE_TOLERANCE) {
                player.displayClientMessage(new TextComponent("Size value is too high: " + size + ", The Size Value is going to be auto set to max").withStyle((style -> style.withColor(ChatFormatting.YELLOW).withBold(true))), false); // Too High Warn
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
                float newSize = getSize(player, amount, true);
                Changed.config.client.basicPlayerInfo.setSize(newSize); // Change Size
                ChangedAdditionsMod.LOGGER.info("Size changed to: " + newSize + " for player: " + player.getName().getString()); // Command Classic Log
                //player.displayClientMessage(new TextComponent("Size changed to: " + newSize), false); // Chat log for the player
                arguments.getSource().sendSuccess(new TranslatableComponent("changed_additions.commands.setBpiSize.success", amount), false);
                return 1;
            } else {
                ChangedAdditionsMod.LOGGER.atError().log("cannot change size."); // Command Classic Error
                arguments.getSource().sendSuccess(new TranslatableComponent("changed_additions.commands.setBpiSize.fail"), false);
                return 0;
            }
        }


        public static int MaxSizeChange(CommandContext<CommandSourceStack> arguments, double amount) {
            try {
                Changed.config.server.bpiSizeTolerance.set(amount); // Change Size
                arguments.getSource().sendSuccess(new TranslatableComponent("changed_additions.commands.setMaxBPISize.success", amount), false);
                if (amount < 0) {
                    arguments.getSource().sendSuccess(new TranslatableComponent("changed_additions.commands.setMaxBPISize.success_but_to_low", amount), false);
                }
                if (amount > 100) {
                    arguments.getSource().sendSuccess(new TranslatableComponent("changed_additions.commands.setMaxBPISize.success_but_to_high", amount), false);
                }
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
