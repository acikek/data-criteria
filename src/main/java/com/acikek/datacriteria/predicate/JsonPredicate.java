package com.acikek.datacriteria.predicate;

import com.acikek.datacriteria.DataCriteria;
import com.google.gson.JsonElement;

import java.util.function.Function;
import java.util.function.Predicate;

public class JsonPredicate<T, P> implements Predicate<T> {

    public static class Single<T> extends JsonPredicate<T, T> {

        public Single(T value, Class<T> type, Function<T, Boolean> tester, Function<T, JsonElement> serializer) {
            super(value, type, tester, serializer);
        }

        public Single(Builder<T, T> builder) {
            super(builder);
        }
    }

    public static class Equality<T> extends JsonPredicate<T, T> {

        public Equality(T value, Builder<T, T> builder) {
            super(builder
                    .value(value)
                    .tester(v -> v.equals(value)));
        }
    }

    public P value;
    public Class<T> type;
    public Function<T, Boolean> tester;
    public Function<P, JsonElement> serializer;

    public JsonPredicate(P value, Class<T> type, Function<T, Boolean> tester, Function<P, JsonElement> serializer) {
        this.value = value;
        this.type = type;
        this.tester = tester;
        this.serializer = serializer;
    }

    public JsonPredicate(Builder<T, P> builder) {
        this(builder.value, builder.type, builder.tester, builder.serializer);
    }

    public Class<T> getType() {
        return type;
    }

    @Override
    public boolean test(T t) {
        return tester.apply(t);
    }

    public JsonElement toJson() {
        return serializer.apply(value);
    }

    public boolean tryTest(Object value, boolean debug) {
        if (getType().isInstance(value)) {
            if (debug) {
                DataCriteria.LOGGER.info("- Input value: {}", value);
                DataCriteria.LOGGER.info("- Parameter value: {} ({})", this.toJson(), this.value);
            }
            return test(getType().cast(value));
        }
        throw new IllegalStateException("'" + value + "' is not of type " + getType());
    }
}
