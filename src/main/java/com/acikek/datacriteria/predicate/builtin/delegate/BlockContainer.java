package com.acikek.datacriteria.predicate.builtin.delegate;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class BlockContainer extends JsonPredicateContainer<BlockContainer.Parameter, BlockContainer.Predicate> {

    public record Parameter(ServerWorld world, BlockPos pos) {}

    @Override
    public Predicate fromJson(JsonElement element) {
        return new Predicate(BlockPredicate.fromJson(element));
    }

    public static class Predicate extends JsonPredicate<Parameter, BlockPredicate> {

        public Predicate(BlockPredicate predicate) {
            super(new Builder<Parameter, BlockPredicate>()
                    .value(predicate)
                    .type(Parameter.class)
                    .tester(parameter -> predicate.test(parameter.world, parameter.pos))
                    .serializer(BlockPredicate::toJson));
        }
    }
}
