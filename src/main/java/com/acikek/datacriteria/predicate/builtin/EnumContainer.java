package com.acikek.datacriteria.predicate.builtin;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang3.EnumUtils;

public class EnumContainer<T extends Enum<T>> extends JsonPredicateContainer<T, EnumContainer.Predicate<T>> {

    public Class<T> type;

    public EnumContainer(Class<T> type) {
        this.type = type;
    }

    @Override
    public Predicate<T> fromJson(JsonElement element) {
        if (!element.getAsJsonPrimitive().isString()) {
            throw new IllegalStateException("enum predicate must be a string");
        }
        String value = element.getAsString();
        T enumValue = EnumUtils.getEnumIgnoreCase(type, value);
        if (enumValue == null) {
            throw new IllegalStateException("'" + value + "' does not match to " + type);
        }
        return new Predicate<>(enumValue, type);
    }

    public static class Predicate<T extends Enum<T>> extends JsonPredicate.Single<T> {

        public Predicate(T value, Class<T> type) {
            super(new Single.Builder<T>()
                    .value(value)
                    .type(type)
                    .tester(e -> e == value)
                    .serializer(e -> new JsonPrimitive(e.name().toLowerCase())));
        }
    }
}
