package com.acikek.datacriteria.predicate.builtin.delegate;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.block.state.StateHolder;

@SuppressWarnings("unchecked")
public class StateContainer<S extends StateHolder<?, S>> extends JsonPredicateContainer.Typed<DelegateParameters.StateParameter<S>, JsonPredicate<DelegateParameters.StateParameter<S>, StatePropertiesPredicate>> {

    public StateContainer() {
        super((Class<DelegateParameters.StateParameter<S>>) (Class<?>) DelegateParameters.StateParameter.class);
    }

    @Override
    public JsonPredicate<DelegateParameters.StateParameter<S>, StatePropertiesPredicate> fromJson(JsonElement element) {
        StatePropertiesPredicate predicate = StatePropertiesPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, type, parameter -> predicate.matches(parameter.stateManager(), parameter.state()), StatePropertiesPredicate::serializeToJson);
    }
}
