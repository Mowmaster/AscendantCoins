package com.mowmaster.ascendantcoins.items.coins.netherites;

import com.mowmaster.ascendantcoins.items.coins.BaseCoinItem;
import com.mowmaster.ascendantcoins.registry.ConfigRegistry;
import net.minecraft.item.ItemStack;

public class CoinNetheriteSmall extends BaseCoinItem {
    public CoinNetheriteSmall(Settings settings) {
        super(settings);
    }

    @Override
    public double coinValue() {
        return ConfigRegistry.CONFIG.coins_small_netherite;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
