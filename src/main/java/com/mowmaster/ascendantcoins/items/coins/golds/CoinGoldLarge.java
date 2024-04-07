package com.mowmaster.ascendantcoins.items.coins.golds;

import com.mowmaster.ascendantcoins.items.coins.BaseCoinItem;
import com.mowmaster.ascendantcoins.registry.ConfigRegistry;

public class CoinGoldLarge extends BaseCoinItem {
    public CoinGoldLarge(Settings settings) {
        super(settings);
    }

    @Override
    public double coinValue() {
        return ConfigRegistry.CONFIG.coins_large_gold;
    }
}
