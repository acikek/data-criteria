package com.acikek.datacriteria.predicate;

import com.google.gson.JsonElement;

import java.util.function.Function;

public class Builder<T, P> {

    public static class Single<T> extends Builder<T, T> {}

    P value;
    Class<T> type;
    Function<T, Boolean> tester;
    Function<P, JsonElement> serializer;;

    public Builder<T, P> value(P value) {
        this.value = value;
        return this;
    }

    public Builder<T, P> type(Class<T> type) {
        this.type = type;
        return this;
    }

    public Builder<T, P> tester(Function<T, Boolean> tester) {
        this.tester = tester;
        return this;
    }

    public Builder<T, P> serializer(Function<P, JsonElement> serializer) {
        this.serializer = serializer;
        return this;
    }
}
