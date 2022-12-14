package com.acikek.datacriteria.advancement;

import com.acikek.datacriteria.api.DataCriteriaAPI;
import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.acikek.datacriteria.predicate.JsonPredicates;
import com.acikek.datacriteria.predicate.builtin.ListContainer;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class Parameter<T, P extends JsonPredicate<T, ?>> {

    public String name;
    public JsonPredicateContainer<T, P> container;
    public boolean optional;

    public Identifier id;

    public Parameter(String name, JsonPredicateContainer<T, P> container, boolean optional, Identifier id) {
        this.name = name;
        this.container = container;
        this.optional = optional;
        this.id = id;
    }

    @SuppressWarnings("unchecked")
    public static <T, P extends JsonPredicate<T, ?>> Parameter<T, P> fromJson(JsonObject obj) {
        String name = JsonHelper.getString(obj, "name");
        boolean listOf = JsonHelper.hasString(obj, "list_of");
        Identifier id = new Identifier(JsonHelper.getString(obj, listOf ? "list_of" : "type"));
        JsonPredicateContainer<T, P> containerType = (JsonPredicateContainer<T, P>) JsonPredicates.REGISTRY.get(id);
        JsonPredicateContainer<?, ?> container = listOf
                ? new ListContainer<>(containerType)
                : containerType;
        boolean optional = JsonHelper.getBoolean(obj, "optional", false);
        return new Parameter<>(name, (JsonPredicateContainer<T, P>) container, optional, id);
    }
}
