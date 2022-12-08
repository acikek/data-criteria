package com.acikek.datacriteria.predicate;

import com.google.gson.JsonElement;

public abstract class JsonPredicateContainer<T, P extends JsonPredicate<T>> {

    public abstract P fromJson(JsonElement element);
}
