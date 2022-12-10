package com.acikek.datacriteria.predicate;

import com.acikek.datacriteria.DataCriteria;
import com.acikek.datacriteria.predicate.builtin.IdentifierContainer;
import com.acikek.datacriteria.predicate.builtin.NumberRangeContainer;
import com.acikek.datacriteria.predicate.builtin.PrimitiveContainer;
import com.acikek.datacriteria.predicate.builtin.delegate.BlockContainer;
import com.acikek.datacriteria.predicate.builtin.delegate.ItemContainer;
import com.acikek.datacriteria.predicate.builtin.delegate.LocationContainer;
import com.acikek.datacriteria.predicate.builtin.delegate.StateContainer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unchecked")
public class JsonPredicates {

    public static final Registry<JsonPredicateContainer<?, ?>> REGISTRY =
            (Registry<JsonPredicateContainer<?, ?>>) (Object)
            FabricRegistryBuilder.createSimple(JsonPredicateContainer.class, DataCriteria.id("container")).buildAndRegister();

    public static final NumberRangeContainer<Integer, NumberRangeContainer.IntPredicate> INT = NumberRangeContainer.getInt();
    public static final NumberRangeContainer<Double, NumberRangeContainer.FloatPredicate> FLOAT = NumberRangeContainer.getFloat();
    public static final PrimitiveContainer<Boolean> BOOLEAN = new PrimitiveContainer<>(PrimitiveContainer.Type.BOOLEAN);
    public static final PrimitiveContainer<Character> CHARACTER = new PrimitiveContainer<>(PrimitiveContainer.Type.CHARACTER);
    public static final PrimitiveContainer<String> STRING = new PrimitiveContainer<>(PrimitiveContainer.Type.STRING);
    public static final IdentifierContainer IDENTIFIER = new IdentifierContainer();
    public static final ItemContainer ITEM = new ItemContainer();
    public static final BlockContainer BLOCK = new BlockContainer();
    public static final StateContainer<BlockState> BLOCK_STATE = StateContainer.getBlock();
    public static final StateContainer<FluidState> FLUID_STATE = StateContainer.getFluid();
    public static final LocationContainer LOCATION = new LocationContainer();

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
