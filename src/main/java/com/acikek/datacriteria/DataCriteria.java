package com.acikek.datacriteria;

import com.acikek.datacriteria.advancement.DataCriterion;
import com.acikek.datacriteria.advancement.Parameter;
import com.acikek.datacriteria.predicate.JsonPredicates;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.util.Identifier;

import java.util.List;

public class DataCriteria implements ModInitializer {

    public static final String ID = "datacriteria";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    @Override
    public void onInitialize() {
        List<Parameter<?, ?>> list = List.of(new Parameter<>("value", false, JsonPredicates.INT_RANGE));
        var criterion = new DataCriterion(new Identifier("datacriteria:int_range"), list);
        Criteria.register(criterion);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            criterion.trigger(handler.player, 12345);
        });
    }
}
