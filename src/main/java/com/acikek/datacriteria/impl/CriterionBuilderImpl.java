package com.acikek.datacriteria.impl;

import com.acikek.datacriteria.advancement.DataCriterion;
import com.acikek.datacriteria.advancement.Parameter;
import com.acikek.datacriteria.api.CriterionBuilder;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.acikek.datacriteria.predicate.JsonPredicates;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class CriterionBuilderImpl implements CriterionBuilder {

    public ResourceLocation id;
    public List<Parameter<?, ?>> parameters = new ArrayList<>();

    public CriterionBuilderImpl(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public CriterionBuilder addParameter(String name, JsonPredicateContainer<?, ?> container, boolean optional) {
        ResourceLocation containerId = JsonPredicates.REGISTRY.get().getKey(container);
        parameters.add(new Parameter<>(name, container, optional, containerId));
        return this;
    }

    @Override
    public DataCriterion build() {
        return new DataCriterion(id, parameters);
    }
}
