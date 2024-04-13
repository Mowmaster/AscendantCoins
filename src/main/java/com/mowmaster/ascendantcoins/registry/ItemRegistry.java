package com.mowmaster.ascendantcoins.registry;

import com.mowmaster.ascendantcoins.ascendantcoins;
import com.mowmaster.ascendantcoins.items.coins.coppers.CoinCopperLarge;
import com.mowmaster.ascendantcoins.items.coins.coppers.CoinCopperMedium;
import com.mowmaster.ascendantcoins.items.coins.coppers.CoinCopperSmall;
import com.mowmaster.ascendantcoins.items.coins.golds.CoinGoldLarge;
import com.mowmaster.ascendantcoins.items.coins.golds.CoinGoldSmall;
import com.mowmaster.ascendantcoins.items.coins.irons.CoinIronLarge;
import com.mowmaster.ascendantcoins.items.coins.irons.CoinIronSmall;
import com.mowmaster.ascendantcoins.items.coins.netherites.CoinNetheriteLarge;
import com.mowmaster.ascendantcoins.items.coins.netherites.CoinNetheriteSmall;
import com.mowmaster.ascendantcoins.items.wallets.WalletBase;
import com.mowmaster.ascendantcoins.util.References;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemRegistry
{
    public static final Item COIN_SMALL_COPPER = registerItem("small_copper", new CoinCopperSmall(new FabricItemSettings()));
    public static final Item COIN_MEDIUM_COPPER = registerItem("medium_copper", new CoinCopperMedium(new FabricItemSettings()));
    public static final Item COIN_LARGE_COPPER = registerItem("large_copper", new CoinCopperLarge(new FabricItemSettings()));

    public static final Item COIN_SMALL_IRON = registerItem("small_iron", new CoinIronSmall(new FabricItemSettings()));
    public static final Item COIN_LARGE_IRON = registerItem("large_iron", new CoinIronLarge(new FabricItemSettings()));

    public static final Item COIN_SMALL_GOLD = registerItem("small_gold", new CoinGoldSmall(new FabricItemSettings()));
    public static final Item COIN_LARGE_GOLD = registerItem("large_gold", new CoinGoldLarge(new FabricItemSettings()));

    public static final Item COIN_SMALL_NETHERITE = registerItem("small_netherite", new CoinNetheriteSmall(new FabricItemSettings()));
    public static final Item COIN_LARGE_NETHERITE = registerItem("large_netherite", new CoinNetheriteLarge(new FabricItemSettings()));


    public static final Item WALLET = registerItem("wallet", new WalletBase(new FabricItemSettings()));

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, new Identifier(References.MODID, name),item);
    }

    public static void registerModItems()
    {
        ascendantcoins.LOGGER.info("Registering Mod Items for " + References.MODNAME);
    }
}
