package com.mowmaster.ascendantcoins.items.coins.golds;

import com.mowmaster.ascendantcoins.items.coins.BaseCoinItem;
import com.mowmaster.ascendantcoins.registry.ConfigRegistry;

public class CoinGoldSmall extends BaseCoinItem {
    public CoinGoldSmall(Settings settings) {
        super(settings);
    }

    @Override
    public double coinValue() {
        return ConfigRegistry.CONFIG.coins_small_gold;
    }
}
