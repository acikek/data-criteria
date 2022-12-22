package com.acikek.datacriteria.advancement;

import com.acikek.datacriteria.DataCriteria;
import com.acikek.datacriteria.predicate.JsonPredicate;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DataCriterion extends AbstractCriterion<DataCriterion.Conditions> {

    public Identifier id;
    public List<Parameter<?, ?>> containers;

    public DataCriterion(Identifier id, List<Parameter<?, ?>> containers) {
        this.id = id;
        this.containers = containers;
    }

    public static DataCriterion fromJson(Identifier id, JsonObject obj) {
        boolean defaultOptional = JsonHelper.getBoolean(obj, "optional", false);
        JsonArray parameters = JsonHelper.getArray(obj, "parameters");
        List<Parameter<?, ?>> containers = new ArrayList<>();
        for (JsonElement element : parameters) {
            containers.add(Parameter.fromJson(element.getAsJsonObject(), defaultOptional));
        }
        return new DataCriterion(id, containers);
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        List<? extends Pair<? extends Parameter<?, ?>, ? extends JsonPredicate<?, ?>>> result = containers.stream()
                .map(parameter -> {
                    if (!obj.has(parameter.name) && !parameter.optional) {
                        throw new IllegalStateException("missing predicate '" + parameter.name + "'");
                    }
                    JsonElement element = obj.get(parameter.name);
                    var predicate = parameter.container.fromJson(element);
                    if (obj.has(parameter.name) && predicate == null) {
                        throw new IllegalStateException("predicate '" + parameter.name + "' deserialized as null");
                    }
                    return new Pair<>(parameter, predicate);
                })
                .toList();
        return new Conditions(playerPredicate, result);
    }

    @Override
    public Identifier getId() {
        return id;
    }

    public void trigger(boolean debug, ServerPlayerEntity player, Object... inputs) {
        trigger(player, conditions -> conditions.matches(debug, inputs));
    }

    public class Conditions extends AbstractCriterionConditions {

        public List<? extends Pair<? extends Parameter<?, ?>, ? extends JsonPredicate<?, ?>>> values;

        public Conditions(EntityPredicate.Extended entity, List<? extends Pair<? extends Parameter<?, ?>, ? extends JsonPredicate<?, ?>>> values) {
            super(id, entity);
            this.values = values;
        }

        public boolean matches(boolean debug, Object[] inputs) {
            if (debug) {
                DataCriteria.LOGGER.info("");
                DataCriteria.LOGGER.info("-- STARTING NEW MATCH CALL --");
            }
            if (inputs.length > values.size()) {
                throw new IllegalStateException("too many arguments given when triggering criterion '" + id + "'; " + inputs.length + " provided, maximum is " + values.size());
            }
            for (int i = 0; i < values.size(); i++) {
                Parameter<?, ?> parameter = values.get(i).getLeft();
                if (debug) {
                    DataCriteria.LOGGER.info("Parameter #{}: {}", i, parameter.id);
                }
                if (i >= inputs.length) {
                    if (parameter.optional) {
                        if (debug) {
                            DataCriteria.LOGGER.info("No input provided for optional parameter. Skipping...");
                        }
                        continue;
                    }
                    throw new IllegalStateException("no value given for parameter '" + parameter.name + "' in criterion '" + id + "'");
                }
                JsonPredicate<?, ?> predicate = values.get(i).getRight();
                if (predicate != null && !predicate.tryTest(inputs[i], debug)) {
                    if (debug) {
                        DataCriteria.LOGGER.info("Check failed! Aborting match...");
                    }
                    return false;
                }
            }
            if (debug) {
                DataCriteria.LOGGER.info("Match passed!");
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
