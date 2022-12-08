package com.acikek.datacriteria.advancement;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.List;

public class DataCriterion extends AbstractCriterion<DataCriterion.Conditions> {

    public Identifier id;
    public List<Parameter<?, ?>> containers;

    public DataCriterion(Identifier id, List<Parameter<?, ?>> containers) {
        this.id = id;
        this.containers = containers;
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        List<? extends Pair<? extends Parameter<?, ?>, ? extends JsonPredicate<?>>> result = containers.stream()
                .map(parameter -> {
                    if (!obj.has(parameter.name) && !parameter.optional) {
                        throw new IllegalStateException("missing predicate '" + parameter.name + "'");
                    }
                    JsonElement element = obj.get(parameter.name);
                    return new Pair<>(parameter, parameter.container.fromJson(element));
                })
                .toList();
        return new Conditions(playerPredicate, result);
    }

    @Override
    public Identifier getId() {
        return id;
    }

    public void trigger(ServerPlayerEntity player, Object... inputs) {
        trigger(player, conditions -> conditions.matches(inputs));
    }

    public class Conditions extends AbstractCriterionConditions {

        public List<? extends Pair<? extends Parameter<?, ?>, ? extends JsonPredicate<?>>> values;

        public Conditions(EntityPredicate.Extended entity, List<? extends Pair<? extends Parameter<?, ?>, ? extends JsonPredicate<?>>> values) {
            super(id, entity);
            this.values = values;
        }

        public boolean matches(Object[] inputs) {
            if (inputs.length > values.size()) {
                throw new IllegalStateException("too many arguments given when triggering criterion '" + id + "'; " + inputs.length + " provided, maximum is " + values.size());
            }
            for (int i = 0; i < values.size(); i++) {
                Parameter<?, ?> parameter = values.get(i).getLeft();
                if (i >= inputs.length) {
                    if (parameter.optional) {
                        continue;
                    }
                    throw new IllegalStateException("no value given for parameter '" + parameter.name + "' in criterion '" + id + "'");
                }
                JsonPredicate<?> predicate = values.get(i).getRight();
                if (predicate != null && !predicate.tryTest(inputs[i])) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject obj = new JsonObject();
            for (var pair : values) {
                if (pair.getRight() != null) {
                    obj.add(pair.getLeft().name, pair.getRight().toJson());
                }
            }
            return obj;
        }
    }
}