package com.acikek.datacriteria.predicate.builtin;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class BooleanContainer extends JsonPredicateContainer<Boolean, BooleanContainer.Predicate> {

    @Override
    public Predicate fromJson(JsonElement element) {
        if (!element.getAsJsonPrimitive().isBoolean()) {
            throw new IllegalStateException("boolean predicate must be 'true' or 'false'");
        }
        return new Predicate(element.getAsBoolean());
    }

    public static class Predicate extends JsonPredicate.Single<Boolean> {

        public Predicate(boolean value) {
            super(new Single.Builder<Boolean>()
                    .value(value)
                    .type(Boolean.class)
                    .tester(bool -> bool == value)
                    .serializer(JsonPrimitive::new));
        }
    }
}
