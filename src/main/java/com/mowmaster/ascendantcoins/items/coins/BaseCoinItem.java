package com.mowmaster.ascendantcoins.items.coins;

import com.mowmaster.ascendantcoins.utils.AscendantCoinsConfig;
import com.mowmaster.mowlib.MowLibUtils.MowLibTooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.math.BigDecimal;
import java.util.List;

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

    private static double getCoinAmount(ItemStack p_150779_) {
        if(p_150779_.getItem() instanceof BaseCoinItem coin)
        {
            return coin.coinValue();
        }
        return 0.0D;
    }

    public void appendHoverText(ItemStack p_150749_, Level p_150750_, List<Component> p_150751_, TooltipFlag p_150752_) {

        MutableComponent componentAmountTextSingle = Component.translatable("toooltip.coin_amount_single");
        MutableComponent componentAmountTextStack = Component.translatable("toooltip.coin_amount_stack");

        componentAmountTextSingle.withStyle(ChatFormatting.GOLD);
        MutableComponent componentAmountSingle = Component.literal(""+ BigDecimal.valueOf(getCoinAmount(p_150749_)).toPlainString());
        componentAmountSingle.withStyle(ChatFormatting.GREEN);
        componentAmountTextSingle.append(componentAmountSingle);
        MowLibTooltipUtils.addTooltipMessage(p_150751_,componentAmountTextSingle);

        //MowLibTooltipUtils.addTooltipMessage(p_150751_,Component.literal(""));

        componentAmountTextStack.withStyle(ChatFormatting.LIGHT_PURPLE);
        MutableComponent componentAmountStack = Component.literal(""+ BigDecimal.valueOf(getCoinAmount(p_150749_)*p_150749_.getCount()).toPlainString());
        componentAmountStack.withStyle(ChatFormatting.WHITE);
        componentAmountTextStack.append(componentAmountStack);
        MowLibTooltipUtils.addTooltipMessage(p_150751_,componentAmountTextStack);
    }
}
