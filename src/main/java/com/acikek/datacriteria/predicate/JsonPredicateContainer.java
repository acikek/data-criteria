package com.acikek.datacriteria.predicate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonHelper;

import java.util.function.Function;

public abstract class JsonPredicateContainer<T, P extends JsonPredicate<T>> {

    //public abstract Function<T, P> getConstructor();

    public abstract P fromJson(JsonElement element);

    public record Pair<T, P extends JsonPredicate<T>>(String name, JsonPredicateContainer<T, P> container) {

        public static <T, P extends JsonPredicate<T>> Pair<T, P> fromJson(JsonObject obj) {
            String name = JsonHelper.getString(obj, "name");
            JsonPredicateContainer<T, P> container = null; // TODO registry
            return new Pair<>(name, container);
        }
    }
}
