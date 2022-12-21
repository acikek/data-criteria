package com.acikek.datacriteria.predicate.builtin.delegate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.State;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class DelegateParameters {

    public record StateParameter<S extends State<?, S>>(StateManager<?, S> stateManager, S state) {}

    public record BlockParameter(ServerWorld world, BlockPos pos) {}

    public record EntityParameter(ServerWorld world, Vec3d pos, Entity entity) {}

    public record DamageSourceParameter(ServerWorld world, Vec3d pos, DamageSource source) {}

    public record DamageParameter(ServerPlayerEntity player, DamageSource source, float dealt, float taken, boolean blocked) {}

    public record DistanceParameter(double x1, double y1, double z1, double x2, double y2, double z2) {}

    public record LocationParameter(ServerWorld world, double x, double y, double z) {}
}
