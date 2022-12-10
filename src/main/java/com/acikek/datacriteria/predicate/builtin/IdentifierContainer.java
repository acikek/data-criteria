package com.acikek.datacriteria.predicate.builtin;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.Identifier;

public class IdentifierContainer extends JsonPredicateContainer<Identifier, IdentifierContainer.Predicate> {

    @Override
    public Predicate fromJson(JsonElement element) {
        return new Predicate(Identifier.tryParse(element.getAsString()));
    }

    public static class Predicate extends JsonPredicate.Equality<Identifier> {

        public Predicate(Identifier value) {
            super(value, new Single.Builder<Identifier>()
                    .type(Identifier.class)
                    .serializer(id -> new JsonPrimitive(id.toString())));
        }
    }
}
