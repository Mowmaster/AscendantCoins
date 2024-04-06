package com.mowmaster.ascendantcoins.items.coins.coppers;

import com.mowmaster.ascendantcoins.items.coins.BaseCoinItem;
import com.mowmaster.ascendantcoins.utils.AscendantCoinsConfig;

public class CoinCopperMedium extends BaseCoinItem {
    public CoinCopperMedium(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public double coinValue() {
        return AscendantCoinsConfig.COMMON.coins_medium_copper.get();
    }
}
