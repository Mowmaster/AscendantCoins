package com.mowmaster.ascendantcoins.items.coins.coppers;

import com.mowmaster.ascendantcoins.items.coins.BaseCoinItem;
import com.mowmaster.ascendantcoins.registry.ConfigRegistry;

public class CoinCopperSmall extends BaseCoinItem {
    public CoinCopperSmall(Settings settings) {
        super(settings);
    }

    @Override
    public double coinValue() {
        return ConfigRegistry.CONFIG.coins_small_copper;
    }
}
