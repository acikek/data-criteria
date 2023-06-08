package com.acikek.test.datacriteriatest;

import com.acikek.datacriteria.api.DataCriteriaAPI;
import com.acikek.datacriteria.api.Parameters;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import java.util.List;

public class DataCriteriaTest implements ModInitializer {

    @Override
    public void onInitialize() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                DataCriteriaAPI.trigger(
                        new Identifier("datacriteria:use_block"), // ID of criterion
                        true, serverPlayer,
                        Parameters.block(serverPlayer.getServerWorld(), hitResult.getBlockPos()), // block predicate parameter
                        player.getStackInHand(hand) // item predicate parameter
                );
            }
            return ActionResult.PASS;
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            DataCriteriaAPI.trigger(
                    new Identifier("datacriteria:list_of_ints"),
                    true, handler.player,
                    List.of(1, 2, 3, 4, 5)
            );
        });
    }
}
