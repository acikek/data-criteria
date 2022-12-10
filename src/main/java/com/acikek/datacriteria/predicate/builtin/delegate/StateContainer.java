package com.acikek.datacriteria.predicate.builtin.delegate;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.state.State;

public class StateContainer<T extends State<?, ?>> extends JsonPredicateContainer<T, StateContainer.Predicate<T>> {

    public Class<T> type;

    public StateContainer(Class<T> type) {
        this.type = type;
    }

    @Override
    public Predicate<T> fromJson(JsonElement element) {
        return new Predicate<>(StatePredicate.fromJson(element), type);
    }

    public static StateContainer<BlockState> getBlock() {
        return new StateContainer<>(BlockState.class);
    }

    public static StateContainer<FluidState> getFluid() {
        return new StateContainer<>(FluidState.class);
    }

    public static class Predicate<T extends State<?, ?>> extends JsonPredicate<T, StatePredicate> {

        public Predicate(StatePredicate predicate, Class<T> type) {
            super(new Builder<T, StatePredicate>()
                    .value(predicate)
                    .type(type)
                    .serializer(StatePredicate::toJson));
        }

        @Override
        public boolean test(T t) {
            if (type == BlockState.class) {
                return value.test((BlockState) t);
            }
            else return value.test((FluidState) t);
        }
    }
}
