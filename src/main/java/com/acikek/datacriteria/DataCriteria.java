package com.acikek.datacriteria;

import com.acikek.datacriteria.advancement.DataCriterion;
import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicates;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
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
        else {
            throw new IllegalArgumentException("data criterion '" + id + "' does not exist");
        }
    }

    public enum Custom {
        TEST,
        YES,
        NO
    }

    @Override
    public void onInitialize() {
        Registry.register(JsonPredicates.REGISTRY, new Identifier("test:custom_enum"), JsonPredicates.getEnum(Custom.class));
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            trigger(id("test"), handler.player, 12345, 0.5, true, Custom.YES);
            trigger(id("test"), handler.player, 1000, 1.5, false, Custom.NO);
        });
    }
}
