package com.mowmaster.ascendantcoins.items.irons;

import com.mowmaster.ascendantcoins.items.BaseCoinItem;
import com.mowmaster.ascendantcoins.utils.AscendantCoinsConfig;

public class CoinIronSmall extends BaseCoinItem {
    public CoinIronSmall(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public double coinValue() {
        return AscendantCoinsConfig.COMMON.coins_small_iron.get();
    }
}
