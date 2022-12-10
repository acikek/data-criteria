package com.acikek.datacriteria.predicate.builtin;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import net.minecraft.predicate.NumberRange;

import java.util.function.Function;

public class NumberRangeContainer<T extends Number, P extends JsonPredicate<T, ? extends NumberRange<T>>> extends JsonPredicateContainer<T, P> {

    public Function<JsonElement, P> deserializer;

    public NumberRangeContainer(Function<JsonElement, P> deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public P fromJson(JsonElement element) {
        return deserializer.apply(element);
    }

    public static NumberRangeContainer<Integer, IntPredicate> getInt() {
        return new NumberRangeContainer<>(element -> new IntPredicate(NumberRange.IntRange.fromJson(element)));
    }

    public static NumberRangeContainer<Double, FloatPredicate> getFloat() {
        return new NumberRangeContainer<>(element -> new FloatPredicate(NumberRange.FloatRange.fromJson(element)));
    }

    public static class IntPredicate extends JsonPredicate<Integer, NumberRange.IntRange> {

        public IntPredicate(NumberRange.IntRange intRange) {
            super(intRange, Integer.class, intRange::test, NumberRange::toJson);
        }
    }

    public static class FloatPredicate extends JsonPredicate<Double, NumberRange.FloatRange> {

        public FloatPredicate(NumberRange.FloatRange floatRange) {
            super(floatRange, Double.class, floatRange::test, NumberRange::toJson);
        }
    }
}
