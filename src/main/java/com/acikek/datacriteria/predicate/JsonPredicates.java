package com.acikek.datacriteria.predicate;

import com.acikek.datacriteria.DataCriteria;
import com.acikek.datacriteria.predicate.builtin.delegate.DelegateParameters;
import com.acikek.datacriteria.predicate.builtin.delegate.StateContainer;
import com.google.gson.JsonPrimitive;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unchecked")
public class JsonPredicates {

    public static final Registry<JsonPredicateContainer<?, ?>> REGISTRY =
            (Registry<JsonPredicateContainer<?, ?>>) (Object)
            FabricRegistryBuilder.createSimple(JsonPredicateContainer.class, DataCriteria.id("container")).buildAndRegister();

    public static final JsonPredicateContainer<Integer, JsonPredicate<Integer, NumberRange.IntRange>> INT = new JsonPredicateContainer<>(
            element -> {
                NumberRange.IntRange range = NumberRange.IntRange.fromJson(element);
                return new JsonPredicate<>(range, Integer.class, range::test, NumberRange::toJson);
            }
    );

    public static final JsonPredicateContainer<Double, JsonPredicate<Double, NumberRange.FloatRange>> FLOAT = new JsonPredicateContainer<>(
            element -> {
                NumberRange.FloatRange range = NumberRange.FloatRange.fromJson(element);
                return new JsonPredicate<>(range, Double.class, range::test, NumberRange::toJson);
            }
    );

    public static final JsonPredicateContainer<Boolean, JsonPredicate.Equality<Boolean>> BOOLEAN = JsonPredicateContainer.createPrimitive(
            Boolean.class, JsonPrimitive::new, JsonPrimitive::getAsBoolean
    );

    public static final JsonPredicateContainer<Character, JsonPredicate.Equality<Character>> CHARACTER = JsonPredicateContainer.createPrimitive(
            Character.class, JsonPrimitive::new, JsonPrimitive::getAsCharacter
    );

    public static final JsonPredicateContainer<String, JsonPredicate.Equality<String>> STRING = JsonPredicateContainer.createPrimitive(
            String.class, JsonPrimitive::new, JsonPrimitive::getAsString
    );

    public static final JsonPredicateContainer<Identifier, JsonPredicate.Equality<Identifier>> IDENTIFIER = JsonPredicateContainer.createEquality(
            Identifier.class,
            identifier -> new JsonPrimitive(identifier.toString()),
            element -> new Identifier(element.getAsString())
    );

    public static final JsonPredicateContainer<ItemStack, JsonPredicate<ItemStack, ItemPredicate>> ITEM = new JsonPredicateContainer<>(
            element -> {
                ItemPredicate predicate = ItemPredicate.fromJson(element);
                return new JsonPredicate<>(predicate, ItemStack.class, predicate::test, ItemPredicate::toJson);
            }
    );

    public static final StateContainer<BlockState> BLOCK_STATE = StateContainer.getBlock();
    public static final StateContainer<FluidState> FLUID_STATE = StateContainer.getFluid();

    public static final JsonPredicateContainer<DelegateParameters.Block, JsonPredicate<DelegateParameters.Block, BlockPredicate>> BLOCK = new JsonPredicateContainer<>(
            element -> {
                BlockPredicate predicate = BlockPredicate.fromJson(element);
                return new JsonPredicate<>(predicate, DelegateParameters.Block.class, parameter -> predicate.test(parameter.world(), parameter.pos()), BlockPredicate::toJson);
            }
    );

    public static final JsonPredicateContainer<DelegateParameters.Location, JsonPredicate<DelegateParameters.Location, LocationPredicate>> LOCATION = new JsonPredicateContainer<>(
            element -> {
                LocationPredicate predicate = LocationPredicate.fromJson(element);
                return new JsonPredicate<>(predicate, DelegateParameters.Location.class, parameter -> predicate.test(parameter.world(), parameter.x(), parameter.y(), parameter.z()), LocationPredicate::toJson);
            }
    );

    public static void register(String name, JsonPredicateContainer<?, ?> container) {
        Registry.register(REGISTRY, DataCriteria.id(name), container);
    }

    static {
        register("int", INT);
        register("float", FLOAT);
        register("boolean", BOOLEAN);
        register("character", CHARACTER);
        register("string", STRING);
        register("identifier", IDENTIFIER);
        register("item", ITEM);
        register("block", BLOCK);
        register("block_state", BLOCK_STATE);
        register("fluid_state", FLUID_STATE);
        register("location", LOCATION);
    }
}
