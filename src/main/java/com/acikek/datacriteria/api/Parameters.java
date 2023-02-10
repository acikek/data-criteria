package com.acikek.datacriteria.api;

import com.acikek.datacriteria.impl.ParametersImpl;
import com.acikek.datacriteria.predicate.builtin.delegate.DelegateParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class Parameters {

    public static DelegateParameters.StateParameter<BlockState> state(BlockState state) {
        return ParametersImpl.state(state);
    }

    public static DelegateParameters.StateParameter<FluidState> state(FluidState state) {
        return ParametersImpl.state(state);
    }

    public static DelegateParameters.BlockParameter block(ServerLevel world, BlockPos pos) {
        return ParametersImpl.block(world, pos);
    }

    public static DelegateParameters.EntityParameter entity(ServerPlayer player, Entity entity) {
        return ParametersImpl.entity(player, entity);
    }

    public static DelegateParameters.EntityParameter entity(ServerLevel world, Vec3 pos, Entity entity) {
        return ParametersImpl.entity(world, pos, entity);
    }

    public static DelegateParameters.DamageSourceParameter damageSource(ServerPlayer player, DamageSource source) {
        return ParametersImpl.damageSource(player, source);
    }

    public static DelegateParameters.DamageSourceParameter damageSource(ServerLevel world, Vec3 pos, DamageSource source) {
        return ParametersImpl.damageSource(world, pos, source);
    }

    public static DelegateParameters.DamageParameter damage(ServerPlayer player, DamageSource source, float dealt, float taken, boolean blocked) {
        return ParametersImpl.damage(player, source, dealt, taken, blocked);
    }

    public static DelegateParameters.LocationParameter location(ServerLevel world, double x, double y, double z) {
        return ParametersImpl.location(world, x, y, z);
    }

    public static DelegateParameters.DistanceParameter distance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return ParametersImpl.distance(x1, y1, z1, x2, y2, z2);
    }
}
