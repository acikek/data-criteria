package com.acikek.datacriteria.predicate.builtin.delegate;

import com.acikek.datacriteria.predicate.JsonPredicate;
import com.acikek.datacriteria.predicate.JsonPredicateContainer;
import com.google.gson.JsonElement;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.item.ItemPredicate;

public class ItemContainer extends JsonPredicateContainer<ItemStack, ItemContainer.Predicate> {

    @Override
    public Predicate fromJson(JsonElement element) {
        return new Predicate(ItemPredicate.fromJson(element));
    }

    public static class Predicate extends JsonPredicate<ItemStack, ItemPredicate> {

        public Predicate(ItemPredicate predicate) {
            super(new Builder<ItemStack, ItemPredicate>()
                    .value(predicate)
                    .type(ItemStack.class)
                    .tester(predicate::test)
                    .serializer(ItemPredicate::toJson));
        }
    }
}
