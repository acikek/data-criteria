package com.acikek.datacriteria.predicate.builtin;

import com.acikek.datacriteria.predicate.Builder;
import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang3.EnumUtils;

public class EnumContainer<T extends Enum<T>> extends JsonPredicateContainer<T, JsonPredicate.Equality<T>> {

    public Class<T> type;

    public EnumContainer(Class<T> type) {
        this.type = type;
    }

    @Override
    public JsonPredicate.Equality<T> fromJson(JsonElement element) {
        if (!element.getAsJsonPrimitive().isString()) {
            throw new IllegalStateException("enum predicate must be a string");
        }
        String value = element.getAsString();
        T enumValue = EnumUtils.getEnumIgnoreCase(type, value);
        if (enumValue == null) {
            throw new IllegalStateException("'" + value + "' does not match to " + type);
        }
        return new JsonPredicate.Equality<>(enumValue, new Builder.Single<T>()
                .type(type)
                .serializer(e -> new JsonPrimitive(e.name().toLowerCase())));
    }
}
