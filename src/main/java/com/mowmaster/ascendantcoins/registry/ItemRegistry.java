package com.mowmaster.ascendantcoins.registry;

import com.mowmaster.ascendantcoins.ascendantcoins;
import com.mowmaster.ascendantcoins.util.References;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemRegistry
{
    public static final Item ITEM_SEASONED_HERB_BUTTER = registerItem("herb_butter", new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, new Identifier(References.MODID, name),item);
    }

    public static void registerModItems()
    {
        ascendantcoins.LOGGER.info("Registering Mod Items for " + References.MODNAME);
    }
}
