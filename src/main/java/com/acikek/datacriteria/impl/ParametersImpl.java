package com.acikek.datacriteria.impl;

import com.acikek.datacriteria.predicate.builtin.delegate.DelegateParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class ParametersImpl {

    public static DelegateParameters.Block block(ServerWorld world, BlockPos pos) {
        return new DelegateParameters.Block(world, pos);
    }

    public static DelegateParameters.Location location(ServerWorld world, double x, double y, double z) {
        return new DelegateParameters.Location(world, x, y, z);
    }
}
