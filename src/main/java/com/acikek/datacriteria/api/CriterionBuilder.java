package com.acikek.datacriteria.api;

import com.acikek.datacriteria.advancement.DataCriterion;
import com.acikek.datacriteria.impl.CriterionBuilderImpl;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import net.minecraft.resources.ResourceLocation;

public interface CriterionBuilder {

    static CriterionBuilder create(ResourceLocation id) {
        return new CriterionBuilderImpl(id);
    }

    CriterionBuilder addParameter(String name, JsonPredicateContainer<?, ?> container, boolean optional);

    default CriterionBuilder addParameter(String name, JsonPredicateContainer<?, ?> container) {
        return addParameter(name, container, false);
    }

    DataCriterion build();
}
