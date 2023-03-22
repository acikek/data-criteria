package com.acikek.datacriteria.predicate;

import com.acikek.datacriteria.DataCriteria;
import com.acikek.datacriteria.predicate.builtin.delegate.DelegateParameters;
import com.acikek.datacriteria.predicate.builtin.delegate.EntityCheckContainer;
import com.acikek.datacriteria.predicate.builtin.delegate.StateContainer;
import com.google.gson.JsonPrimitive;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.predicate.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.Map;

@SuppressWarnings("unchecked")
public class JsonPredicates {

    public static final Registry<JsonPredicateContainer<?, ?>> REGISTRY =
            (Registry<JsonPredicateContainer<?, ?>>) (Object)
            FabricRegistryBuilder.createSimple(RegistryKey.ofRegistry(DataCriteria.id("container"))).buildAndRegister();

    //#region Basic

    public static final JsonPredicateContainer<Integer, JsonPredicate<Integer, NumberRange.IntRange>> INT = new JsonPredicateContainer<>(element -> {
        NumberRange.IntRange range = NumberRange.IntRange.fromJson(element);
        return new JsonPredicate<>(range, Integer.class, range::test, NumberRange::toJson);
    });

    public static final JsonPredicateContainer<Double, JsonPredicate<Double, NumberRange.FloatRange>> FLOAT = new JsonPredicateContainer<>(element -> {
        NumberRange.FloatRange range = NumberRange.FloatRange.fromJson(element);
        return new JsonPredicate<>(range, Double.class, range::test, NumberRange::toJson);
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

    public static final JsonPredicateContainer<Identifier, JsonPredicate.Equality<Identifier>> IDENTIFIER = JsonPredicateContainer.createEquality(
            Identifier.class,
            identifier -> new JsonPrimitive(identifier.toString()),
            element -> new Identifier(element.getAsString())
    );

    //#endregion Basic

    //#region Items

    public static final JsonPredicateContainer<ItemStack, JsonPredicate<ItemStack, ItemPredicate>> ITEM = new JsonPredicateContainer<>(element -> {
        ItemPredicate predicate = ItemPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, ItemStack.class, predicate::test, ItemPredicate::toJson);
    });

    public static final JsonPredicateContainer<Map<Enchantment, Integer>, JsonPredicate<Map<Enchantment, Integer>, EnchantmentPredicate>> ENCHANTMENTS = new JsonPredicateContainer<>(element -> {
        EnchantmentPredicate predicate = EnchantmentPredicate.deserialize(element);
        return new JsonPredicate<>(predicate, (Class<Map<Enchantment, Integer>>) (Class<?>) Map.class, predicate::test, EnchantmentPredicate::serialize);
    });

    //#endregion Items

    //#region Blocks

    public static final StateContainer<?> STATE = new StateContainer<>();

    public static final JsonPredicateContainer<DelegateParameters.BlockParameter, JsonPredicate<DelegateParameters.BlockParameter, BlockPredicate>> BLOCK = new JsonPredicateContainer<>(element -> {
        BlockPredicate predicate = BlockPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.BlockParameter.class, parameter -> predicate.test(parameter.world(), parameter.pos()), BlockPredicate::toJson);
    });

