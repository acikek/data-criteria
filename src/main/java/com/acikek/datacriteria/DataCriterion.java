package com.acikek.datacriteria;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class DataCriterion extends AbstractCriterion<DataCriterion.Conditions> {

    public record PredicatePair<T, P extends JsonPredicate<T>>(String name, P predicate) {

        public static <T, P extends JsonPredicate<T>> PredicatePair<T, P> fromContainerPair(JsonPredicateContainer.Pair<T, P> containerPair, JsonObject obj) {
            JsonElement element = obj.get(containerPair.name());
            return new PredicatePair<>(containerPair.name(), containerPair.container().fromJson(element));
        }
    }

    public Identifier id;
    public List<JsonPredicateContainer.Pair<?, ?>> containers;

    public DataCriterion(Identifier id, List<JsonPredicateContainer.Pair<?, ?>> containers) {
        this.id = id;
        this.containers = containers;
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        List<? extends PredicatePair<?, ?>> result = containers.stream()
                .map(pair -> PredicatePair.fromContainerPair(pair, obj))
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

        public List<? extends PredicatePair<?, ?>> values;

        public Conditions(EntityPredicate.Extended entity, List<? extends PredicatePair<?, ?>> values) {
            super(id, entity);
            this.values = values;
        }

        public boolean matches(Object[] inputs) {
            for (int i = 0; i < inputs.length; i++) {
                JsonPredicate<?> predicate = values.get(i).predicate();
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
                if (pair.predicate() != null) {
                    obj.add(pair.name(), pair.predicate().toJson());
                }
            }
            return obj;
        }
    }
}
