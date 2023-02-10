package com.acikek.datacriteria.predicate;

import com.acikek.datacriteria.DataCriteria;
import com.acikek.datacriteria.predicate.builtin.delegate.DelegateParameters;
import com.acikek.datacriteria.predicate.builtin.delegate.EntityCheckContainer;
import com.acikek.datacriteria.predicate.builtin.delegate.StateContainer;
import com.google.gson.JsonPrimitive;
import net.minecraft.advancements.critereon.*;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class JsonPredicates {

    public static Supplier<IForgeRegistry<JsonPredicateContainer<?, ?>>> REGISTRY;

    /*public static final Registry<JsonPredicateContainer<?, ?>> REGISTRY =
            (Registry<JsonPredicateContainer<?, ?>>) (Object)
            FabricRegistryBuilder.createSimple(JsonPredicateContainer.class, DataCriteria.id("container")).buildAndRegister();*/

    //#region Basic

    public static final JsonPredicateContainer<Integer, JsonPredicate<Integer, MinMaxBounds.Ints>> INT = new JsonPredicateContainer<>(element -> {
        MinMaxBounds.Ints range = MinMaxBounds.Ints.fromJson(element);
        return new JsonPredicate<>(range, Integer.class, range::matches, MinMaxBounds::serializeToJson);
    });

    public static final JsonPredicateContainer<Double, JsonPredicate<Double, MinMaxBounds.Doubles>> FLOAT = new JsonPredicateContainer<>(element -> {
        MinMaxBounds.Doubles range = MinMaxBounds.Doubles.fromJson(element);
        return new JsonPredicate<>(range, Double.class, range::matches, MinMaxBounds::serializeToJson);
    });

    public static final JsonPredicateContainer<Boolean, JsonPredicate.Equality<Boolean>> BOOLEAN = JsonPredicateContainer.createPrimitive(
            Boolean.class, JsonPrimitive::new, JsonPrimitive::getAsBoolean
    );

    public static final JsonPredicateContainer<Character, JsonPredicate.Equality<Character>> CHARACTER = JsonPredicateContainer.createPrimitive(
            Character.class, JsonPrimitive::new, JsonPrimitive::getAsCharacter
    );

    public static final JsonPredicateContainer<String, JsonPredicate.Equality<String>> STRING = JsonPredicateContainer.createPrimitive(
            String.class, JsonPrimitive::new, JsonPrimitive::getAsString
    );

    public static final JsonPredicateContainer<ResourceLocation, JsonPredicate.Equality<ResourceLocation>> IDENTIFIER = JsonPredicateContainer.createEquality(
            ResourceLocation.class,
            identifier -> new JsonPrimitive(identifier.toString()),
            element -> new ResourceLocation(element.getAsString())
    );

    //#endregion Basic

    //#region Items

    public static final JsonPredicateContainer<ItemStack, JsonPredicate<ItemStack, ItemPredicate>> ITEM = new JsonPredicateContainer<>(element -> {
        ItemPredicate predicate = ItemPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, ItemStack.class, predicate::matches, ItemPredicate::serializeToJson);
    });

    public static final JsonPredicateContainer<Map<Enchantment, Integer>, JsonPredicate<Map<Enchantment, Integer>, EnchantmentPredicate>> ENCHANTMENTS = new JsonPredicateContainer<>(element -> {
        EnchantmentPredicate predicate = EnchantmentPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, (Class<Map<Enchantment, Integer>>) (Class<?>) Map.class, predicate::containedIn, EnchantmentPredicate::serializeToJson);
    });

    //#endregion Items

    //#region Blocks

    public static final StateContainer<?> STATE = new StateContainer<>();

    public static final JsonPredicateContainer<DelegateParameters.BlockParameter, JsonPredicate<DelegateParameters.BlockParameter, BlockPredicate>> BLOCK = new JsonPredicateContainer<>(element -> {
        BlockPredicate predicate = BlockPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.BlockParameter.class, parameter -> predicate.matches(parameter.world(), parameter.pos()), BlockPredicate::serializeToJson);
    });

    public static final JsonPredicateContainer<DelegateParameters.BlockParameter, JsonPredicate<DelegateParameters.BlockParameter, FluidPredicate>> FLUID = new JsonPredicateContainer<>(element -> {
        FluidPredicate predicate = FluidPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.BlockParameter.class, parameter -> predicate.matches(parameter.world(), parameter.pos()), FluidPredicate::serializeToJson);
    });

    public static final JsonPredicateContainer<DelegateParameters.BlockParameter, JsonPredicate<DelegateParameters.BlockParameter, LightPredicate>> LIGHT_LEVEL = new JsonPredicateContainer<>(element -> {
        LightPredicate predicate = LightPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.BlockParameter.class, parameter -> predicate.matches(parameter.world(), parameter.pos()), LightPredicate::serializeToJson);
    });

    //#endregion Blocks

    //#region Entities

    public static final EntityCheckContainer ENTITY_CHECK = new EntityCheckContainer();

    public static final JsonPredicateContainer<DelegateParameters.EntityParameter, JsonPredicate<DelegateParameters.EntityParameter, EntityPredicate>> ENTITY = new JsonPredicateContainer<>(element -> {
        EntityPredicate predicate = EntityPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.EntityParameter.class, parameter -> predicate.matches(parameter.world(), parameter.pos(), parameter.entity()), EntityPredicate::serializeToJson);
    });

    public static final JsonPredicateContainer<EntityType<?>, JsonPredicate<EntityType<?>, EntityTypePredicate>> ENTITY_TYPE = new JsonPredicateContainer<>(element -> {
        EntityTypePredicate predicate = EntityTypePredicate.fromJson(element);
        return new JsonPredicate<>(predicate, (Class<EntityType<?>>) (Class<?>) EntityType.class, predicate::matches, EntityTypePredicate::serializeToJson);
    });

    public static final JsonPredicateContainer<DelegateParameters.EntityParameter, JsonPredicate<DelegateParameters.EntityParameter, EntitySubPredicate>> ENTITY_VARIANT = new JsonPredicateContainer<>(element -> {
        EntitySubPredicate predicate = EntitySubPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.EntityParameter.class, parameter -> predicate.matches(parameter.entity(), parameter.world(), parameter.pos()), EntitySubPredicate::serialize);
    });

    public static final JsonPredicateContainer<Entity, JsonPredicate<Entity, MobEffectsPredicate>> ENTITY_EFFECTS = new JsonPredicateContainer<>(element -> {
        MobEffectsPredicate predicate = MobEffectsPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, Entity.class, predicate::matches, MobEffectsPredicate::serializeToJson);
    });

    public static final JsonPredicateContainer<Entity, JsonPredicate<Entity, EntityEquipmentPredicate>> ENTITY_EQUIPMENT = new JsonPredicateContainer<>(element -> {
        EntityEquipmentPredicate predicate = EntityEquipmentPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, Entity.class, predicate::matches, EntityEquipmentPredicate::serializeToJson);
    });

    public static final JsonPredicateContainer<Entity, JsonPredicate<Entity, EntityFlagsPredicate>> ENTITY_FLAGS = new JsonPredicateContainer<>(element -> {
        EntityFlagsPredicate predicate = EntityFlagsPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, Entity.class, predicate::matches, EntityFlagsPredicate::serializeToJson);
    });

    public static final JsonPredicateContainer<DelegateParameters.EntityParameter, JsonPredicate<DelegateParameters.EntityParameter, PlayerPredicate>> PLAYER = new JsonPredicateContainer<>(element -> {
        PlayerPredicate predicate = PlayerPredicate.fromJson(element.getAsJsonObject());
        return new JsonPredicate<>(predicate, DelegateParameters.EntityParameter.class, parameter -> predicate.matches(parameter.entity(), parameter.world(), parameter.pos()), EntitySubPredicate::serialize);
    });

    public static final JsonPredicateContainer<DelegateParameters.DamageSourceParameter, JsonPredicate<DelegateParameters.DamageSourceParameter, DamageSourcePredicate>> DAMAGE_SOURCE = new JsonPredicateContainer<>(element -> {
        DamageSourcePredicate predicate = DamageSourcePredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.DamageSourceParameter.class, parameter -> predicate.matches(parameter.world(), parameter.pos(), parameter.source()), DamageSourcePredicate::serializeToJson);
    });

    public static final JsonPredicateContainer<DelegateParameters.DamageParameter, JsonPredicate<DelegateParameters.DamageParameter, DamagePredicate>> DAMAGE = new JsonPredicateContainer<>(element -> {
        DamagePredicate predicate = DamagePredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.DamageParameter.class, parameter -> predicate.matches(parameter.player(), parameter.source(), parameter.dealt(), parameter.taken(), parameter.blocked()), DamagePredicate::serializeToJson);
    });

    //#endregion Entiries

    //#region Misc

    public static final JsonPredicateContainer<DelegateParameters.DistanceParameter, JsonPredicate<DelegateParameters.DistanceParameter, DistancePredicate>> DISTANCE = new JsonPredicateContainer<>(element -> {
        DistancePredicate predicate = DistancePredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.DistanceParameter.class, parameter -> predicate.matches(parameter.x1(), parameter.y1(), parameter.z1(), parameter.x2(), parameter.y2(), parameter.z2()), DistancePredicate::serializeToJson);
    });

    public static final JsonPredicateContainer<DelegateParameters.LocationParameter, JsonPredicate<DelegateParameters.LocationParameter, LocationPredicate>> LOCATION = new JsonPredicateContainer<>(element -> {
        LocationPredicate predicate = LocationPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.LocationParameter.class, parameter -> predicate.matches(parameter.world(), parameter.x(), parameter.y(), parameter.z()), LocationPredicate::serializeToJson);
    });

    public static final JsonPredicateContainer<Tag, JsonPredicate<Tag, NbtPredicate>> NBT = new JsonPredicateContainer<>(element -> {
        NbtPredicate predicate = NbtPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, Tag.class, predicate::matches, NbtPredicate::serializeToJson);
    });

    //#endregion Misc

    public static void createRegistry(NewRegistryEvent event) {
        REGISTRY = event.create(new RegistryBuilder<JsonPredicateContainer<?, ?>>()
                .setName(DataCriteria.id("container"))
                .setIDRange(1, Integer.MAX_VALUE - 1)
                .disableSaving());
    }

    public static void register(String name, JsonPredicateContainer<?, ?> container) {
        REGISTRY.get().register(DataCriteria.id(name), container);
    }

    public static void registerMc(String name, JsonPredicateContainer<?, ?> container) {
        REGISTRY.get().register(new ResourceLocation(name), container);
    }

    public static void register() {
        // Basic
        register("int", INT);
        register("float", FLOAT);
        register("bool", BOOLEAN);
        register("char", CHARACTER);
        register("string", STRING);
        register("id", IDENTIFIER);
        // Items
        registerMc("item", ITEM);
        registerMc("enchantment", ENCHANTMENTS);
        // Blocks
        registerMc("state", STATE);
        registerMc("block", BLOCK);
        registerMc("fluid", FLUID);
        registerMc("light_level", LIGHT_LEVEL);
        // Entities
        registerMc("entity_check", ENTITY_CHECK);
        registerMc("entity", ENTITY);
        registerMc("entity_type", ENTITY_TYPE);
        registerMc("entity_variant", ENTITY_VARIANT);
        registerMc("entity_effects", ENTITY_EFFECTS);
        registerMc("entity_equipment", ENTITY_EQUIPMENT);
        registerMc("entity_flags", ENTITY_FLAGS);
        registerMc("player", PLAYER);
        registerMc("damage_source", DAMAGE_SOURCE);
        registerMc("damage", DAMAGE);
        // Misc
        registerMc("distance", DISTANCE);
        registerMc("location", LOCATION);
        registerMc("nbt", NBT);
    }
}
