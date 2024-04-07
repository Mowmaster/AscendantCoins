package com.mowmaster.ascendantcoins.items.coins.coppers;

import com.mowmaster.ascendantcoins.items.coins.BaseCoinItem;
import com.mowmaster.ascendantcoins.registry.ConfigRegistry;


public class CoinCopperLarge extends BaseCoinItem {

    public CoinCopperLarge(Settings settings) {
        super(settings);
    }

    @Override
    public double coinValue() {
        return ConfigRegistry.CONFIG.coins_large_copper;
    }
}
