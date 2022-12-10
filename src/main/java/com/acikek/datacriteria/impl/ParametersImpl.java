package com.acikek.datacriteria.impl;

import com.acikek.datacriteria.predicate.builtin.delegate.BlockContainer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class ParametersImpl {

    public static BlockContainer.Parameter block(ServerWorld world, BlockPos pos) {
        return new BlockContainer.Parameter(world, pos);
    }
}
