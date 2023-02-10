package com.acikek.datacriteria.api;

import com.acikek.datacriteria.advancement.DataCriterion;
import com.acikek.datacriteria.impl.DataCriteriaAPIImpl;
import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.acikek.datacriteria.predicate.builtin.EnumContainer;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Map;
import java.util.function.Function;

public class DataCriteriaAPI {

    public static DataCriterion getDataCriterion(ResourceLocation id) {
        return DataCriteriaAPIImpl.getDataCriterion(id);
    }

    public static void trigger(ResourceLocation id, boolean debug, ServerPlayer player, Object... inputs) {
        DataCriteriaAPIImpl.trigger(id, debug, player, inputs);
    }

    public static void trigger(ResourceLocation id, ServerPlayer player, Object... inputs) {
        DataCriteriaAPIImpl.trigger(id, player, inputs);
    }

    public static IForgeRegistry<JsonPredicateContainer<?,?>> getRegistry() {
        return DataCriteriaAPIImpl.getRegistry();
    }

    public static <T extends Enum<T>> EnumContainer<T> createEnum(Class<T> type) {
        return DataCriteriaAPIImpl.createEnum(type);
    }

    public static <T> JsonPredicateContainer<T, JsonPredicate.Equality<T>> createEquality(Class<T> type, Function<T, JsonElement> serializer, Function<JsonElement, T> deserializer) {
        return JsonPredicateContainer.createEquality(type, serializer, deserializer);
    }
}
