package com.acikek.datacriteria.predicate;

import com.acikek.datacriteria.DataCriteria;
import com.acikek.datacriteria.predicate.builtin.BooleanContainer;
import com.acikek.datacriteria.predicate.builtin.EnumContainer;
import com.acikek.datacriteria.predicate.builtin.math.NumberRangeContainer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unchecked")
public class JsonPredicates {

    public static final Registry<JsonPredicateContainer<?, ?>> REGISTRY =
            (Registry<JsonPredicateContainer<?, ?>>) (Object)
            FabricRegistryBuilder.createSimple(JsonPredicateContainer.class, DataCriteria.id("container")).buildAndRegister();

    public static final NumberRangeContainer<Integer, NumberRangeContainer.IntPredicate> INT = NumberRangeContainer.getInt();
    public static final NumberRangeContainer<Double, NumberRangeContainer.FloatPredicate> FLOAT = NumberRangeContainer.getFloat();
    public static final BooleanContainer BOOLEAN = new BooleanContainer();

    public static <T extends Enum<T>> EnumContainer<T> getEnum(Class<T> type) {
        return new EnumContainer<>(type);
    }

    public static void register(String name, JsonPredicateContainer<?, ?> container) {
        Registry.register(REGISTRY, DataCriteria.id(name), container);
    }

    static {
        register("int", INT);
        register("float", FLOAT);
        register("boolean", BOOLEAN);
    }
}
