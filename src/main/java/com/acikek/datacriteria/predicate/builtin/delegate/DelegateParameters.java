package com.acikek.datacriteria.predicate.builtin.delegate;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class DelegateParameters {

    public record Block(ServerWorld world, BlockPos pos) {}

    public record Location(ServerWorld world, double x, double y, double z) {}

    public record Damage(ServerPlayerEntity player, DamageSource damageSource) {}
}
