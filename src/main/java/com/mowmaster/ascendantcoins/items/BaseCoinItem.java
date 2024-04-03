package com.mowmaster.ascendantcoins.items;

import com.mowmaster.ascendantcoins.utils.AscendantCoinsConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BaseCoinItem extends Item
{
    public BaseCoinItem(Properties p_41383_)
    {
        super(p_41383_);
    }

    public double coinValue()
    {
        //return AscendantCoinsConfig.COMMON.coins_small_copper.get();
        return 0.0D;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        return super.onDroppedByPlayer(item, player);
    }

    @Override
    public void onCraftedBy(ItemStack p_41447_, Level p_41448_, Player p_41449_) {
        super.onCraftedBy(p_41447_, p_41448_, p_41449_);
    }
}
