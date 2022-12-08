package com.acikek.datacriteria.predicate.builtin;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import net.minecraft.predicate.NumberRange;

public class IntRangeContainer extends JsonPredicateContainer<Integer, IntRangeContainer.Predicate> {

    public static class Predicate implements JsonPredicate<Integer> {

        public NumberRange.IntRange intRange;

        public Predicate(NumberRange.IntRange intRange) {
            this.intRange = intRange;
        }

        @Override
        public Class<Integer> getType() {
            return Integer.class;
        }

        @Override
        public JsonElement toJson() {
            return intRange.toJson();
        }

        @Override
        public boolean test(Integer integer) {
            return intRange.test(integer);
        }
    }

    @Override
    public Predicate fromJson(JsonElement element) {
        return new Predicate(NumberRange.IntRange.fromJson(element));
    }
}
