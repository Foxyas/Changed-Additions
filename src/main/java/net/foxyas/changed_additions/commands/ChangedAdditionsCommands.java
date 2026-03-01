package net.foxyas.changed_additions.commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.foxyas.changed_additions.entities.AbstractTamableLatexEntity;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QTEManager;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QuickTimeEvent;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QuickTimeEventSequenceType;
import net.foxyas.changed_additions.process.quickTimeEvents.commonSide.QuickTimeEventType;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.entity.BasicPlayerInfo;
import net.ltxprogrammer.changed.entity.beast.AbstractDarkLatexEntity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.Collection;

@Mod.EventBusSubscriber
public class ChangedAdditionsCommands {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        final LiteralArgumentBuilder<CommandSourceStack> literalBuilder = Commands.literal("changed-additions");
        var ChangedAdditionsCommandNode = event.getDispatcher().register(literalBuilder);


        /*LiteralArgumentBuilder<CommandSourceStack> BlocksHandle = literalBuilder.requires(source -> !source.getLevel().isClientSide && source.hasPermission(2)).then(Commands.literal("BlocksHandle").requires(source -> !source.getLevel().isClientSide && source.hasPermission(2))
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
                                                        ctx.getSource().sendFailure(Component.literal("Too many blocks selected: " + value + " > " + SAFE_LIMIT));
                                                        return 0;
                                                    }

                                                    BlockHandle.run(world, minPos, maxPos, LatexType.WHITE_LATEX);
                                                    ctx.getSource().sendSuccess(() -> Component.literal("Set Infection of " + value + " blocks to white_latex"), true);
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
                                                        ctx.getSource().sendFailure(Component.literal("Too many blocks selected: " + value + " > " + SAFE_LIMIT));
                                                        return 0;
                                                    }

                                                    BlockHandle.run(world, minPos, maxPos, LatexType.DARK_LATEX);
                                                    ctx.getSource().sendSuccess(() -> Component.literal("Set Infection of " + value + " blocks to dark_latex"), true);
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
                                                        ctx.getSource().sendFailure(Component.literal("Too many blocks selected: " + value + " > " + SAFE_LIMIT));
                                                        return 0;
                                                    }

                                                    BlockHandle.run(world, minPos, maxPos, LatexType.NEUTRAL);
                                                    ctx.getSource().sendSuccess(() -> Component.literal("Set Infection of " + value + " blocks to neutral"), true);
                                                    return 1;

                                                })
                                        )
                                )
                        )
                )
        );


        var BlockInfectionHandleCommandNode = event.getDispatcher().register(BlocksHandle);*/

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
        var tameEntityCommandNode = event.getDispatcher().register(tameEntity);

        LiteralArgumentBuilder<CommandSourceStack> QuickTimeEventsHandle = literalBuilder.requires(source -> !source.getLevel().isClientSide && source.hasPermission(2)).then(Commands.literal("QuickTimeEventsHandle").requires(source -> !source.getLevel().isClientSide && source.hasPermission(2))
                .then(Commands.literal("getQuickTimeEventForPlayer")
                        .then(Commands.argument("targetPlayer", EntityArgument.player())
                                .executes(ctx -> {
                                    Player player = EntityArgument.getPlayer(ctx, "targetPlayer");
                                    QuickTimeEvent quickTimeEvent = QTEManager.getActiveQTE(player);
                                    if (quickTimeEvent != null) {
                                        ctx.getSource().sendSuccess(() ->
                                                        Component.translatable(
                                                        "changed_additions.commands.getQuickTimeEventForPlayer.have",
                                                        player.getName().getString()
                                                ),
                                                false
                                        );
                                        ctx.getSource().sendSuccess(() ->
                                                        Component.translatable(
                                                        "changed_additions.commands.getQuickTimeEventForPlayer.details",
                                                        quickTimeEvent.getType().name(),
                                                        quickTimeEvent.getSequenceType().name(),
                                                        quickTimeEvent.getTicksRemaining(),
                                                        quickTimeEvent.getProgress() * 100 + "%"
                                                ),
                                                false
                                        );
                                    } else {
                                        ctx.getSource().sendSuccess(() ->
                                                        Component.translatable(
                                                        "changed_additions.commands.getQuickTimeEventForPlayer.none",
                                                        player.getName().getString()
                                                ),
                                                false
                                        );
                                    }

                                    return 1;
                                })
                        )
                )


                .then(Commands.literal("setQuickTimeEventForPlayer")
                        .then(Commands.argument("targetPlayer", EntityArgument.player())
                                .then(Commands.argument("SequenceType", StringArgumentType.word())
                                        .suggests((ctx, builder) -> {
                                            return SharedSuggestionProvider.suggest(
                                                    Arrays.stream(QuickTimeEventSequenceType.values()).map(Enum::name),
                                                    builder
                                            );
                                        })
                                        .then(Commands.argument("Type", StringArgumentType.word())
                                                .suggests((ctx, builder) -> {
                                                    return SharedSuggestionProvider.suggest(
                                                            Arrays.stream(QuickTimeEventType.values()).map(Enum::name),
                                                            builder
                                                    );
                                                })
                                                .then(Commands.argument("Ticks", IntegerArgumentType.integer(0))
                                                        .executes(ctx -> {
                                                            ServerLevel world = ctx.getSource().getLevel();
                                                            Player player = EntityArgument.getPlayer(ctx, "targetPlayer");

                                                            // Converte os nomes para enums (com validação)
                                                            String sequenceStr = StringArgumentType.getString(ctx, "SequenceType");
                                                            String typeStr = StringArgumentType.getString(ctx, "Type");

                                                            QuickTimeEventSequenceType sequenceType;
                                                            QuickTimeEventType type;
                                                            try {
                                                                sequenceType = QuickTimeEventSequenceType.valueOf(sequenceStr.toUpperCase());
                                                                type = QuickTimeEventType.valueOf(typeStr.toUpperCase());
                                                            } catch (IllegalArgumentException ex) {
                                                                ctx.getSource().sendFailure(Component.translatable("changed_additions.commands.setQuickTimeEventForPlayer.fail"));
                                                                return 0;
                                                            }

                                                            int ticks = IntegerArgumentType.getInteger(ctx, "Ticks");
                                                            QuickTimeEvent quickTimeEvent = new QuickTimeEvent(player, sequenceType, type, ticks);
                                                            QTEManager.addQTE(player, quickTimeEvent);
                                                            ctx.getSource().sendSuccess(() ->
                                                                            Component.translatable(
                                                                            "changed_additions.commands.setQuickTimeEventForPlayer.success",
                                                                            type.name(), sequenceType.name(), player.getName().getString(), ticks
                                                                    ),
                                                                    false
                                                            );
                                                            return 1;
                                                        })
                                                )
                                        )
                                )
                        )
                )
        );

