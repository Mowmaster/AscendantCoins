package com.mowmaster.ascendantcoins.items.golds;

import com.mowmaster.ascendantcoins.items.BaseCoinItem;
import com.mowmaster.ascendantcoins.utils.AscendantCoinsConfig;

public class CoinGoldSmall extends BaseCoinItem {
    public CoinGoldSmall(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public double coinValue() {
        return AscendantCoinsConfig.COMMON.coins_small_gold.get();
    }
}
