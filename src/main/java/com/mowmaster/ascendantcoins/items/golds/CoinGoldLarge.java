package com.mowmaster.ascendantcoins.items.golds;

import com.mowmaster.ascendantcoins.items.BaseCoinItem;
import com.mowmaster.ascendantcoins.utils.AscendantCoinsConfig;

public class CoinGoldLarge extends BaseCoinItem {
    public CoinGoldLarge(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public double coinValue() {
        return AscendantCoinsConfig.COMMON.coins_large_gold.get();
    }
}
