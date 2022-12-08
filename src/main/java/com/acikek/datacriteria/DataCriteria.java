package com.acikek.datacriteria;

import com.acikek.datacriteria.advancement.DataCriterion;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataCriteria implements ModInitializer {

    public static final String ID = "datacriteria";

    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    public static void trigger(Identifier id, ServerPlayerEntity player, Object... inputs) {
        var criterion = Criteria.VALUES.get(id);
        if (criterion instanceof DataCriterion dataCriterion) {
            dataCriterion.trigger(player, inputs);
        }
    }

    @Override
    public void onInitialize() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            trigger(id("int_range"), handler.player, 12345);
        });
    }
}
