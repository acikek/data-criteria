package com.acikek.datacriteria.advancement;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.acikek.datacriteria.predicate.JsonPredicates;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class Parameter<T, P extends JsonPredicate<T, ?>> {

    public String name;
    public boolean optional;
    public JsonPredicateContainer<T, P> container;

    public Parameter(String name, boolean optional, JsonPredicateContainer<T, P> container) {
        this.name = name;
        this.optional = optional;
        this.container = container;
    }

    @SuppressWarnings("unchecked")
    public static <T, P extends JsonPredicate<T, ?>> Parameter<T, P> fromJson(JsonObject obj) {
        String name = JsonHelper.getString(obj, "name");
        boolean optional = JsonHelper.getBoolean(obj, "optional", false);
        JsonPredicateContainer<?, ?> container = JsonPredicates.REGISTRY.get(new Identifier(JsonHelper.getString(obj, "type")));
        return new Parameter<>(name, optional, (JsonPredicateContainer<T, P>) container);
    }
}
