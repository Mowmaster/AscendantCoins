package com.mowmaster.ascendantcoins.items.coppers;

import com.mowmaster.ascendantcoins.items.BaseCoinItem;
import com.mowmaster.ascendantcoins.utils.AscendantCoinsConfig;

public class CoinCopperLarge extends BaseCoinItem {
    public CoinCopperLarge(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public double coinValue() {
        return AscendantCoinsConfig.COMMON.coins_large_copper.get();
    }
}
