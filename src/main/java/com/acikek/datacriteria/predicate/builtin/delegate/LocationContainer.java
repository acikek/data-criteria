package com.acikek.datacriteria.predicate.builtin.delegate;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.server.world.ServerWorld;

public class LocationContainer extends JsonPredicateContainer<LocationContainer.Parameter, JsonPredicate<LocationContainer.Parameter, LocationPredicate>> {

    public record Parameter(ServerWorld world, double x, double y, double z) {}

    @Override
    public JsonPredicate<Parameter, LocationPredicate> fromJson(JsonElement element) {
        LocationPredicate predicate = LocationPredicate.fromJson(element);
        return new JsonPredicate<>(
                predicate, Parameter.class,
                parameter -> predicate.test(parameter.world, parameter.x, parameter.y, parameter.z),
                LocationPredicate::toJson
        );
    }
}
