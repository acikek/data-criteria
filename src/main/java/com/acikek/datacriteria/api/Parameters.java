package com.acikek.datacriteria.api;

import com.acikek.datacriteria.impl.ParametersImpl;
import com.acikek.datacriteria.predicate.builtin.delegate.DelegateParameters;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Parameters {

    public static DelegateParameters.StateParameter<BlockState> state(BlockState state) {
        return ParametersImpl.state(state);
    }

    public static DelegateParameters.StateParameter<FluidState> state(FluidState state) {
        return ParametersImpl.state(state);
    }

    public static DelegateParameters.BlockParameter block(ServerWorld world, BlockPos pos) {
        return ParametersImpl.block(world, pos);
    }

    public static DelegateParameters.EntityParameter entity(ServerPlayerEntity player, Entity entity) {
        return ParametersImpl.entity(player, entity);
    }

    public static DelegateParameters.EntityParameter entity(ServerWorld world, Vec3d pos, Entity entity) {
        return ParametersImpl.entity(world, pos, entity);
    }

    public static DelegateParameters.DamageSourceParameter damageSource(ServerPlayerEntity player, DamageSource source) {
        return ParametersImpl.damageSource(player, source);
    }

    public static DelegateParameters.DamageSourceParameter damageSource(ServerWorld world, Vec3d pos, DamageSource source) {
        return ParametersImpl.damageSource(world, pos, source);
    }

    public static DelegateParameters.DamageParameter damage(ServerPlayerEntity player, DamageSource source, float dealt, float taken, boolean blocked) {
        return ParametersImpl.damage(player, source, dealt, taken, blocked);
    }

    public static DelegateParameters.LocationParameter location(ServerWorld world, double x, double y, double z) {
        return ParametersImpl.location(world, x, y, z);
    }

    public static DelegateParameters.DistanceParameter distance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return ParametersImpl.distance(x1, y1, z1, x2, y2, z2);
    }
}
