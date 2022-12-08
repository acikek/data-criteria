package com.acikek.datacriteria.predicate;

import com.acikek.datacriteria.DataCriteria;
import com.acikek.datacriteria.predicate.builtin.IntRangeContainer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unchecked")
public class JsonPredicates {

    public static final Registry<JsonPredicateContainer<?, ?>> REGISTRY =
            (Registry<JsonPredicateContainer<?, ?>>) (Object)
            FabricRegistryBuilder.createSimple(JsonPredicateContainer.class, DataCriteria.id("container")).buildAndRegister();

    public static final IntRangeContainer INT_RANGE = new IntRangeContainer();

    static {
        Registry.register(REGISTRY, DataCriteria.id("int_range"), INT_RANGE);
    }
}
