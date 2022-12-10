package com.acikek.datacriteria.predicate.builtin;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

@SuppressWarnings("unchecked")
public class PrimitiveContainer<T> extends JsonPredicateContainer<T, PrimitiveContainer.PrimitivePredicate<T>> {

    public enum Type {

        BOOLEAN(Boolean.class),
        CHARACTER(Character.class),
        STRING(String.class);

        public final Class<?> clazz;

        Type(Class<?> clazz) {
            this.clazz = clazz;
        }
    }

    public Type type;

    public PrimitiveContainer(Type type) {
        this.type = type;
    }

    @Override
    public PrimitivePredicate<T> fromJson(JsonElement element) {
        JsonPrimitive primitive = element.getAsJsonPrimitive();
        Object value = switch (type) {
            case BOOLEAN -> primitive.getAsBoolean();
            case CHARACTER -> primitive.getAsCharacter();
            case STRING -> primitive.getAsString();
        };
        return new PrimitivePredicate<>((T) value, type);
    }

    public static class PrimitivePredicate<T> extends JsonPredicate.Equality<T> {

        public Type type;

        public PrimitivePredicate(T value, Type type) {
            super(value, new Single.Builder<T>().type((Class<T>) type.clazz));
            this.type = type;
        }

        @Override
        public JsonElement toJson() {
            return switch (type) {
                case BOOLEAN -> new JsonPrimitive((boolean) value);
                case CHARACTER -> new JsonPrimitive((char) value);
                case STRING -> new JsonPrimitive((String) value);
            };
        }
    }
}


