package com.acikek.datacriteria.api;

import com.acikek.datacriteria.advancement.DataCriterion;
import com.acikek.datacriteria.impl.DataCriteriaAPIImpl;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.acikek.datacriteria.predicate.builtin.EnumContainer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DataCriteriaAPI {

    public static DataCriterion getDataCriterion(Identifier id) {
        return DataCriteriaAPIImpl.getDataCriterion(id);
    }

    public static void trigger(Identifier id, boolean debug, ServerPlayerEntity player, Object... inputs) {
        DataCriteriaAPIImpl.trigger(id, debug, player, inputs);
    }

    public static void trigger(Identifier id, ServerPlayerEntity player, Object... inputs) {
        DataCriteriaAPIImpl.trigger(id, player, inputs);
    }

    public static Registry<JsonPredicateContainer<?, ?>> getRegistry() {
        return DataCriteriaAPIImpl.getRegistry();
    }

    public static <T extends Enum<T>> EnumContainer<T> getEnum(Class<T> type) {
        return DataCriteriaAPIImpl.getEnum(type);
    }

    public static <T extends Enum<T>> void registerEnum(Identifier id, Class<T> type) {
        DataCriteriaAPIImpl.registerEnum(id, type);
    }
}
