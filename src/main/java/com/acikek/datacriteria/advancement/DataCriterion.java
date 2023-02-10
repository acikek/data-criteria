package com.acikek.datacriteria.advancement;

import com.acikek.datacriteria.DataCriteria;
import com.acikek.datacriteria.predicate.JsonPredicate;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;

public class DataCriterion extends SimpleCriterionTrigger<DataCriterion.Conditions> {

    public ResourceLocation id;
    public List<Parameter<?, ?>> containers;

    public DataCriterion(ResourceLocation id, List<Parameter<?, ?>> containers) {
        this.id = id;
        this.containers = containers;
    }

    public static DataCriterion fromJson(ResourceLocation id, JsonObject obj) {
        boolean defaultOptional = GsonHelper.getAsBoolean(obj, "optional", false);
        JsonArray parameters = GsonHelper.getAsJsonArray(obj, "parameters");
        List<Parameter<?, ?>> containers = new ArrayList<>();
        for (JsonElement element : parameters) {
            containers.add(Parameter.fromJson(element.getAsJsonObject(), defaultOptional));
        }
        return new DataCriterion(id, containers);
    }

    @Override
    protected Conditions createInstance(JsonObject obj, EntityPredicate.Composite playerPredicate, DeserializationContext predicateDeserializer) {
        List<? extends Tuple<? extends Parameter<?, ?>, ? extends JsonPredicate<?, ?>>> result = containers.stream()
                .map(parameter -> {
                    if (!obj.has(parameter.name) && !parameter.optional) {
                        throw new IllegalStateException("missing predicate '" + parameter.name + "'");
                    }
                    JsonElement element = obj.get(parameter.name);
                    var predicate = parameter.container.checkedFromJson(element);
                    if (obj.has(parameter.name) && predicate == null) {
                        throw new IllegalStateException("predicate '" + parameter.name + "' deserialized as null");
                    }
                    return new Tuple<>(parameter, predicate);
                })
                .toList();
        return new Conditions(playerPredicate, result);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public void trigger(boolean debug, ServerPlayer player, Object... inputs) {
        trigger(player, conditions -> conditions.matches(debug, inputs));
    }

    public class Conditions extends AbstractCriterionTriggerInstance {

        public List<? extends Tuple<? extends Parameter<?, ?>, ? extends JsonPredicate<?, ?>>> values;

        public Conditions(EntityPredicate.Composite entity, List<? extends Tuple<? extends Parameter<?, ?>, ? extends JsonPredicate<?, ?>>> values) {
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
                Parameter<?, ?> parameter = values.get(i).getA();
                if (debug) {
                    DataCriteria.LOGGER.info("Parameter #{}: {}", i + 1, parameter.id);
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
                JsonPredicate<?, ?> predicate = values.get(i).getB();
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
        public JsonObject serializeToJson(SerializationContext predicateSerializer) {
            JsonObject obj = new JsonObject();
            for (var pair : values) {
                if (pair.getB() != null) {
                    obj.add(pair.getA().name, pair.getB().toJson());
                }
            }
            return obj;
        }
    }
}
