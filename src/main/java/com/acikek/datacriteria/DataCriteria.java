package com.acikek.datacriteria;

import com.acikek.datacriteria.api.DataCriteriaAPI;
import com.acikek.datacriteria.api.Parameters;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DataCriteria implements ModInitializer {

    public static final String ID = "datacriteria";

    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    @Override
    public void onInitialize() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                DataCriteriaAPI.trigger(
                        new Identifier("datacriteria:use_block"), // ID of criterion
                        true, serverPlayer,
                        Parameters.block((ServerWorld) serverPlayer.world, hitResult.getBlockPos()), // block predicate parameter
                        player.getStackInHand(hand) // item predicate parameter
                );
                DataCriteriaAPI.trigger(
                        new Identifier("datacriteria:list_of_ints"),
                        true, serverPlayer,
                        List.of(1, 2, 3, 4, 5)
                );
            }
            return ActionResult.PASS;
        });
    }
}