        var QuickTimeEventsHandleCommandNode = event.getDispatcher().register(QuickTimeEventsHandle);

        //Short Commands
        event.getDispatcher().register(Commands.literal("ca").redirect(ChangedAdditionsCommandNode));
        event.getDispatcher().register(Commands.literal("ca-QTE").requires(source -> source.hasPermission(2)).redirect(QuickTimeEventsHandleCommandNode.getChild("QuickTimeEventsHandle")));
        //event.getDispatcher().register(Commands.literal("ca-setBlocksInfection").requires(source -> source.hasPermission(2)).redirect(BlockInfectionHandleCommandNode.getChild("BlocksHandle").getChild("setBlocksInfectionType")));
    }

    @SubscribeEvent
    public static void registerClientCommand(RegisterClientCommandsEvent event) {
        final LiteralArgumentBuilder<CommandSourceStack> literalBuilder = Commands.literal("changed-additions");
        var ChangedAdditionsCommandNode = event.getDispatcher().register(literalBuilder);
        event.getDispatcher().register(Commands.literal("ca").redirect(ChangedAdditionsCommandNode));

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

    /*public static class BlockHandle {
        public static void run(LevelAccessor world, BlockPos minPos, BlockPos maxPos, LatexType enumValue) {
            for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
                BlockState state = world.getBlockState(pos);
                if (state.hasProperty(AbstractLatexBlock.COVERED)) {
                    BlockState newState = state.setValue(AbstractLatexBlock.COVERED, enumValue);
                    world.setBlock(pos, newState, 3);
                }
            }
        }
    }*/

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

        private static float getSize(Player player, float size, boolean overrideSize) {
            float MINIMUM_SIZE_TOLERANCE = BasicPlayerInfo.getSizeMinimum(player);
            float MAX_SIZE_TOLERANCE = BasicPlayerInfo.getSizeMaximum(player);
            if (size < MINIMUM_SIZE_TOLERANCE) {
                ChangedAdditionsMod.LOGGER.atWarn().log("Size value is too low: {}, The Size Value will probably to be auto set to 0.95", size); // Too Low Warn
            } else if (size > MAX_SIZE_TOLERANCE) {
                ChangedAdditionsMod.LOGGER.atWarn().log("Size value is too high: {}, The Size Value will probably to be auto set to 1.05", size); // Too High Warn
            }
            return overrideSize ? Mth.clamp(size, MINIMUM_SIZE_TOLERANCE, MAX_SIZE_TOLERANCE) : size;
        }

        public static int SizeChange(CommandContext<CommandSourceStack> arguments, Entity entity, float amount) {
            if (entity instanceof Player player) {
                float newSize = getSize(player, amount, true);
                Changed.config.client.basicPlayerInfo.setSize(newSize); // Change Size
                ChangedAdditionsMod.LOGGER.info("Size changed to: " + newSize + " for player: " + player.getName().getString()); // Command Classic Log
                //player.displayClientMessage(Component.literal("Size changed to: " + newSize), false); // Chat log for the player
                arguments.getSource().sendSuccess(() -> Component.translatable("changed_additions.commands.setBpiSize.success", amount), false);
                return 1;
            } else {
                ChangedAdditionsMod.LOGGER.atError().log("cannot change size."); // Command Classic Error
                arguments.getSource().sendSuccess(() -> Component.translatable("changed_additions.commands.setBpiSize.fail"), false);
                return 0;
            }
        }

    }


}
