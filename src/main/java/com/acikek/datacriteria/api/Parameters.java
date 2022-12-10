package com.acikek.datacriteria.api;

import com.acikek.datacriteria.impl.ParametersImpl;
import com.acikek.datacriteria.predicate.builtin.delegate.BlockContainer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class Parameters {

    public static BlockContainer.Parameter block(ServerWorld world, BlockPos pos) {
        return ParametersImpl.block(world, pos);
    }
}
