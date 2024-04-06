package com.mowmaster.ascendantcoins.registry;

import com.mowmaster.ascendantcoins.items.coins.coppers.CoinCopperLarge;
import com.mowmaster.ascendantcoins.items.coins.coppers.CoinCopperMedium;
import com.mowmaster.ascendantcoins.items.coins.coppers.CoinCopperSmall;
import com.mowmaster.ascendantcoins.items.coins.golds.CoinGoldSmall;
import com.mowmaster.ascendantcoins.items.coins.irons.CoinIronLarge;
import com.mowmaster.ascendantcoins.items.coins.netherites.CoinNetheriteLarge;
import com.mowmaster.ascendantcoins.items.coins.netherites.CoinNetheriteSmall;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.mowmaster.ascendantcoins.utils.Reference.MODID;

public class DeferredRegistryItems
{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> COIN_SMALL_COPPER = ITEMS.register("small_copper",
            () -> new CoinCopperSmall(new Item.Properties()));
    public static final RegistryObject<Item> COIN_MEDIUM_COPPER = ITEMS.register("medium_copper",
            () -> new CoinCopperMedium(new Item.Properties()));
    public static final RegistryObject<Item> COIN_LARGE_COPPER = ITEMS.register("large_copper",
            () -> new CoinCopperLarge(new Item.Properties()));

    public static final RegistryObject<Item> COIN_SMALL_IRON = ITEMS.register("small_iron",
            () -> new CoinIronLarge(new Item.Properties()));
    public static final RegistryObject<Item> COIN_LARGE_IRON = ITEMS.register("large_iron",
            () -> new CoinIronLarge(new Item.Properties()));

    public static final RegistryObject<Item> COIN_SMALL_GOLD = ITEMS.register("small_gold",
            () -> new CoinGoldSmall(new Item.Properties()));
    public static final RegistryObject<Item> COIN_LARGE_GOLD = ITEMS.register("large_gold",
            () -> new CoinGoldSmall(new Item.Properties()));

    public static final RegistryObject<Item> COIN_SMALL_NETHERITE = ITEMS.register("small_netherite",
            () -> new CoinNetheriteSmall(new Item.Properties()));
    public static final RegistryObject<Item> COIN_LARGE_NETHERITE = ITEMS.register("large_netherite",
            () -> new CoinNetheriteLarge(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
