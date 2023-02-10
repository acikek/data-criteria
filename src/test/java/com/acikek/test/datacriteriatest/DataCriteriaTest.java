package com.acikek.test.datacriteriatest;

public class DataCriteriaTest /*implements ModInitializer*/ {

    /*@Override
    public void onInitialize() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                DataCriteriaAPI.trigger(
                        new Identifier("datacriteria:use_block"), // ID of criterion
                        true, serverPlayer,
                        Parameters.block(serverPlayer.getWorld(), hitResult.getBlockPos()), // block predicate parameter
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
    }*/
}
