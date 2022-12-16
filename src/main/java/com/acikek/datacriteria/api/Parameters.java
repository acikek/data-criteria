package com.acikek.datacriteria.api;

import com.acikek.datacriteria.impl.ParametersImpl;
import com.acikek.datacriteria.predicate.builtin.delegate.DelegateParameters;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class Parameters {

    public static DelegateParameters.BlockParameter block(ServerWorld world, BlockPos pos) {
        return ParametersImpl.block(world, pos);
    }

    public static DelegateParameters.LocationParameter location(ServerWorld world, double x, double y, double z) {
        return ParametersImpl.location(world, x, y, z);
    }

    public static DelegateParameters.StateParameter<BlockState> state(BlockState state) {
        return ParametersImpl.state(state);
    }

    public static DelegateParameters.StateParameter<FluidState> state(FluidState state) {
        return ParametersImpl.state(state);
    }
}
