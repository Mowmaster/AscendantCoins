package com.mowmaster.ascendantcoins.registry;

import com.mowmaster.ascendantcoins.ascendantcoins;
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
    public static final ItemGroup RAW_INGREDIENTS_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(MODID,"coins_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.coins"))
                    .icon(() -> new ItemStack(ItemRegistry.ITEM_CROP_SEED))
                    .entries((displayContext, entries) -> {
                        entries.add(ItemRegistry.ITEM_CROP_SEED);
                        entries.add(ItemRegistry.ITEM_DOUBLE_CROP_SEED);
                    })
                    .build());

    public static void registerItemGroups()
    {
        ascendantcoins.LOGGER.info("Registering Item Groups for " + MODNAME);
    }
}
