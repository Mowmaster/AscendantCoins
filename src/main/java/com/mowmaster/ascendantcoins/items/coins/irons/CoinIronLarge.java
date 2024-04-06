package com.mowmaster.ascendantcoins.items.coins.irons;

import com.mowmaster.ascendantcoins.items.coins.BaseCoinItem;
import com.mowmaster.ascendantcoins.utils.AscendantCoinsConfig;

public class CoinIronLarge extends BaseCoinItem {
    public CoinIronLarge(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public double coinValue() {
        return AscendantCoinsConfig.COMMON.coins_large_iron.get();
    }
}
