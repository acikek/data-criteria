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

    public Identifier id;

    public Parameter(String name, boolean optional, JsonPredicateContainer<T, P> container, Identifier id) {
        this.name = name;
        this.optional = optional;
        this.container = container;
        this.id = id;
    }

    @SuppressWarnings("unchecked")
    public static <T, P extends JsonPredicate<T, ?>> Parameter<T, P> fromJson(JsonObject obj) {
        String name = JsonHelper.getString(obj, "name");
        boolean optional = JsonHelper.getBoolean(obj, "optional", false);
        Identifier id = new Identifier(JsonHelper.getString(obj, "type"));
        JsonPredicateContainer<?, ?> container = JsonPredicates.REGISTRY.get(id);
        return new Parameter<>(name, optional, (JsonPredicateContainer<T, P>) container, id);
    }
}
