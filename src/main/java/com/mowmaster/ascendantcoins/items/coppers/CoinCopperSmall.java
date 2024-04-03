package com.mowmaster.ascendantcoins.items.coppers;

import com.mowmaster.ascendantcoins.items.BaseCoinItem;
import com.mowmaster.ascendantcoins.utils.AscendantCoinsConfig;

public class CoinCopperSmall extends BaseCoinItem {
    public CoinCopperSmall(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public double coinValue() {
        return AscendantCoinsConfig.COMMON.coins_small_copper.get();
    }
}
