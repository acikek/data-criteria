package com.acikek.datacriteria.predicate.builtin.delegate;

import com.acikek.datacriteria.predicate.Builder;
import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;

import java.util.function.Predicate;

public class EntityCheckContainer extends JsonPredicateContainer<Entity, JsonPredicate<Entity, Predicate<Entity>>> {

    public static BiMap<String, Predicate<Entity>> VALUES = HashBiMap.create();

    static {
        VALUES.put("exists", EntitySelector.ENTITY_STILL_ALIVE);
        VALUES.put("exists_and_living", EntitySelector.LIVING_ENTITY_STILL_ALIVE);
        VALUES.put("not_mounted", EntitySelector.ENTITY_NOT_BEING_RIDDEN);
        VALUES.put("is_inventory", EntitySelector.CONTAINER_ENTITY_SELECTOR);
        VALUES.put("not_creative_nor_spectator", EntitySelector.NO_CREATIVE_OR_SPECTATOR);
        VALUES.put("not_spectator", EntitySelector.NO_SPECTATORS);
        VALUES.put("can_collide", EntitySelector.CAN_BE_COLLIDED_WITH);
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
