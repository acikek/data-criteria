package com.acikek.datacriteria.predicate;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.function.Function;

public class JsonPredicateContainer<T, P extends JsonPredicate<T, ?>> {

    public static class Typed<T, P extends JsonPredicate<T, ?>> extends JsonPredicateContainer<T, P> {

        public Class<T> type;

        public Typed(Class<T> type) {
            this.type = type;
        }
    }

    public Function<JsonElement, P> deserializer;

    public JsonPredicateContainer(Function<JsonElement, P> deserializer) {
        this.deserializer = deserializer;
    }

    public JsonPredicateContainer() {
        this(null);
    }

    public P fromJson(JsonElement element) {
        return deserializer.apply(element);
    }

    public static <T> JsonPredicateContainer<T, JsonPredicate.Equality<T>> createEquality(Class<T> type, Function<T, JsonElement> serializer, Function<JsonElement, T> deserializer) {
        var builder = new Builder<T, T>()
                .type(type)
                .serializer(serializer);
        return new JsonPredicateContainer<>(element -> new JsonPredicate.Equality<>(deserializer.apply(element), builder));
    }

    public static <T> JsonPredicateContainer<T, JsonPredicate.Equality<T>> createPrimitive(Class<T> type, Function<T, JsonElement> serializer, Function<JsonPrimitive, T> deserializer) {
        return createEquality(type, serializer, element -> deserializer.apply(element.getAsJsonPrimitive()));
    }
}
