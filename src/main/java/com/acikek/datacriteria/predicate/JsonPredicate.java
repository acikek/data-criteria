package com.acikek.datacriteria.predicate;

import com.google.gson.JsonElement;

import java.util.function.Predicate;

public interface JsonPredicate<T> extends Predicate<T> {

    Class<T> getType();

    JsonElement toJson();

    default boolean tryTest(Object value) {
        if (getType().isInstance(value)) {
            return test(getType().cast(value));
        }
        return false;
    }
}
