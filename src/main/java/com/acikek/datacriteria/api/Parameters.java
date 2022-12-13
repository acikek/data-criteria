package com.acikek.datacriteria.api;

import com.acikek.datacriteria.impl.ParametersImpl;
import com.acikek.datacriteria.predicate.builtin.delegate.DelegateParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class Parameters {

    public static DelegateParameters.Block block(ServerWorld world, BlockPos pos) {
        return ParametersImpl.block(world, pos);
    }

    public static DelegateParameters.Location location(ServerWorld world, double x, double y, double z) {
        return ParametersImpl.location(world, x, y, z);
    }
}
