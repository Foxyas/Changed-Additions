package net.foxyas.changed_additions.process;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public class StructureHandle {

    /**
     * Obtém as coordenadas do chunk a partir de uma posição de bloco.
     */
    private static int[] getChunkCoordinates(BlockPos pos) {
        return new int[]{pos.getX() >> 4, pos.getZ() >> 4}; // Converte coordenadas de bloco para chunk
    }

    /**
     * Verifica se uma estrutura pode gerar dentro de um determinado raio de chunks.
     *
     * @param level      o ServerLevel
     * @param pos        a posição a ser verificada
     * @param structureKey  ResourceKey da estrutura
     * @return true se a estrutura pode gerar na área, false caso contrário.
     */
    public static boolean isStructureAt(ServerLevel level, BlockPos pos, ResourceKey<Structure> structureKey) {
        return level.structureManager().getStructureAt(pos, level.registryAccess().registryOrThrow(Registries.STRUCTURE).getHolderOrThrow(structureKey).get()).isValid();
    }


    /**
     * Verifica se uma estrutura pode gerar dentro de um determinado raio de chunks.
     *
     * @param level       o ServerLevel
     * @param pos         a posição a ser verificada
     * @param structureId o ID da estrutura desejada (ex.: "changed_additions:dazed_meteor")
     * @param chunkRange  o raio de chunks a ser verificado
     * @return true se a estrutura pode gerar na área, false caso contrário.
     */
    public static boolean isStructureNearby(ServerLevel level, BlockPos pos, String structureId, int chunkRange) {
        ResourceKey<Structure> structureKey = ResourceKey.create(Registries.STRUCTURE, ResourceLocation.parse(structureId));
        return isStructureAt(level, pos, structureKey);
    }
}
