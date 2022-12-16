package com.acikek.datacriteria.predicate.builtin.delegate;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.state.State;

@SuppressWarnings("unchecked")
public class StateContainer<S extends State<?, S>> extends JsonPredicateContainer.Typed<DelegateParameters.StateParameter<S>, JsonPredicate<DelegateParameters.StateParameter<S>, StatePredicate>> {

    public StateContainer() {
        super((Class<DelegateParameters.StateParameter<S>>) (Class<?>) DelegateParameters.StateParameter.class);
    }

    @Override
    public JsonPredicate<DelegateParameters.StateParameter<S>, StatePredicate> fromJson(JsonElement element) {
        StatePredicate predicate = StatePredicate.fromJson(element);
        return new JsonPredicate<>(predicate, type, parameter -> predicate.test(parameter.stateManager(), parameter.state()), StatePredicate::toJson);
    }
}
