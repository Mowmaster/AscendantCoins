package com.mowmaster.ascendantcoins.items.coins.irons;

import com.mowmaster.ascendantcoins.items.coins.BaseCoinItem;
import com.mowmaster.ascendantcoins.registry.ConfigRegistry;

public class CoinIronSmall extends BaseCoinItem {
    public CoinIronSmall(Settings settings) {
        super(settings);
    }

    @Override
    public double coinValue() {
        return ConfigRegistry.CONFIG.coins_small_iron;
    }
}
