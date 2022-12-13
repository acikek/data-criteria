package com.acikek.datacriteria.impl;

import com.acikek.datacriteria.advancement.DataCriterion;
import com.acikek.datacriteria.mixin.CriteriaAccess;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.acikek.datacriteria.predicate.JsonPredicates;
import com.acikek.datacriteria.predicate.builtin.EnumContainer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DataCriteriaAPIImpl {

    public static DataCriterion getDataCriterion(Identifier id) {
        var criterion = CriteriaAccess.getValues().get(id);
        if (criterion instanceof DataCriterion dataCriterion) {
            return dataCriterion;
        }
        throw new IllegalArgumentException("data criterion '" + id + "' does not exist");
    }

    public static void trigger(Identifier id, boolean debug, ServerPlayerEntity player, Object... inputs) {
        getDataCriterion(id).trigger(debug, player, inputs);
    }

    public static void trigger(Identifier id, ServerPlayerEntity player, Object... inputs) {
        trigger(id, false, player, inputs);
    }

    public static Registry<JsonPredicateContainer<?, ?>> getRegistry() {
        return JsonPredicates.REGISTRY;
    }

    public static <T extends Enum<T>> EnumContainer<T> createEnum(Class<T> type) {
        return new EnumContainer<>(type);
    }
}