    public static final JsonPredicateContainer<DelegateParameters.BlockParameter, JsonPredicate<DelegateParameters.BlockParameter, FluidPredicate>> FLUID = new JsonPredicateContainer<>(element -> {
        FluidPredicate predicate = FluidPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.BlockParameter.class, parameter -> predicate.test(parameter.world(), parameter.pos()), FluidPredicate::toJson);
    });

    public static final JsonPredicateContainer<DelegateParameters.BlockParameter, JsonPredicate<DelegateParameters.BlockParameter, LightPredicate>> LIGHT_LEVEL = new JsonPredicateContainer<>(element -> {
        LightPredicate predicate = LightPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.BlockParameter.class, parameter -> predicate.test(parameter.world(), parameter.pos()), LightPredicate::toJson);
    });

    //#endregion Blocks

    //#region Entities

    public static final EntityCheckContainer ENTITY_CHECK = new EntityCheckContainer();

    public static final JsonPredicateContainer<DelegateParameters.EntityParameter, JsonPredicate<DelegateParameters.EntityParameter, EntityPredicate>> ENTITY = new JsonPredicateContainer<>(element -> {
        EntityPredicate predicate = EntityPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.EntityParameter.class, parameter -> predicate.test(parameter.world(), parameter.pos(), parameter.entity()), EntityPredicate::toJson);
    });

    public static final JsonPredicateContainer<EntityType<?>, JsonPredicate<EntityType<?>, EntityTypePredicate>> ENTITY_TYPE = new JsonPredicateContainer<>(element -> {
        EntityTypePredicate predicate = EntityTypePredicate.fromJson(element);
        return new JsonPredicate<>(predicate, (Class<EntityType<?>>) (Class<?>) EntityType.class, predicate::matches, EntityTypePredicate::toJson);
    });

    public static final JsonPredicateContainer<DelegateParameters.EntityParameter, JsonPredicate<DelegateParameters.EntityParameter, TypeSpecificPredicate>> ENTITY_VARIANT = new JsonPredicateContainer<>(element -> {
        TypeSpecificPredicate predicate = TypeSpecificPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.EntityParameter.class, parameter -> predicate.test(parameter.entity(), parameter.world(), parameter.pos()), TypeSpecificPredicate::toJson);
    });

    public static final JsonPredicateContainer<Entity, JsonPredicate<Entity, EntityEffectPredicate>> ENTITY_EFFECTS = new JsonPredicateContainer<>(element -> {
        EntityEffectPredicate predicate = EntityEffectPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, Entity.class, predicate::test, EntityEffectPredicate::toJson);
    });

    public static final JsonPredicateContainer<Entity, JsonPredicate<Entity, EntityEquipmentPredicate>> ENTITY_EQUIPMENT = new JsonPredicateContainer<>(element -> {
        EntityEquipmentPredicate predicate = EntityEquipmentPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, Entity.class, predicate::test, EntityEquipmentPredicate::toJson);
    });

    public static final JsonPredicateContainer<Entity, JsonPredicate<Entity, EntityFlagsPredicate>> ENTITY_FLAGS = new JsonPredicateContainer<>(element -> {
        EntityFlagsPredicate predicate = EntityFlagsPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, Entity.class, predicate::test, EntityFlagsPredicate::toJson);
    });

    public static final JsonPredicateContainer<DelegateParameters.EntityParameter, JsonPredicate<DelegateParameters.EntityParameter, PlayerPredicate>> PLAYER = new JsonPredicateContainer<>(element -> {
        PlayerPredicate predicate = PlayerPredicate.fromJson(element.getAsJsonObject());
        return new JsonPredicate<>(predicate, DelegateParameters.EntityParameter.class, parameter -> predicate.test(parameter.entity(), parameter.world(), parameter.pos()), TypeSpecificPredicate::toJson);
    });

    public static final JsonPredicateContainer<DelegateParameters.DamageSourceParameter, JsonPredicate<DelegateParameters.DamageSourceParameter, DamageSourcePredicate>> DAMAGE_SOURCE = new JsonPredicateContainer<>(element -> {
        DamageSourcePredicate predicate = DamageSourcePredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.DamageSourceParameter.class, parameter -> predicate.test(parameter.world(), parameter.pos(), parameter.source()), DamageSourcePredicate::toJson);
    });

    public static final JsonPredicateContainer<DelegateParameters.DamageParameter, JsonPredicate<DelegateParameters.DamageParameter, DamagePredicate>> DAMAGE = new JsonPredicateContainer<>(element -> {
        DamagePredicate predicate = DamagePredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.DamageParameter.class, parameter -> predicate.test(parameter.player(), parameter.source(), parameter.dealt(), parameter.taken(), parameter.blocked()), DamagePredicate::toJson);
    });

    //#endregion Entiries

    //#region Misc

    public static final JsonPredicateContainer<DelegateParameters.DistanceParameter, JsonPredicate<DelegateParameters.DistanceParameter, DistancePredicate>> DISTANCE = new JsonPredicateContainer<>(element -> {
        DistancePredicate predicate = DistancePredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.DistanceParameter.class, parameter -> predicate.test(parameter.x1(), parameter.y1(), parameter.z1(), parameter.x2(), parameter.y2(), parameter.z2()), DistancePredicate::toJson);
    });

    public static final JsonPredicateContainer<DelegateParameters.LocationParameter, JsonPredicate<DelegateParameters.LocationParameter, LocationPredicate>> LOCATION = new JsonPredicateContainer<>(element -> {
        LocationPredicate predicate = LocationPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, DelegateParameters.LocationParameter.class, parameter -> predicate.test(parameter.world(), parameter.x(), parameter.y(), parameter.z()), LocationPredicate::toJson);
    });

    public static final JsonPredicateContainer<NbtElement, JsonPredicate<NbtElement, NbtPredicate>> NBT = new JsonPredicateContainer<>(element -> {
        NbtPredicate predicate = NbtPredicate.fromJson(element);
        return new JsonPredicate<>(predicate, NbtElement.class, predicate::test, NbtPredicate::toJson);
    });

    //#endregion Misc

    public static void register(String name, JsonPredicateContainer<?, ?> container) {
        Registry.register(REGISTRY, DataCriteria.id(name), container);
    }

    public static void registerMc(String name, JsonPredicateContainer<?, ?> container) {
        Registry.register(REGISTRY, new Identifier(name), container);
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
