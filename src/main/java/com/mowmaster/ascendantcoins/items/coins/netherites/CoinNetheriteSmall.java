package com.mowmaster.ascendantcoins.items.coins.netherites;

import com.mowmaster.ascendantcoins.items.coins.BaseCoinItem;
import com.mowmaster.ascendantcoins.utils.AscendantCoinsConfig;
import net.minecraft.world.item.ItemStack;

public class CoinNetheriteSmall extends BaseCoinItem {
    public CoinNetheriteSmall(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public double coinValue() {
        return AscendantCoinsConfig.COMMON.coins_small_netherite.get();
    }

    public boolean isFoil(ItemStack p_43138_) {
        return true;
    }
}
