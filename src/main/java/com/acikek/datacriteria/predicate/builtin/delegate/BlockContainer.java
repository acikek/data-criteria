package com.acikek.datacriteria.predicate.builtin.delegate;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class BlockContainer extends JsonPredicateContainer<BlockContainer.Parameter, JsonPredicate<BlockContainer.Parameter, BlockPredicate>> {

    public record Parameter(ServerWorld world, BlockPos pos) {}

    @Override
    public JsonPredicate<BlockContainer.Parameter, BlockPredicate> fromJson(JsonElement element) {
        BlockPredicate predicate = BlockPredicate.fromJson(element);
        return new JsonPredicate<>(
                predicate, Parameter.class,
                parameter -> predicate.test(parameter.world, parameter.pos),
                BlockPredicate::toJson
        );
    }
}
