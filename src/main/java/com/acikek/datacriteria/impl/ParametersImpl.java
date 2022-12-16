package com.acikek.datacriteria.impl;

import com.acikek.datacriteria.predicate.builtin.delegate.DelegateParameters;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class ParametersImpl {

    public static DelegateParameters.BlockParameter block(ServerWorld world, BlockPos pos) {
        return new DelegateParameters.BlockParameter(world, pos);
    }

    public static DelegateParameters.LocationParameter location(ServerWorld world, double x, double y, double z) {
        return new DelegateParameters.LocationParameter(world, x, y, z);
    }

    public static DelegateParameters.StateParameter<BlockState> state(BlockState state) {
        return new DelegateParameters.StateParameter<>(state.getBlock().getStateManager(), state);
    }

    public static DelegateParameters.StateParameter<FluidState> state(FluidState state) {
        return new DelegateParameters.StateParameter<>(state.getFluid().getStateManager(), state);
    }
}
