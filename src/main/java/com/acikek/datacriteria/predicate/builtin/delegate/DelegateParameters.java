package com.acikek.datacriteria.predicate.builtin.delegate;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.phys.Vec3;

public class DelegateParameters {

    public record StateParameter<S extends StateHolder<?, S>>(StateDefinition<?, S> stateManager, S state) {}

    public record BlockParameter(ServerLevel world, BlockPos pos) {}

    public record EntityParameter(ServerLevel world, Vec3 pos, Entity entity) {}

    public record DamageSourceParameter(ServerLevel world, Vec3 pos, DamageSource source) {}

    public record DamageParameter(ServerPlayer player, DamageSource source, float dealt, float taken, boolean blocked) {}

    public record DistanceParameter(double x1, double y1, double z1, double x2, double y2, double z2) {};

    public record LocationParameter(ServerLevel world, double x, double y, double z) {}
}
