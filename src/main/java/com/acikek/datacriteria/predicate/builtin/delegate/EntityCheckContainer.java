package com.acikek.datacriteria.predicate.builtin.delegate;

import com.acikek.datacriteria.predicate.Builder;
import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.entity.EntityPredicates;

import java.util.function.Predicate;

public class EntityCheckContainer extends JsonPredicateContainer<Entity, JsonPredicate<Entity, Predicate<Entity>>> {

    public static BiMap<String, Predicate<Entity>> VALUES = HashBiMap.create();

    static {
        VALUES.put("exists", EntityPredicates.VALID_ENTITY);
        VALUES.put("exists_and_living", EntityPredicates.VALID_LIVING_ENTITY);
        VALUES.put("not_mounted", EntityPredicates.NOT_MOUNTED);
        VALUES.put("is_inventory", EntityPredicates.VALID_INVENTORIES);
        VALUES.put("not_creative_nor_spectator", EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);
        VALUES.put("not_spectator", EntityPredicates.EXCEPT_SPECTATOR);
        VALUES.put("can_collide", EntityPredicates.CAN_COLLIDE);
    }

    @Override
    public JsonPredicate<Entity, Predicate<Entity>> fromJson(JsonElement element) {
        String type = element.getAsString();
        if (!VALUES.containsKey(type)) {
            throw new IllegalArgumentException("invalid entity check type");
        }
        Predicate<Entity> predicate = VALUES.get(type);
        return new JsonPredicate<>(new Builder<Entity, Predicate<Entity>>()
                .value(predicate)
                .type(Entity.class)
                .tester(predicate::test)
                .serializer(value -> new JsonPrimitive(VALUES.inverse().get(value))));
    }
}
