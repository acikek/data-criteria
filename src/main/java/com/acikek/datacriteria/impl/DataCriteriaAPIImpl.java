package com.acikek.datacriteria.impl;

import com.acikek.datacriteria.advancement.DataCriterion;
import com.acikek.datacriteria.mixin.CriteriaTriggersAccess;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.acikek.datacriteria.predicate.JsonPredicates;
import com.acikek.datacriteria.predicate.builtin.EnumContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.registries.IForgeRegistry;

public class DataCriteriaAPIImpl {

    public static DataCriterion getDataCriterion(ResourceLocation id) {
        var criterion = CriteriaTriggersAccess.getValues().get(id);
        if (criterion instanceof DataCriterion dataCriterion) {
            return dataCriterion;
        }
        throw new IllegalArgumentException("data criterion '" + id + "' does not exist");
    }

    public static void trigger(ResourceLocation id, boolean debug, ServerPlayer player, Object... inputs) {
        getDataCriterion(id).trigger(debug, player, inputs);
    }

    public static void trigger(ResourceLocation id, ServerPlayer player, Object... inputs) {
        trigger(id, false, player, inputs);
    }

    public static IForgeRegistry<JsonPredicateContainer<?, ?>> getRegistry() {
        return JsonPredicates.REGISTRY.get();
    }

    public static <T extends Enum<T>> EnumContainer<T> createEnum(Class<T> type) {
        return new EnumContainer<>(type);
    }
}
