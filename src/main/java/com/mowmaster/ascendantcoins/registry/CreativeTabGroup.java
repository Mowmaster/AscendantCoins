package com.mowmaster.ascendantcoins.registry;

import com.mowmaster.ascendantcoins.ascendantcoins;
import com.mowmaster.ascendantcoins.util.References;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.mowmaster.ascendantcoins.util.References.MODID;
import static com.mowmaster.ascendantcoins.util.References.MODNAME;

public class CreativeTabGroup
{
    public static final ItemGroup COINS_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(MODID,"coins_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.coins"))
                    .icon(() -> new ItemStack(ItemRegistry.COIN_LARGE_NETHERITE))
                    .entries((displayContext, entries) -> {
                        entries.add(ItemRegistry.COIN_SMALL_COPPER);
                        entries.add(ItemRegistry.COIN_MEDIUM_COPPER);
                        entries.add(ItemRegistry.COIN_LARGE_COPPER);

                        entries.add(ItemRegistry.COIN_SMALL_IRON);
                        entries.add(ItemRegistry.COIN_LARGE_IRON);

                        entries.add(ItemRegistry.COIN_SMALL_GOLD);
                        entries.add(ItemRegistry.COIN_LARGE_GOLD);

                        entries.add(ItemRegistry.COIN_SMALL_NETHERITE);
                        entries.add(ItemRegistry.COIN_LARGE_NETHERITE);

                        entries.add(ItemRegistry.WALLET);
                    })
                    .build());

    public static void registerItemGroups()
    {
        References.LOGGER.info("Registering Item Groups for " + MODNAME);
    }
}
