package com.acikek.datacriteria.predicate.builtin.delegate;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.server.world.ServerWorld;

public class LocationContainer extends JsonPredicateContainer<LocationContainer.Parameter, LocationContainer.Predicate> {

    public record Parameter(ServerWorld world, double x, double y, double z) {}

    @Override
    public Predicate fromJson(JsonElement element) {
        return new Predicate(LocationPredicate.fromJson(element));
    }

    public static class Predicate extends JsonPredicate<Parameter, LocationPredicate> {

        public Predicate(LocationPredicate predicate) {
            super(new Builder<Parameter, LocationPredicate>()
                    .value(predicate)
                    .type(Parameter.class)
                    .tester(parameter -> predicate.test(parameter.world, parameter.x, parameter.y, parameter.z))
                    .serializer(LocationPredicate::toJson));
        }
    }
}
