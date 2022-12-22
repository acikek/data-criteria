package com.acikek.datacriteria.predicate.builtin;

import com.acikek.datacriteria.predicate.Builder;
import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.EnumUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ListContainer<T, P extends JsonPredicate<T, ?>, C extends JsonPredicateContainer<T, P>> extends JsonPredicateContainer.Typed<List<T>, ListContainer.Predicate<T, P>> {

    public C container;

    public ListContainer(C container) {
        super((Class<List<T>>) (Class<?>) List.class);
        this.container = container;
    }

    @Override
    public Predicate<T, P> fromJson(JsonElement element) {
        boolean isArray = element.isJsonArray();
        List<P> predicates = new ArrayList<>();
        JsonArray predicateArray = isArray
                ? element.getAsJsonArray()
                : JsonHelper.getArray(element.getAsJsonObject(), "values");
        for (JsonElement entry : predicateArray) {
            var predicate = container.fromJson(entry);
            if (predicate == null) {
                throw new IllegalArgumentException("entry '" + entry + "' deserialized as null");
            }
            predicates.add(predicate);
        }
        Predicate.Type matchType = isArray
                ? Predicate.Type.ALL
                : EnumUtils.getEnumIgnoreCase(Predicate.Type.class, JsonHelper.getString(element.getAsJsonObject(), "match"));
        return new Predicate<>(predicates, type, matchType);
    }

    public static class Predicate<T, P extends JsonPredicate<T, ?>> extends JsonPredicate<List<T>, List<P>> {

        public enum Type {
            ALL,
            ANY,
            NONE
        }

        public Type matchType;

        public Predicate(List<P> values, Class<List<T>> type, Type matchType) {
            super(new Builder<List<T>, List<P>>()
                    .value(values)
                    .type(type));
            this.matchType = matchType;
        }

        @Override
        public boolean test(List<T> ts) {
            for (int i = 0; i < value.size(); i++) {
                P predicate = value.get(i);
                T item = ts.get(i);
                boolean result = predicate.test(item);
                if (result) {
                    if (matchType == Type.NONE) {
                        return false;
                    }
                    else if (matchType == Type.ANY) {
                        return true;
                    }
                }
                else if (matchType == Type.ALL) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public JsonElement toJson() {
            JsonObject obj = new JsonObject();
            obj.add("match", new JsonPrimitive(matchType.name().toLowerCase()));
            JsonArray predicates = new JsonArray();
            for (P predicate : value) {
                predicates.add(predicate.toJson());
            }
            obj.add("values", predicates);
            return obj;
        }
    }
}
